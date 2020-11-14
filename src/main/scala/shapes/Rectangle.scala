package shapes


class Rectangle private (val minpoint: Point, val maxpoint: Point)  {

  def contains(point: Point): Boolean = {
   point.x >= minpoint.x && point.x <= maxpoint.x && point.y >= minpoint.y && point.y <= maxpoint.y
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
      throw new IllegalArgumentException("Rectangle string must contains four points")
      }
      val xpoints = List(rectangleStrArr(0).trim().toDouble, rectangleStrArr(2).trim().toDouble).sorted
      val ypoints = List(rectangleStrArr(1).trim().toDouble, rectangleStrArr(3).trim().toDouble).sorted
      val minpoint = new Point(xpoints(0), ypoints(0))
      val maxpoint = new Point(xpoints(1), ypoints(1))
      apply(minpoint, maxpoint)
  }


  def apply(x: Point, y: Point): Rectangle = {
    new Rectangle(x,y)
  }
}