package shapes

/**
 * Represents a rectangle in two-dimensional space
 * @param minpoint {@type Point} represents the minimum x and y coordinates of the rectangle
 * @param maxpoint {@type Point} represents the maximum x and y coordinates of the rectangle
 */
class Rectangle private (val minpoint: Point, val maxpoint: Point)  {

  /**
   * Given a Point, determines whether the Point is contained within the rectangle
   * @param point point to test
   * @return true - if the point is contianed within the rectangle; false - if otherwise
   */
  def contains(point: Point): Boolean = {
   point.x >= minpoint.x && point.x <= maxpoint.x && point.y >= minpoint.y && point.y <= maxpoint.y
  }

  // auto-generated
  def canEqual(other: Any): Boolean = other.isInstanceOf[Rectangle]

  // auto-generated
  override def equals(other: Any): Boolean = other match {
    case that: Rectangle =>
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
object Rectangle {
  /**
   * Creates an object rectangle with min and max coordinates
   * @param rectangleString
   * @return Rectangle object
   * @throws IllegalArgumentException
   *  - Need to contain the four coordinates of a rectangle
   */
  def fromString(rectangleString: String): Rectangle = {
      val rectangleStrArr = rectangleString.split(",")
      if (rectangleStrArr.length != 4) {
        throw new IllegalArgumentException("Rectangle string must contain four points")
      }
      val xpoints = List(rectangleStrArr(0).trim().toDouble, rectangleStrArr(2).trim().toDouble).sorted
      val ypoints = List(rectangleStrArr(1).trim().toDouble, rectangleStrArr(3).trim().toDouble).sorted
      apply(xpoints(0), ypoints(0), xpoints(1), ypoints(1))
  }
  /**
   * Factory-method for Rectangle
   *
   * Note: A rectangle where the first point is the min of x and y co-ordinates and
   * the second point is represented by the max of each x and y co-ordinates 
   *
   * @param xmin min x co-ordinate position
   * @param ymin min y co-ordinate position
   * @param xmax max x co-ordinate position
   * @param ymax max y co-ordinate position
   * @return Rectangle with points
   */
  def apply (xmin:Double, ymin:Double, xmax:Double, ymax:Double): Rectangle =
  {
    new Rectangle(Point(xmin,ymin), Point(xmax,ymax))
  }
}