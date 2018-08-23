package h

// Example 1: Pure implementation of primitive types as objects
// An implementation of non-negative integers ("Peano numbers")
abstract class Nat {
  def isZero: Boolean
  def predecessor: Nat
  def successor = new Succ(this)
  def + (that: Nat): Nat
  def - (that: Nat): Nat
}

object Zero extends Nat {
  def isZero = true
  def predecessor = throw new Error("0.predecessor")
  def + (that: Nat) = that
  def - (that: Nat) = if (that.isZero) this else predecessor
}

class Succ(n: Nat) extends Nat {
  def isZero = false
  def predecessor = n
  def + (that: Nat) = new Succ(n + that)
  def - (that: Nat) = if (that.isZero) this else n - that.predecessor
}

// Example 2: Pure implementation of functions as objects
// Consider our previous implementation of a linked List...
import g._

// Define an object List as a compainion to trait List from Lesson 7
// with 3 functions in it so users can create lists of length 0-2
// List() should create an empty list
// List(1) should create a list with a single element 1
// List(2, 3) should create a list elements 2 and 3
object List {
  def apply[T]() = new Nil
  def apply[T](x: T): List[T] = new Cons(x, new Nil)
  def apply[T](x1: T, x2: T): List[T] = new Cons(x1, new Cons(x2, new Nil))
}

object PureImplementations extends App {
  val x = List()
  val x1 = List(1)
  val x2 = List(2, 3)
  println(x isEmpty) // true
  println(x1 head) // 1
  println(x2.tail.head) // 3
}