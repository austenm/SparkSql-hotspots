package cse512

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{IntegerType, StringType, StructType}
import org.scalatest.{DoNotDiscover, FunSuite}
import org.scalatest.Matchers.{contain, convertToAnyShouldWrapper}

object HotzoneAnalysisTest {
  val spark = SparkSession.builder
    .appName("HotzoneAnalysisTest")
    .config("spark.some.config.option", "some-value")
    .master("local[*]")
    .getOrCreate()
}

@DoNotDiscover
class HotzoneAnalysisTest extends FunSuite {
  test("run HotZoneAnalysis on test data") {
    val actualAnswer = HotzoneAnalysis.runHotZoneAnalysis(
      HotzoneAnalysisTest.spark,
      "src/resources/point_hotzone.csv",
      "src/resources/zone-hotzone.csv")

    val schema = new StructType()
      .add("rectangle", StringType, false)
      .add("count", IntegerType, false)

    val expectedAnswer = HotzoneAnalysisTest.spark.read
      .format("com.databricks.spark.csv")
      .option("delimiter", ",")
      .option("header", "false")
      .schema(schema)
      .load("testcase/hotzone/hotzone-example-answer.csv")
      .orderBy("rectangle")

    actualAnswer.collect() should contain theSameElementsAs expectedAnswer.collect()
    expectedAnswer.collect() should contain theSameElementsAs actualAnswer.collect()
  }
}
