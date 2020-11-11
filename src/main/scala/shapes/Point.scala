package shapes

class Point (var x: Double, var y: Double) {
  // This is the point class
  var xc: Double = x
  var yc: Double = y

}
object Point {
  def fromString(pointsString: String): Point = {
    val pointsArr = pointsString.split(",")
    val xval = pointsArr(0).trim().toDouble
    val yval = pointsArr(1).trim().toDouble
    val pt = new Point(xval, yval)
    return pt
  }
  def apply(x: Double, y: Double): Point = {
     val pt = new Point(x,y)
     return pt
  }
}

