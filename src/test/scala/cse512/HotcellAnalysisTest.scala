package cse512

import org.apache.spark.sql.{Row, SparkSession}
import org.scalatest.{DoNotDiscover, FunSuite}

object HotcellAnalysisTest {
  val spark = SparkSession.builder
    .appName("HotcellAnalysisTest")
    .config("spark.some.config.option", "some-value")
    .master("local[*]")
    .getOrCreate()
}

@DoNotDiscover
class HotcellAnalysisTest extends FunSuite {

  test("hotcell verification - functional test") {
    val result = HotcellAnalysis.runHotcellAnalysisWithGScore(HotcellAnalysisTest.spark,
      "src/test/resources/simple_hotcell.csv")

    val rows = result.collectAsList()
    assert(rows.size() == 5)
    assertRow(rows.get(0), -7395, 4077, 1, 33.7956)
    assertRow(rows.get(1), -7399, 4073, 16, 27.5832)
    assertRow(rows.get(2), -7399, 4075, 8, 27.5832)
    assertRow(rows.get(3), -7399, 4075, 3, 27.5832)
    assertRow(rows.get(4), -7401, 4071, 24, 27.5832)
  }

  def assertRow(row: Row, expectedX: Int, expectedY: Int, expectedZ: Int, expectedGScore: Double) = {
    assert(row.getInt(0) == expectedX)
    assert(row.getInt(1) == expectedY)
    assert(row.getInt(2) == expectedZ)
    assert(math.round(row.getDouble(3) * 10000) / 10000.0 == expectedGScore)
  }
}
