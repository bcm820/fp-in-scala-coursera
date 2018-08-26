package m

// Lists are sequences like arrays, but
// - Lists are immutable -- its elements can't change
// - Lists are recursive while arrays are flat

// Lists are homogeneous, having the same type (i.e. List[T])

object Lists extends App {

  // Syntactic sugar for lists: List(x1 ... xn)
  val fruit: List[String] = List("apples", "oranges", "pears")
  val nums: List[Int] = List(1, 2, 3, 4)
  val diag: List[List[Int]] = List(List(1, 0, 0), List(0, 1, 0), List(0, 0, 1))
  val empty: List[Nothing] = List()

  // The actual construction operation is `::` (referred to as `cons`)
  // e.g. new Cons(x, xs) == x :: xs
  // Note that Nil is required as the last element always points to Nil
  val nums2 = 1 :: (2 :: (3 :: (4 :: Nil)))

  // Since operators ending in `:` associate to the right
  // we can thus omit the parentheses in the definition
  val nums3 = 1 :: 2 :: 3 :: 4 :: Nil

  // Operators ending in `:` are method calls of the right-hand operand
  // So the `::` operator is actually the `prepend` method call on a List
  val nums4 = Nil.::(4).::(3).::(2).::(1)

  // Main operations on lists:
  val head = fruit.head // "apples"
  val tail = fruit.tail // List("oranges", "pears"))
  // val isEmpty = empty.head // throw new NoSuchElementException())

  // With pattern matching
  def matchList(list: List[Any]): String = list match {
    case "apples" :: _    => "starts with apples"
    case 1 :: 2 :: _      => "starts with 1 and 2"
    case Nil              => "empty list" // also use List()
    case _ :: Nil         => "has length of 1" // also use List(_)
    case List(2 :: _)     => "has a single elem, a list starting with 2"
    case List(1, 3, 5, 7) => "exact match"
    case _                => ":( no match found :("
  }

  println(matchList(fruit))
  println(matchList(nums))
  println(matchList(empty))
  println(matchList(List(1)))
  println(matchList(List(List(2, 4, 6, 8))))
  println(matchList(List(1, 3, 5, 7)))

  // Although List has a sort method already, how to implement one?
  // One way is to sort the tail, and insert the head in the right place.

  def insertionSort(list: List[Int]): List[Int] = list match {
    case List()       => Nil
    case head :: tail => insert(head, insertionSort(tail))
  }

  def insert(el: Int, tail: List[Int]): List[Int] = tail match {
    case List() => List(el) // if list is empty, insert el
    case tailHead :: tailTail =>
      if (el <= tailHead) el :: tail // prepend el
      else tailHead :: insert(el, tailTail) // prepend tailHead
  }

  println(insertionSort(List(5, 7, 2, 3, 9, 1)))

}
