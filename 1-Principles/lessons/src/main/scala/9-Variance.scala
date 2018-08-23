package i

// trait VList is declared as covariant
// It can be extended by any subtype of a given `T`
trait VList[+T] {
  def isEmpty: Boolean
  def head: T
  def tail: VList[T]

  // in order to add a `prepend` method to this VList,
  // we must require its parameter to be a supertype of T
  def prepend[U >: T](elem: U): VList[U] = new VCons(elem, this)
}

class VCons[T](val head: T, val tail: VList[T]) extends VList[T] {
  def isEmpty = false
}

// object VNil cannot have a type parameter (only one instance)
// It can extend VList[Nothing] (Nothing subtypes all other types)
object VNil extends VList[Nothing] {
  def isEmpty = true
  def head: Nothing = throw new NoSuchElementException("Nil.head")
  def tail: Nothing = throw new NoSuchElementException("Nil.tail")
}

object Variance extends App {

  // Here we declare `x` as a VList[String], initialized to `VNil`.
  // This works since Nothing is a subtype of String
  val xs: VList[String] = VNil
  val ys = xs.prepend("hello")
  println(ys head)

}
