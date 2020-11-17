package shapes

class Point private(val x: Double, val y: Double) {

  def within(point: Point, rangeAllowed: Double): Boolean = {
    math.sqrt(math.pow((x - point.x), 2) + math.pow((y - point.y), 2)) <= rangeAllowed
  }

}
/**
 * Companion object used for utility functions
 */
object Point {
  /**
   * Converts to a point
   * @param pointsString co-ordinate string
   * @return a object of point with x and y coordinates
   *
   * @throws IllegalArgumentException
   *  - does not contain two values separated by the regex
   */
  def fromString(pointsString: String): Point = {
    val pointsArr = pointsString.split(",")
    if (pointsArr.length != 2) {
      throw new IllegalArgumentException("Points string must contain two coordinates")
    }
    val xval = pointsArr(0).trim().toDouble
    val yval = pointsArr(1).trim().toDouble
    new Point(xval, yval)

  }
  def apply(x: Double, y: Double): Point = {
     new Point(x,y)
  }
}

