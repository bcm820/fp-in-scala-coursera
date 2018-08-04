package funsets

import org.scalatest.FunSuite


import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * This class is a test suite for the methods in object FunSets. To run
 * the test suite, you can either:
 *  - run the "test" command in the SBT console
 *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
 */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {

  /**
   * Link to the scaladoc - very clear and detailed tutorial of FunSuite
   * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
   *
   * Operators
   *  - test
   *  - ignore
   *  - pending
   *
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   *   val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   */

  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
  }

  test("singletonSet(1) contains 1") {
    // We create a new instance of the "TestSets" trait
    // to give us access to the values "s1" to "s3".
    new TestSets {
      assert(contains(s1, 1), "Singleton")
    }
  }

  test("union contains all elements of each set") {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }

  test("intersect contains all elements in each set") {
    new TestSets {
      val s = intersect(union(s1, s2), union(s3, s1))
      assert(contains(s, 1), "Intersect 1")
      assert(!contains(s, 2), "Intersect 2")
    }
  }

  test("diff contains elements in `a` not found in `b`") {
    new TestSets {
      val s = diff(union(s1, s2), union(s3, s1))
      assert(contains(s, 2), "Intersect 1")
      assert(!contains(s, 1), "Intersect 2")
    }
  }

  test("filter selects elements satisfying a predicate") {
    new TestSets {
      val s = filter(union(s1, s2), x => x % 2 == 0)
      assert(contains(s, 2), "Filter 1")
      assert(!contains(s, 3), "Filter 2")
    }
  }

  test("forall tests each elements with a predicate") {
    new TestSets {
      val s = union(s1, s3)
      assert(forall(s, x => x % 2 == 1), "Forall 1")
      assert(!forall(s, x => x % 2 == 0), "Forall 2")
    }
  }

  test("exists tests for a single element satisfying a predicate") {
    new TestSets {
      val s = union(s1, s2)
      assert(exists(s, x => x == 1), "Exists 1")
      assert(!exists(s, x => x == 3), "Exists 2")
    }
  }

  test("map applies `f` to each element and returns a new set") {
    new TestSets {
      val s = map(s1, x => x + 1)
      printSet(s)
      assert(contains(s, 2), "Map 1")
      assert(!contains(s, 1), "Map 2")
    }
  }

}
