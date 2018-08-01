object CurriedHOF {

  // tail recursive sumRangeFn
  // sums the results of a range of ints passed into a given func
  def sumRangeFn(f: Int => Int, a: Int, b: Int): Int = {
    def loop(a: Int, acc: Int): Int =
      if (a > b) acc
      else loop(a + 1, acc + f(a))
    loop(a, 0)
  }

  // curried sumRangeFn
  // since this belongs to a singleton object, it needs to be assigned to fn
  // in order to be curryable (val fn = curriedSumRangeFn _)
  def curriedSumRangeFn(f: Int => Int)(a: Int, b: Int): Int = {
    def loop(a: Int, acc: Int): Int =
      if (a > b) acc
      else loop(a + 1, acc + f(a))
    loop(a, 0)
  }

  /*
  1. Write a product function that calculates the product
  of the values of a function for a range of integers.
  2. Write factorial in terms of product.
  3. Write `mapReduce`, which generalizes both sum and product.
  */

  def productRange(f: Int => Int)(a: Int, b: Int): Int =
    if (a > b) 1
    else f(a) * productRange(f)(a + 1, b)

  def mapReduce
    (f: Int => Int, combine: (Int, Int) => Int, base: Int)
    (a: Int, b: Int): Int =
      if (a > b) base
      else combine(f(a), mapReduce(f, combine, base)(a + 1, b))

  def productRange2(f: Int => Int)(a: Int, b: Int): Int =
    mapReduce(f, (x, y) => x * y, 1)(a, b)

  def factorial(n: Int) = productRange2(x => x)(1, n)

}