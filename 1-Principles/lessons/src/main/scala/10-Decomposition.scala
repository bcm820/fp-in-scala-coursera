package j

// Suppose you want to write a small interpreter for arithmetic expressions.
// Consider a base trait `Expression` and two subclasses, `Number`, and `Sum`.

// And then later... consider you want to add "Product" and "Variable".
// And what if you wanted to add eval as a method?
// What if you wanted to add + and * as simplified operators?
// You would need to add many more classification and accessor methods.
// This is not desirable. There has to be another way.

// This is what not to do...

trait Expression {
  // classification methods
  def isNumber: Boolean
  def isSum: Boolean
  def numValue: Int
  // accessor methods
  def leftOp: Expression
  def rightOp: Expression
}

class Number(n: Int) extends Expression {
  def isNumber = true
  def isSum = false
  def numValue = n
  def leftOp = throw new Error("Number.leftOp")
  def rightOp = throw new Error("Number.rightOp")
}

class Sum(e1: Expression, e2: Expression) extends Expression {
  def isNumber = false
  def isSum = true
  def numValue = throw new Error("Sum.numValue")
  def leftOp = e1
  def rightOp = e2
}

object Decomposition extends App {
  
  def eval(e: Expression): Int =
    if (e.isNumber) e.numValue
    else if (e.isSum) eval(e.leftOp) + eval(e.rightOp)
    else throw new Error("Unknown expression " + e)

  val two = new Number(2)
  val three = new Number(3)
  val five = new Sum(two, three)
  println(eval(five))

}