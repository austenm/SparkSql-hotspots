package shapes

import org.scalatest.FunSuite
import org.specs2.matcher.ShouldMatchers.thisValue

class PointTest extends FunSuite {

  test("throws IllegalArgumentException when string is empty") {
    try {
      Point.fromString("")
    } catch {
      case _ : IllegalArgumentException =>
    }
  }

  test("throws IllegalArgumentException when string has only one point") {
    try {
      Point.fromString("23.456")
    } catch {
      case _ : IllegalArgumentException =>
    }
  }

  test("throws IllegalArgumentException when string has more than two points") {
    try {
      Point.fromString("23.456,-12.34,24.123")
    } catch {
      case _ : IllegalArgumentException =>
    }
  }

  test("creates a valid point from a string") {
    val point = Point.fromString("10,20")
    point shouldEqual Point(10, 20)
  }

  test("creates a valid point") {
    val point = Point(10, 20)
    point.x shouldEqual 10
    point.y shouldEqual 20
  }

  test("point is within") {
    val point1 = Point(10, 20)
    val point2 = Point(20, 20)
    assert(point1.within(point2, 10))
  }

  test("point is not within") {
    val point1 = Point(10, 20)
    val point2 = Point(20, 20)
    assert(!point1.within(point2, 5))
  }
}
