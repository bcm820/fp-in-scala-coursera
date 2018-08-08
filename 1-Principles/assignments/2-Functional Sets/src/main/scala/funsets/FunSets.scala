package funsets


/**
 * 2. Purely Functional Sets.
 */
object FunSets {

  /**
   * Mathematically, a function which takes an integer as argument
   * and returns a boolean indicating whether it belongs to a set
   * is called the `characteristic` function of the set.
   *
   * Here a set is represented by its `contains` predicate, defining a type alias:
  */
  type Set = Int => Boolean

  /**
   * Using this representation, we define a function
   * that tests for the presence of a value in a set.
   */
  def contains(s: Set, elem: Int): Boolean = s(elem)

  /**
   * Define a function singletonSet which creates a singleton set from
   * one integer value: the set represents the set of the one given element.
   * It should return a set with the one given element.
  */
  def singletonSet(elem: Int): Set = x => x == elem

  /**
   * Now that we have a way to create singleton sets,
   * we want to define functions to build bigger sets from smaller ones.
   * `union` should return the set of all elements that are in either `s` or `t`.
   * `intersect` should return the set of all elements that are both in `s` and `t`.
   * `diff` should return the set of all elements of `s` that are not in `t`.
   * `filter` selects only the elements of `s` that satisfy predicate `p`.
  */
  def union(s: Set, t: Set): Set = x => contains(s, x) || contains(t, x)
  def intersect(s: Set, t: Set): Set = x => contains(s, x) && contains(t, x)
  def diff(s: Set, t: Set): Set = x => contains(s, x) && !contains(t, x)
  def filter(s: Set, p: Int => Boolean): Set = x => p(x) && contains(s, x)
  

  /**
   * Next, we can define functions for making requests on set elements.
   * `forall` tests whether `p` is true for all elements of the set.
   * `exists` tests whether `p` is true for at least one element of the set.
  */

  // The bounds for `forall` and `exists` are +/- 1000.
  val bound = 1000

  def forall(s: Set, p: Int => Boolean): Boolean = {
    def iter(a: Int): Boolean = {
      if (a > bound) true
      else if (contains(s, a) && !p(a)) false
      else iter(a + 1)
    }
    iter(-bound)
  }
  
  // NOT(for all x we have f(x) == false)
  def exists(s: Set, p: Int => Boolean): Boolean = !forall(s, x => !p(x))
  
  // `map` applies `f` to each element of `s` and returns a new set
  def map(s: Set, f: Int => Int): Set = {
    def iter(s2: Set, a: Int): Set = {
      if (a > bound) diff(s2, s)
      else if (contains(s, a))
        iter(union(s2, singletonSet(f(a))), a + 1)
      else iter(s2, a + 1)
    }
    iter(s, -bound)
  }
  
  /**
   * Displays the contents of a set
  */
  def toString(s: Set): String = {
    val xs = for (i <- -bound to bound if contains(s, i)) yield i
    xs.mkString("{", ",", "}")
  }

  /**
   * Prints the contents of a set on the console.
  */
  def printSet(s: Set) {
    println(toString(s))
  }
}
