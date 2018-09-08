package q

object ListHOFs extends App {

  // Example: Multiply each element in a list by the same factor
  def scaleList(xs: List[Int], factor: Int): List[Int] = xs match {
    case Nil     => xs
    case y :: ys => y * factor :: scaleList(ys, factor)
  }
  val xs = List(1, 2, 3)
  scaleList(xs, 2) // List(2, 4, 6)
  xs map (x => x * 2) // easily achieved via map

  // Write a function `pack` that packs consecutive duplicates of list
  // e.g. List(1, 1, 2, 2, 3, 3, 3) => List(List(1,1), List(2,2), List(3,3,3))
  // Then use it to produce the run-length encoding of that list
  // e.g. List(List(1, 2), List(2, 2), List(3, 3))
  def pack[T](xs: List[T]): List[List[T]] = xs match {
    case Nil => Nil
    case y :: ys => {
      val (first, rest) = xs span (x => x == y)
      first :: pack(rest)
    }
  }
  val dups = List(1, 1, 2, 2, 3, 3, 3)
  pack(dups) map (ys => (ys.head, ys.length))

  // Reduction of lists
  // Note underscores. Every _ represents a new parameter.
  // Reduce can only be applied to non-empty lists
  // whereas Fold can be applied to empty lists, since it takes an accumulator
  xs reduceLeft (_ + _) // 6
  (xs foldLeft 1)(_ * _) // 6

  // "Right" operations associate right to left
  // i..e (1 - (2 - 3)) vs ((1 - 2) - 3)
  xs reduceRight (_ + _)
  (xs foldRight 1)(_ * _)

}
