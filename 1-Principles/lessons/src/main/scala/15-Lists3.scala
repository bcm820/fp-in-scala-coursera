package o

object Lists3 extends App {

  // Merge Sort with type parameterization
  def mergeSort[T](xs: List[T])(lt: (T, T) => Boolean): List[T] = {
    val n = xs.length / 2
    if (n == 0) xs
    else {

      // If working with Ints, we can compare via `x < y`
      // but if T is not an Int, we need a separate comparator (lt)
      def merge(xs: List[T], ys: List[T]): List[T] =
        (xs, ys) match {
          case (Nil, ys) => ys
          case (xs, Nil) => xs
          case (x :: xs1, y :: ys1) =>
            if (lt(x, y)) x :: merge(xs1, ys)
            else y :: merge(xs, ys1)
        }

      val (fst, snd) = xs splitAt n
      merge(mergeSort(fst)(lt), mergeSort(snd)(lt))
    }
  }

  // using a basic Int lt operator
  val nums = List(2, -4, 5, 7, 1)
  val sortedNums = mergeSort(nums)((x, y) => x < y)
  println(sortedNums) // List(-4, 1, 2, 5, 7)

  // using Java's String compareTo
  // which returns -1 if lt, 0 if equal, 1 if gt
  val fruits = List("apple", "pineapple", "orange", "banana")
  val sortedFruits = mergeSort(fruits)((x, y) => x.compareTo(y) < 0)
  println(sortedFruits) // List(apple, banana, orange, pineapple)

  // NOTE: No need to specify the type of `x` and `y`
  // since the Scala compiler infers it from the type of nums
  // Generally, in a parameter list, put the function value last
  // since its param types can be inferred from the previous
}
