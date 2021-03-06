package shapes

/**
 * Represents a rectangle in three-dimensional space
 * @param minpoint {@type Point3D} represents the minimum x, y and z coordinates of the rectangle
 * @param maxpoint {@type Point3D} represents the maximum x, y and z coordinates of the rectangle
 */
@SerialVersionUID(100L)
class Rectangle3D private(val minpoint: Point3D, val maxpoint: Point3D) extends Serializable {

  /**
   * Given a Point3D, determines whether the Point3D is contained within the rectangle3D
   * @param point to test
   * @return true - if the point is contained within the rectangle; false - if otherwise
   */
  def contains(point: Point3D): Boolean = {
     point.x >= minpoint.x &&
     point.x <= maxpoint.x &&
     point.y >= minpoint.y &&
     point.y <= maxpoint.y &&
     point.z >= minpoint.z &&
     point.z <= maxpoint.z
  }

  /**
   * Given a Point3D, determines the number of coordinate boundaries the Point3D is touching
   * @param point {@type Point3D} to test
   * @return number of coordinate boundaries the point is touching
   */
  def getBoundariesTouching(point: Point3D): Int = {
    var numberOfBoundaries = 0
    if (Seq(minpoint.x, maxpoint.x).contains(point.x)) {
      numberOfBoundaries += 1
    }
    if (Seq(minpoint.y, maxpoint.y).contains(point.y)) {
      numberOfBoundaries += 1
    }
    if (Seq(minpoint.z, maxpoint.z).contains(point.z)) {
      numberOfBoundaries += 1
    }
    numberOfBoundaries
  }

  // auto-generated
  def canEqual(other: Any): Boolean = other.isInstanceOf[Rectangle3D]

  // auto-generated
  override def equals(other: Any): Boolean = other match {
    case that: Rectangle3D =>
      (that canEqual this) &&
        minpoint == that.minpoint &&
        maxpoint == that.maxpoint
    case _ => false
  }

  // auto-generated
  override def hashCode(): Int = {
    val state = Seq(minpoint, maxpoint)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }

  // Auto-generated
  override def toString = s"Rectangle3D($minpoint, $maxpoint)"
}

/**
 * Companion object used for utility functions
 */
object Rectangle3D {
  /**
   * Creates an object rectangle with min and max coordinates
   * @param rectangle3DString
   * @return Rectangle3D object
   * @throws IllegalArgumentException
   *  - Need to contain the six coordinates of a rectangle in three dimensions
   */
  def fromString(rectangle3DString: String): Rectangle3D = {
      val rectangleStrArr = rectangle3DString.split(",")
      if (rectangleStrArr.length != 6) {
        throw new IllegalArgumentException("Rectangle string must contain six points")
      }
      val xpoints = List(rectangleStrArr(0).trim().toDouble, rectangleStrArr(3).trim().toDouble).sorted
      val ypoints = List(rectangleStrArr(1).trim().toDouble, rectangleStrArr(4).trim().toDouble).sorted
      val zpoints = List(rectangleStrArr(2).trim().toDouble, rectangleStrArr(5).trim().toDouble).sorted
      apply(xpoints(0), ypoints(0), zpoints(0), xpoints(1), ypoints(1), zpoints(1))
  }
  /**
   * Factory-method for Rectangle3D
   *
   * Note: A rectangle where the first point is the min of x, y and z co-ordinates and
   * the second point is represented by the max of each x, y and z co-ordinates
   *
   * @param xMin min x co-ordinate position
   * @param yMin min y co-ordinate position
   * @param zMin min z co-ordinate position
   * @param xMax max x co-ordinate position
   * @param yMax max y co-ordinate position
   * @param zMax max z co-ordinate position
   * @return Rectangle3D with points
   */
  def apply(xMin: Double,
            yMin: Double,
            zMin: Double,
            xMax: Double,
            yMax: Double,
            zMax: Double): Rectangle3D = {
    new Rectangle3D(Point3D(xMin, yMin, zMin), Point3D(xMax, yMax, zMax))
  }
}