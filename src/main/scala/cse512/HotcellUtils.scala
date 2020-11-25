package cse512

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Calendar

import org.apache.commons.math3.stat.descriptive.summary.SumOfSquares

object HotcellUtils {
  val coordinateStep = 0.01

  def CalculateCoordinate(inputString: String, coordinateOffset: Int): Int = {
    // Configuration variable:
    // Coordinate step is the size of each cell on x and y
    var result = 0
    coordinateOffset match {
      case 0 =>
        result =
          Math.floor((inputString.split(",")(0).replace("(", "").toDouble / coordinateStep)).toInt
      case 1 =>
        result =
          Math.floor(inputString.split(",")(1).replace(")", "").toDouble / coordinateStep).toInt
      // We only consider the data from 2009 to 2012 inclusively, 4 years in total. Week 0 Day 0 is 2009-01-01
      case 2 => {
        val timestamp = HotcellUtils.timestampParser(inputString)
        result = HotcellUtils.dayOfMonth(timestamp) // Assume every month has 31 days
      }
    }
    result
  }

  def timestampParser(timestampString: String): Timestamp = {
    val dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
    val parsedDate = dateFormat.parse(timestampString)
    new Timestamp(parsedDate.getTime)
  }

  def dayOfYear(timestamp: Timestamp): Int = {
    val calendar = Calendar.getInstance
    calendar.setTimeInMillis(timestamp.getTime)
    calendar.get(Calendar.DAY_OF_YEAR)
  }

  def dayOfMonth(timestamp: Timestamp): Int = {
    {
      val calendar = Calendar.getInstance
      calendar.setTimeInMillis(timestamp.getTime)
      calendar.get(Calendar.DAY_OF_MONTH)
    }
  }

  /**
   * Partial function that returns the g-score given a cell score and number of adjacent neighbors
   * @param sumOfValues sum of all cells
   * @param sumOfSquares sum of squares of all cells
   * @param numCells total number of cells
   * @return proatial function to calculate g-score
   */
  def partialGscore(sumOfValues: Double,
                    sumOfSquares: Double,
                    numCells: Double): (Long, Long) => Double = {
    val xBar = sumOfValues / numCells
    val stdDev = math.sqrt((sumOfSquares / numCells) - math.pow(xBar, 2))
    (cellScore: Long, adjacentNeighbors: Long) => {
      val denom_inner_numerator = (numCells * adjacentNeighbors) - math.pow(adjacentNeighbors, 2)
      val denom_inner_denom     = numCells - 1
      val denominator           = stdDev * math.sqrt(denom_inner_numerator / denom_inner_denom)
      val numerator             = cellScore - (xBar * adjacentNeighbors)
      numerator / denominator
    }
  }

}
