package shapes

/**
 * Represents a point in three-dimensional space
 * @param x x-coordinate
 * @param y y-coordinate
 * @param z z-coordinate
 */
class Point3D private(val x: Double, val y: Double, val z: Double) {

  /**
   * Given a point and a range, returns whether the distance between the point and the given point is within the range
   * @param point other point use to determine distance between
   * @param rangeAllowed maximum allowed range for distance between the points
   * @return true, if the distance between the current point and the given point are less than or equal to the
   *         given range; false, if otherwise
   */
  def within(point: Point3D, rangeAllowed: Double): Boolean = {
    math.sqrt(math.pow(x - point.x, 2) + math.pow(y - point.y, 2) + math.pow(z - point.z, 2)) <= rangeAllowed
  }

  // Auto-generated
  def canEqual(other: Any): Boolean = other.isInstanceOf[Point3D]

  // Auto-generated
  override def equals(other: Any): Boolean = other match {
    case that: Point3D =>
      (that canEqual this) &&
        x == that.x &&
        y == that.y &&
        z == that.z
    case _ => false
  }
  // Does this section need to be edited?
  // Auto-generated
  override def hashCode(): Int = {
    val state = Seq(x, y, z)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }

}
/**
 * Companion object used for utility functions
 */
object Point3D {
  /**
   * Converts to a Point3D
   * @param pointsString co-ordinate string
   * @return a object of point with x, y and z coordinates
   *
   * @throws IllegalArgumentException
   *  - does not contain three values separated by the regex
   */
  def fromString(pointsString: String): Point3D = {
    val pointsArr = pointsString.split(",")
    if (pointsArr.length != 3) {
      throw new IllegalArgumentException("3D Points string must contain three coordinates")
    }
    val xval = pointsArr(0).trim().toDouble
    val yval = pointsArr(1).trim().toDouble
    val zval = pointsArr(2).trim().toDouble
    Point3D(xval, yval, zval)
  }

  def apply(x: Double, y: Double, z: Double): Point3D = {
     new Point3D(x,y,z)
  }
}

