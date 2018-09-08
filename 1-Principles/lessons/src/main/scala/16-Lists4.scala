package p
import math.Ordering

object Lists4 extends App {

  /**
    * Merge Sort with IMPLICIT type parameterization
    * Ordering is a type extended by Ordering.Int, Ordering.String, etc.
    * `T` of Ordering will be implied from the function call
    * So no need to curry call `ord` due to implicit (magic!)
    *
    * If a function takes an implicit paramter of type `T`
    * The compiler will search an implicit definition that:
    * - is marked implicit
    * - has a type compatible with `T`
    * - is visible at the point of the function call (Example A)
    *   or is defined in a companion object associated with `T` (Example B)
    * If there is a single definition, it will be taken as an
    * actual argument for the implicit parameter.
    */
  def mergeSort[T](xs: List[T])(implicit ord: Ordering[T]): List[T] = {
    val n = xs.length / 2
    if (n == 0) xs
    else {

      def merge(xs: List[T], ys: List[T]): List[T] =
        (xs, ys) match {
          case (Nil, ys) => ys
          case (xs, Nil) => xs
          case (x :: xs1, y :: ys1) =>
            if (ord.lt(x, y)) x :: merge(xs1, ys)
            else y :: merge(xs, ys1)
        }

      val (fst, snd) = xs splitAt n
      merge(mergeSort(fst), mergeSort(snd)) // * Example A
    }
  }

  val nums = List(2, -4, 5, 7, 1)
  val fruits = List("apple", "pineapple", "orange", "banana")
  val sortedNums = mergeSort(nums) // * Example B
  val sortedFruits = mergeSort(fruits) // * Example B
  println(sortedNums) // List(-4, 1, 2, 5, 7)
  println(sortedFruits) // List(apple, banana, orange, pineapple)
}
