package shapes

import org.scalatest.FunSuite
import org.specs2.matcher.ShouldMatchers.thisValue

class Rectangle3DTest extends FunSuite {

  test("throws InvalidArgumentException if points string does not contain enough params") {
    try {
      Rectangle3D.fromString("1.345")
    } catch {
      case _: IllegalArgumentException =>
    }
  }

  test("throws InvalidArgumentException if points string does not contain 6 points") {
    try {
      Rectangle3D.fromString("1.345,-2.345,1.567,-3.4567,1")
    } catch {
      case _: IllegalArgumentException =>
    }
  }

  test("Creates a Rectangle from a valid string") {
    val rectangles = Rectangle3D.fromString("1,1,1,2,2,2")
    rectangles shouldEqual Rectangle3D.apply(1, 1, 1, 2, 2, 2)
  }

  test("points on boundaries are contained") {
    val rectangle = Rectangle3D.apply(1, 1, 1, 2, 2, 2)
    assert(rectangle.contains(Point3D(1, 1.5, 1.5)))
    assert(rectangle.contains(Point3D(2, 1.5, 1.5)))
    assert(rectangle.contains(Point3D(1.5, 1, 1.5)))
    assert(rectangle.contains(Point3D(1.5, 2, 1.5)))
    assert(rectangle.contains(Point3D(1.5, 1.5, 1)))
    assert(rectangle.contains(Point3D(1.5, 1.5, 2)))
  }

  test("points in rectangle are contained") {
    val rectangle = Rectangle3D.apply(1, 1, 1, 2, 2, 2)
    assert(rectangle.contains(Point3D(1.5, 1.5, 1.5)))
  }

  test("points outside of rectangle are not contained") {
    val rectangle = Rectangle3D.apply(1, 1, 1, 2, 2, 2)
    assert(!rectangle.contains(Point3D(1, 1, 2.5)))
    assert(!rectangle.contains(Point3D(1, 2.5, 1)))
    assert(!rectangle.contains(Point3D(2.5, 1, 1)))
  }
}
