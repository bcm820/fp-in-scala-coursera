package e

// Abstract classes contain members missing implementation
// and cannot be instantiated (i.e. via `new` keyword)
abstract class IntSet {
  def incl(x: Int): IntSet
  def contains(x: Int): Boolean
  def union(other: IntSet): IntSet
}

// Here we implement sets as binary trees.

// First we start with an empty tree, for creating an IntSet that does not yet have nodes.
// It should be a singleton object, since only one empty IntSet is needed
// (Once `incl` is called on the Empty, it returns a NonEmpty)
// Singleton objects are values, not classes, so referring to `Empty` evaluates to itself.
object Empty extends IntSet {
  def incl(x: Int): IntSet = new NonEmpty(x)
  def contains(x: Int): Boolean = false
  def union(other: IntSet): IntSet = other // since the initial is empty
  override def toString = "."
}

// A non-empty tree consisting of an integer node with two sub-trees
class NonEmpty(elem: Int, left: IntSet, right: IntSet) extends IntSet {

  def this(x: Int) = this(x, Empty, Empty)
  
  def incl(x: Int): IntSet =
    if (x < elem) new NonEmpty(elem, left incl x, right) // note no mutation
    else if (x > elem) new NonEmpty(elem, left, right incl x)
    else this // element is already in tree, return original

  def contains(x: Int): Boolean =
    if (x < elem) left contains x
    else if (x > elem) right contains x
    else true // element is found

  // Two join two NonEmpty sets, we split the original set into its constituents,
  // unifying the left and right subsets, and then the new set, and finally the main elem.
  // The initial union call on `left` is on something smaller than the original
  def union(other: IntSet): IntSet = ((left union right) union other) incl elem

  override def toString = s"{ ${left} ${elem} ${right} }"

}

// IntSet is the superclass, and Empty and NonEmpty are subclasses.
// By default, the non-specified superclass of a class is standard class Object.
// For the following subclasses, their `base` classes are IntSet and Object.

// Key Concept: Dynamic Binding / Dynamic Dispatch
// Which `contains`, `incl`, or `union` is called?
// It depends on if the runtime value is of type Empty or NonEmpty.
// Dynamic binding means the method invoked relies on the runtime value.

object ClassHierarchies extends App {
  val t1 = new NonEmpty(6) incl 4 incl 10 incl 2 incl 8
  val t2 = new NonEmpty(5) incl 7 incl 3 incl 1 incl 9
  val t3 = t1 union t2
  println(t3)
}