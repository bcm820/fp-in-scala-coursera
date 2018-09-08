package n

object Lists2 extends App {

  // Merge Sort with Ints only
  def mergeSort(xs: List[Int]): List[Int] = {
    val n = xs.length / 2
    if (n == 0) xs
    else {
      val (fst, snd) = xs splitAt n // decompose
      merge(mergeSort(fst), mergeSort(snd))
    }
  }

  def merge(xs: List[Int], ys: List[Int]): List[Int] =
    (xs, ys) match { // pattern match with decomposition
      case (Nil, ys) => ys // if one list is empty
      case (xs, Nil) => xs // just return the other list
      case (x :: xs1, y :: ys1) =>
        if (x < y) x :: merge(xs1, ys)
        else y :: merge(xs, ys1)
    }

  val nums = List(2, -4, 5, 7, 1)
  val sortedNums = mergeSort(nums)
  println(sortedNums) // List(-4, 1, 2, 5, 7)
}
