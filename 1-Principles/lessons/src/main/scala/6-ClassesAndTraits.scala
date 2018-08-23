package f

// Traits resemble interfaces,
// but traits can contain fields and methods.
trait Shape {
  def sides: Int
  def is3D = true // can be overridden
  def dimensions = if (is3D) 3 else 1
}

// Classes and objects may only inherit from a single SuperClass
// but they can inherit from many traits.
trait Movable {
  def x = 0
  def y = 0
}

// Traits can't have value parameters -- only classes can.
class Square(h: Int, w: Int) extends Shape with Movable {
  def sides = 4
  def printInfo() = {
    println(s"I am a ${this.getClass.getSimpleName}.")
    println(s"I have ${sides} sides.")
    println(s"I have ${dimensions} dimensions.")
    println(s"My current position is ${x}x, ${y}y.")
  }
}

object ClassesAndTraits extends App {
  new Square(2, 2) printInfo
}