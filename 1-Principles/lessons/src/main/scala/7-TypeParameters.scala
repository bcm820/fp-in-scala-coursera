package g

// Type Parameters
// To generalize type definitions, use type parameterization
// which is specified in square brackets, with a T enclosed
trait List[T] {
  def isEmpty: Boolean // is it a Cons or Nil?
  def head: T
  def tail: List[T]
}

// Value Parameters
// By using `val`, we define a field in the class
class Cons[T](val head: T, val tail: List[T]) extends List[T] {
  def isEmpty = false
}

// Since Nil has no head or tail, we should throw an exception on ref
// Exceptions are of type Nothing, which is a subtype of all AnyRefs
class Nil[T] extends List[T] {
  def isEmpty = true
  def head: Nothing = throw new NoSuchElementException("Nil.head")
  def tail: Nothing = throw new NoSuchElementException("Nil.tail")
}

object TypeParameters extends App {

  // Type Parameters in functions
  // singleton creates a new List with a head and a tail (an empty list)
  def singleton[T](elem: T) = new Cons[T](elem, new Nil[T])
  val a = singleton[Int](1)
  val b = singleton[Boolean](true)

  // Note: Type Parameters are often inferred from value args
  val c = singleton(1)
  val d = singleton(true)

  // In fact, type parameters do not affect evaluation in Scala.
  // This is `type erasure`. Types are erased at runtime. Same as with Java.

  // EXERCISE:
  // Write function `nth` that takes an integer `n` and a list and selects the n`th element.
  // Elements are numbered from 0. If index is out of range, throw `IndexOutOfBoundsException`.
  def nth[T](n: Int, list: List[T]): T =
    if (list isEmpty) throw new IndexOutOfBoundsException
    else if (n == 0) list head
    else nth(n-1, list tail)

  val x = nth(2, new Cons("Zero", new Cons("One", new Cons("Two", new Nil))))
  println(x)

}
