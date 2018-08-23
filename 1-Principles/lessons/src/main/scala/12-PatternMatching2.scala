package l

// This is the same Pattern Matching example as before
// except the evaluation function is defined in the base trait
// and more implementations are added

trait Expression {

  def eval: Int = this match {
    case Number(n) => n
    case Variable(_) => throw new Error("Unable to eval Variable")
    case Sum(e1, e2) => (e1 eval) + (e2 eval)
    case Product(e1, e2) => (e1 eval) * (e2 eval)
  }

  def paren: String = this match {
    case Sum(_, _) => "(" + show + ")"
    case Product(_, _) => "(" + show + ")"
    case _ => show
  }

  def show: String = this match {
    case Number(n) => n toString
    case Variable(x) => x
    case Sum(e1, e2) => e1.paren + " + " + e2.paren
    case Product(e1, e2) => e1.paren + " * " + e2.paren
  }

}

case class Number(n: Int) extends Expression
case class Variable(x: String) extends Expression
case class Sum(e1: Expression, e2: Expression) extends Expression
case class Product(e1: Expression, e2: Expression) extends Expression

object PatternMatching2 extends App {
  val two = Number(2)
  val three = Number(3)
  val x = Variable("x")
  val five = Sum(two, three)
  val six = Product(two, three)
  println(five.show + " = " + five.eval)
  println(six.show + " = " + six.eval)
  println(Sum(three, x).show)
  println(Sum(six, x).show)
  println(Product(Sum(five, six), Product(x, two)).show)
}