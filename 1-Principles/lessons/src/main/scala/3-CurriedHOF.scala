package c

object CurriedHOF extends App {

  // curried sumRangeFn
  // sums the results of a range of ints passed into a given func
  // since this belongs to a singleton object, it needs to be assigned to fn
  // in order to be curryable (val fn = curriedSumRangeFn _)
  def sumRange(f: Int => Int)(a: Int, b: Int): Int = {
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

  def factorial(n: Int) = productRange(x => x)(1, n)

  def mapReduce
    (f: Int => Int, combine: (Int, Int) => Int, base: Int)
    (a: Int, b: Int): Int =
      if (a > b) base
      else combine(f(a), mapReduce(f, combine, base)(a + 1, b))

  def productRange2(f: Int => Int)(a: Int, b: Int): Int =
    mapReduce(f, (x, y) => x * y, 1)(a, b)


  def sumRangeFn = sumRange _
  def multRange = sumRangeFn(x => x * x)
  val multToThree = multRange(1, 3)
  println(s"(1 * 1) + (2 * 2) + (3 * 3) = ${multToThree}")

  // mapReduce -> product -> factorial
  val pRange = productRange(x => x + x)(1, 3)
  val fact = factorial(5)
  println(s"(1 + 1) * (2 + 2) * (3 + 3) = ${pRange}")
  println(s"The factorial of 5 is ${fact}")

}