package shapes

import org.scalatest.FunSuite
import org.specs2.matcher.ShouldMatchers.thisValue

import scala.math.sqrt

class Point3DTest extends FunSuite {

  test("creates a valid point") {
    val point = Point3D(10, 20, 30)
    point.x shouldEqual 10
    point.y shouldEqual 20
    point.z shouldEqual 30
  }

  test("creates a valid point from a string") {
    val point = Point3D.fromString("10,20,30")
    Point3D(10, 20, 30) shouldEqual point
  }

  test("throws IllegalArgumentException when string is empty") {
    try {
      Point3D.fromString("")
    } catch {
      case _ : IllegalArgumentException =>
    }
  }

  test("throws IllegalArgumentException when string has only one point") {
    try {
      Point3D.fromString("23.456")
    } catch {
      case _ : IllegalArgumentException =>
    }
  }

  test("throws IllegalArgumentException when point is not valid") {
    try {
      Point3D.fromString("2a.345,-12.34")
    } catch {
      case _ : IllegalArgumentException =>
    }
  }

  test("test distance within") {
    val pointA = Point3D(1, 1, 1)
    val pointB = Point3D(1, 1, 2)
    assert(pointA.within(pointB, 1))
  }

  test("test distance outside within") {
    val pointA = Point3D(1, 1, 1)
    val pointB = Point3D(2, 2, 2)
    assert(!pointA.within(pointB, 1))
  }
}
