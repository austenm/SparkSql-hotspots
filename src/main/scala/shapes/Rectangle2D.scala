package shapes

/**
 * Represents a rectangle in two-dimensional space
 * @param minpoint {@type Point2D} represents the minimum x and y coordinates of the rectangle
 * @param maxpoint {@type Point2D} represents the maximum x and y coordinates of the rectangle
 */
class Rectangle2D private(val minpoint: Point2D, val maxpoint: Point2D)  {

  /**
   * Given a Point2D, determines whether the Point is contained within the rectangle
   * @param point point to test
   * @return true - if the point is contained within the rectangle; false - if otherwise
   */
  def contains(point: Point2D): Boolean = {
   point.x >= minpoint.x && point.x <= maxpoint.x && point.y >= minpoint.y && point.y <= maxpoint.y
  }

  // auto-generated
  def canEqual(other: Any): Boolean = other.isInstanceOf[Rectangle2D]

  // auto-generated
  override def equals(other: Any): Boolean = other match {
    case that: Rectangle2D =>
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
}

/**
 * Companion object used for utility functions
 */
object Rectangle2D {
  /**
   * Creates an object rectangle with min and max coordinates
   * @param rectangleString string representation of the rectangle
   * @return Rectangle object
   * @throws IllegalArgumentException
   *  - Need to contain the four coordinates of a rectangle
   */
  def fromString(rectangleString: String): Rectangle2D = {
      val rectangleStrArr = rectangleString.split(",")
      if (rectangleStrArr.length != 4) {
        throw new IllegalArgumentException("Rectangle string must contain four points")
      }
      val xpoints = List(rectangleStrArr(0).trim().toDouble, rectangleStrArr(2).trim().toDouble).sorted
      val ypoints = List(rectangleStrArr(1).trim().toDouble, rectangleStrArr(3).trim().toDouble).sorted
      apply(xpoints(0), ypoints(0), xpoints(1), ypoints(1))
  }
  /**
   * Factory-method for Rectangle2D
   *
   * Note: A Rectangle2D where the first point is the min of x and y co-ordinates and
   * the second point is represented by the max of each x and y co-ordinates
   *
   * @param xmin min x co-ordinate position
   * @param ymin min y co-ordinate position
   * @param xmax max x co-ordinate position
   * @param ymax max y co-ordinate position
   * @return Rectangle2D with points
   */
  def apply (xmin:Double, ymin:Double, xmax:Double, ymax:Double): Rectangle2D = {
    new Rectangle2D(Point2D(xmin,ymin), Point2D(xmax,ymax))
  }
}