package example

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * A test suite is created by defining a class which extends the type
 * `org.scalatest.FunSuite`. When running ScalaTest, it will automatically
 * find this class and execute all of its tests.
 *
 * Adding the `@RunWith` annotation enables the test suite to be executed
 * inside eclipse using the built-in JUnit test runner.
 * Console: Start the sbt console and run the "test" command.
*/
@RunWith(classOf[JUnitRunner])
class ListsSuite extends FunSuite {
 
  /**
   * Tests are written using the `test` operator which takes two arguments:
   * - A unique description of the test.
   * - The test body, a piece of Scala code that implements the test
  */
  test("one plus one is two")(assert(1 + 1 == 2))

  /**
   * In Scala, it is allowed to pass an argument to a method using block
   * syntax, i.e. `{ argument }` instead of parentheses `(argument)`.
   * This allows tests to be written in a more readable manner:
  */
  test("one plus one is not three") {
    assert(1 + 1 != 3)
  }

  /**
   * Using special equality operator `===` instead of `==`
   * (only possible in ScalaTest) gives more detailed output.
   * Always use the `===` equality operator when writing tests.
  */
  test("details why one plus one is not three") {
    // prior to fixing, showed: "*** FAILED *** 2 did not equal 3"
    assert(1 + 1 !== 3)
  }

  /**
   * In order to test exceptions of a method,
   * ScalaTest offers the `intercept` operation.
   */
  def intNotZero(x: Int): Int = {
    if (x == 0) throw new IllegalArgumentException("int is 0")
    else x
  }
  test("intNotZero throws an exception if given int is 0") {
    intercept[IllegalArgumentException](intNotZero(0))
  }


  /**
   * Write tests for `sum` and `max` methods.
   * In particular, write tests for corner cases:
   * negative numbers, zeros, empty lists, lists with repeats, etc.
   */
  import Lists._

  // sum
  test("sum of a list of ints")(assert(sum(List(1,2,0)) === 3))
  test("sum of a list of negative ints")(assert(sum(List(-1,-2,-3)) === -6))
  test("sum of a list of zeros")(assert(sum(List(0,0,0)) === 0))
  test("sum of a list of repeats")(assert(sum(List(7,7,7)) === 21))
  test("sum of an empty list")(assert(sum(List()) === 0))

  // max
  test("max of a list of ints")(assert(max(List(3, 7, 2)) === 7))
  test("max of a list of negative ints")(assert(max(List(-1,-2,-3)) === -1))
  test("max of a list of zeros")(assert(sum(List(0,0,0)) === 0))
  test("max of a list of repeats")(assert(max(List(7,7,7)) === 7))
  test("max of an empty list throws NoSuchElementException") {
    intercept[NoSuchElementException](max(List()))
  }

}
