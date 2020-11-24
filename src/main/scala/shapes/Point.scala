package shapes

/**
 * Represents a point in two-dimensional space
 * @param x x-coordinate
 * @param y y-coordinate
 */
class Point private(val x: Double, val y: Double) {

  /**
   * Given a point and a range, returns whether the distance between the point and the given point is within the range
   * @param point other point use to determine distance between
   * @param rangeAllowed maximum allowed range for distance between the points
   * @return true, if the distance between the current point and the given point are less than or equal to the
   *         given range; false, if otherwise
   */
  def within(point: Point, rangeAllowed: Double): Boolean = {
    math.sqrt(math.pow(x - point.x, 2) + math.pow(y - point.y, 2)) <= rangeAllowed
  }

  // Auto-generated
  def canEqual(other: Any): Boolean = other.isInstanceOf[Point3D]

  // Auto-generated
  override def equals(other: Any): Boolean = other match {
    case that: Point =>
      (that canEqual this) &&
        x == that.x &&
        y == that.y
    case _ => false
  }

  // Auto-generated
  override def hashCode(): Int = {
    val state = Seq(x, y)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
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
    Point(xval, yval)
  }

  def apply(x: Double, y: Double): Point = {
     new Point(x,y)
  }
}

