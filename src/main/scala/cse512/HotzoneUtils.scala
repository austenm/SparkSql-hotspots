package cse512

import shapes.{Point2D, Rectangle2D}

object HotzoneUtils {


  /**
   * ST Contains method to check if a point coordinate is present in a Rectangle
   * @return boolean representing if the point is within the rectangle
   */
  def ST_Contains(queryRectangle: String, pointString: String ) : Boolean = {
    Rectangle2D.fromString(queryRectangle).contains(Point2D.fromString(pointString))
  }



}
