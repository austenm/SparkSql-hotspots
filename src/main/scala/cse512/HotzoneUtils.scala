package cse512

import shapes.{Point, Rectangle}

object HotzoneUtils {


  /**
   * ST Contains method to check if a point coordinate is present in a Rectangle
   * @return boolean representing if the point is within the rectangle
   */
  def ST_Contains(queryRectangle: String, pointString: String ) : Boolean = {
    Rectangle.fromString(queryRectangle).contains(Point.fromString(pointString))
  }



}
