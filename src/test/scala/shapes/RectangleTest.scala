package shapes

import org.scalatest.FunSuite
import org.specs2.matcher.ShouldMatchers.thisValue

class RectangleTest extends FunSuite {

  test("throws InvalidArgumentException if points string does not contain enough params") {
    try {
      Rectangle.fromString("1.345")
    } catch {
      case _ : IllegalArgumentException =>
    }
  }

  test("throws InvalidArgumentException if points string contains non-multiple of 5 points") {
    try {
      Rectangle.fromString("1.345,-2.345,1.567,-3.4567,1")
    } catch {
      case _ : IllegalArgumentException =>
    }
  }

  test("throws InvalidArgumentException if any point is invalid") {
    try {
      Rectangle.fromString("111.345,-2.345,1.567,-3.4567")
    } catch {
      case _ : IllegalArgumentException =>
    }
  }

  test("Creates a Rectangle from a valid string") {
    val rectangle = Rectangle.fromString("1.345,-2.345,1.567,-3.4567")
    rectangle shouldEqual Rectangle(1.345, -2.345, 1.567, -3.4567)
  }

  test("x1 is smaller than x2, y1 is smaller than y2") {
    val actualRectangle = Rectangle(1.234, 2.345, 1.345, 2.567)
    actualRectangle.minpoint shouldEqual Point(1.234, 2.345)
    actualRectangle.maxpoint shouldEqual Point(1.345, 2.567)
  }

  test("x2 is smaller than x1, y2 is smaller than y1") {
    val actualRectangle = Rectangle(1.345, 2.567, 1.234, 2.345)
    actualRectangle.minpoint shouldEqual Point(1.234, 2.345)
    actualRectangle.maxpoint shouldEqual Point(1.345, 2.567)
  }

  test("points on boundaries are contained") {
    val rectangle = Rectangle(1.234, 2.345, 1.345, 2.567)
    assert(rectangle.contains(Point(1.234, 2.345)))
    assert(rectangle.contains(Point(1.345, 2.345)))
    assert(rectangle.contains(Point(1.234, 2.567)))
    assert(rectangle.contains(Point(1.345, 2.567)))
  }

  test("points in rectangle are contained") {
    val rectangle = Rectangle(1.234, 2.345, 1.345, 2.567)
    assert(rectangle.contains(Point(1.235, 2.346)))
  }

  test("points outside of rectangle are not contained") {
    val rectangle = Rectangle(1.234, 2.345, 1.345, 2.567)
    assert(!rectangle.contains(Point(1.234, 4.346)))
    assert(!rectangle.contains(Point(1.534, 2.346)))
    assert(!rectangle.contains(Point(1.134, 2.346)))
    assert(!rectangle.contains(Point(1.334, 2.11)))
  }
}
