package shapes


class Rectangle (var p1: Point, var p2: Point)  {

  def contains(point: Point): Boolean = {
    if (point.x >= p1.x && point.x <= p2.x && point.y >= p1.y && point.y <= p2.y) {
      return true
    } else {
      return false
    }
  }

}
object Rectangle {

  def fromString(rectangleString: String): Rectangle = {
      val rectangleStrArr = rectangleString.split(",")
      val rectanglex1 = rectangleStrArr(0).trim().toDouble
      val rectangley1 = rectangleStrArr(1).trim().toDouble
      val rectanglex2 = rectangleStrArr(2).trim().toDouble
      val rectangley2 = rectangleStrArr(3).trim().toDouble
      var p1c: Point = new Point(rectanglex1, rectangley1)
      var p2c: Point = new Point(rectanglex2, rectangley2)
      // This is the Rectangle class
      var min_x: Double = 0
      var max_x: Double = 0
      var min_y: Double = 0
      var max_y: Double = 0
      if (p1c.x < p2c.x) {
      min_x = p1c.x
      max_x = p2c.x
    } else {
      min_x = p2c.x
      max_x = p1c.x
    }
      if (p1c.y < p2c.y) {
      min_y = p1c.y
      max_y = p2c.y
    } else {
      min_y = p2c.y
      max_y = p1c.y
    }
      val minpoint = new Point(min_x, min_y)
      val maxpoint = new Point(max_x, max_y)
      val rectangleObj = new Rectangle(minpoint, maxpoint)
      return rectangleObj
  }


  def apply(x: Point, y: Point): Rectangle = {
    val rectangle1 = new Rectangle(x,y)
    return rectangle1
  }
}