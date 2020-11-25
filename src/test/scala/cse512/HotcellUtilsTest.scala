package cse512

import org.scalatest.FunSuite

class HotcellUtilsTest extends FunSuite {
  test("correctly calculates gi score for 5x5x3 using (2,2,1) with score 1, and a non-neighbor has 1") {
    val numCells = (5 * 5 * 3).toDouble
    val sumOfValues = 2
    val sumOfSquares = 2
    val partialGScore = HotcellUtils.partialGscore(sumOfValues, sumOfSquares, numCells)

    val cellByWeightSum = 1
    val adjacentNeighbors = 27

    assert(math.round(partialGScore(cellByWeightSum, adjacentNeighbors) * 10000) / 10000.0 == 0.4153)
  }
}
