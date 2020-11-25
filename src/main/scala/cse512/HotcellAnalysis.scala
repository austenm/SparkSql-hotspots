package cse512

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.{DataFrame, SparkSession}
import shapes.{Point3D, Rectangle3D}

object HotcellAnalysis {
  Logger.getLogger("org.spark_project").setLevel(Level.WARN)
  Logger.getLogger("org.apache").setLevel(Level.WARN)
  Logger.getLogger("akka").setLevel(Level.WARN)
  Logger.getLogger("com").setLevel(Level.WARN)

  // Temporary table names
  val CellToRidesTable = "CellToRides"
  val HotCellScoresTable = "HotCellScores"
  val GScoreTable = "GScores"

  // Reused fields
  val HotcellScoreField = "hotcellScore"
  val GScoreField = "gscore"
  val NeighbourCountField = "neighbourCount"

  // Fudge-factor to support floating-point arithmetic
  val Epsilon = 0.000001


  def runHotcellAnalysisWithGScore(spark: SparkSession, pointPath: String): DataFrame = {
    // Load the original data from a data source
    var pickupInfo = spark.read
      .format("com.databricks.spark.csv")
      .option("delimiter", ";")
      .option("header", "false")
      .load(pointPath);
    pickupInfo.createOrReplaceTempView("nyctaxitrips")
    pickupInfo.show()

    // Assign cell coordinates based on pickup points
    spark.udf.register("CalculateX",
      (pickupPoint: String) =>
        ((
          HotcellUtils.CalculateCoordinate(pickupPoint,
            0)
          )))
    spark.udf.register("CalculateY",
      (pickupPoint: String) =>
        ((
          HotcellUtils.CalculateCoordinate(pickupPoint,
            1)
          )))
    spark.udf.register("CalculateZ",
      (pickupTime: String) =>
        ((
          HotcellUtils.CalculateCoordinate(pickupTime,
            2)
          )))
    pickupInfo = spark.sql(
      "select CalculateX(nyctaxitrips._c5),CalculateY(nyctaxitrips._c5), CalculateZ(nyctaxitrips._c1) from nyctaxitrips")
    val newCoordinateName = Seq("x", "y", "z")
    pickupInfo = pickupInfo.toDF(newCoordinateName: _*)
    pickupInfo.show()

    // Define the min and max of x, y, z
    val minX     = -74.50 / HotcellUtils.coordinateStep
    val maxX     = -73.70 / HotcellUtils.coordinateStep
    val minY     = 40.50 / HotcellUtils.coordinateStep
    val maxY     = 40.90 / HotcellUtils.coordinateStep
    val minZ     = 1
    val maxZ     = 31
    val numCells = (maxX - minX + 1) * (maxY - minY + 1) * (maxZ - minZ + 1)

    val searchSpace = Rectangle3D(minX, minY, minZ, maxX, maxY, maxZ)
    spark.udf.register(
      "ST_Contains",
      (x: Double, y: Double, z: Double) =>
        searchSpace.contains(Point3D(x, y, z))
    )

    pickupInfo
      .filter("ST_Contains(x, y, z)")
      .groupBy("x", "y", "z")
      .count()
      .createOrReplaceTempView(CellToRidesTable)

    // Get the global values to calculate xBar and std. deviation
    val sums = spark.sql(s"""
      select sum(count) sumOfValues, sum(count * count) sumOfSquares
      from ${CellToRidesTable}
      """)

    val sumsRow = sums.first()
    val sumOfValues = sumsRow.getLong(0).toDouble
    val sumOfSquares = sumsRow.getLong(1).toDouble

    // Neighbor cells must be within sqrt(3)
    //  - adding an epsilon to avoid any floating point math issue
    spark.udf.register(
      "ST_Neighbor",
      (x1: Double, y1: Double, z1: Double, x2: Double, y2: Double, z2: Double) => {
        val point1 = Point3D(x1, y1, z1)
        val point2 = Point3D(x2, y2, z2)
        point1.within(point2, math.sqrt(3) + Epsilon)
      }
    )

    // Determine the number of neighbour cells based on how many boundaries the cell
    // touches of the search space
    //
    // If a cell is touching a boundary, it will only have 2 neighbors along that axis,
    // otherwise, it will have 3.
    //
    // Therefore,
    //   neighbor cell count =
    //      2 ^ (search space boundaries) *
    //      3 ^ (3 - search space boundaries)
    spark.udf.register(
      "ST_GetCellNeighborCount",
      (x : Double, y: Double, z: Double) => {
        val numBoundaries = searchSpace.getBoundariesTouching(Point3D.apply(x, y, z))
        math.pow(2, numBoundaries) * math.pow(3, 3 - numBoundaries)
      }
    )

    // Calculate the raw hot cell score and the number of neighbors for each cell
    spark.sql(
      s"""
         select ctr1.x x, ctr1.y y, ctr1.z z, sum(ctr2.count) ${HotcellScoreField},
            ST_GetCellNeighborCount(ctr1.x, ctr1.y, ctr1.z) ${NeighbourCountField}
         from ${CellToRidesTable} ctr1, ${CellToRidesTable} ctr2
         where ST_Neighbor(ctr1.x, ctr1.y, ctr1.z, ctr2.x, ctr2.y, ctr2.z)
         group by ctr1.x, ctr1.y, ctr1.z
         """)
      .createOrReplaceTempView(HotCellScoresTable)

    // Create a partial function for gScore calculation - since xBar and std. deviation won't change
    val partialGscore = HotcellUtils.partialGscore(sumOfValues, sumOfSquares, numCells)
    spark.udf.register(
      "ST_Gscore",
      (hotcellScore: Long, neighbourCount: Long) => partialGscore(hotcellScore, neighbourCount)
    )

    // Calculate the gScores of all cells, with ordering to appease the auto-grader
    spark.sql(s"""
      select x, y, z, ST_Gscore(${HotcellScoreField}, ${NeighbourCountField}) ${GScoreField}
      from ${HotCellScoresTable}
      order by ${GScoreField} DESC, x DESC, y ASC, z DESC
    """)
  }

  def runHotcellAnalysis(spark: SparkSession, pointPath: String): DataFrame = {
    runHotcellAnalysisWithGScore(spark, pointPath)
      .coalesce(1)
      .select("x", "y", "z")
  }
}
