package cse512

import org.apache.spark.sql.SparkSession
import org.scalatest.FunSuite
import org.scalatest.Matchers.convertToAnyShouldWrapper

object SpatialQueryTest {
  val spark = SparkSession.builder
    .appName("SpatialQueryTest")
    .config("spark.some.config.option", "some-value")
    .master("local[*]")
    .getOrCreate()
}

class SpatialQueryTest extends FunSuite {
  test("range query where point is contained in rectangle") {
    val count = SpatialQuery.runRangeQuery(
      SpatialQueryTest.spark,
      "src/test/resources/range_query.csv",
      "0.5,1.5,1.5,2.5")
    count shouldEqual 1
  }

  test("range query where point is not contained in rectangle") {
    val count = SpatialQuery.runRangeQuery(
      SpatialQueryTest.spark,
      "src/test/resources/range_query.csv",
      "1.5,1.5,2.5,2.5")
    count shouldEqual 0
  }

  test("range join query where no points are contained") {
    val count = SpatialQuery.runRangeJoinQuery(
      SpatialQueryTest.spark,
      "src/test/resources/range_join_query_points_not_contained.csv",
      "src/test/resources/range_join_query_rectangles.csv"
    )
    count shouldEqual 0
  }

  test("range join query where all points are contained in one rectangle") {
    val count = SpatialQuery.runRangeJoinQuery(
      SpatialQueryTest.spark,
      "src/test/resources/range_join_query_points_contained_one_rectangle.csv",
      "src/test/resources/range_join_query_rectangles.csv"
    )
    count shouldEqual 2
  }

  test("range join query where all points are contained in two rectangles") {
    val count = SpatialQuery.runRangeJoinQuery(
      SpatialQueryTest.spark,
      "src/test/resources/range_join_query_points_contained_two_rectangles.csv",
      "src/test/resources/range_join_query_rectangles.csv"
    )
    count shouldEqual 4
  }
}
