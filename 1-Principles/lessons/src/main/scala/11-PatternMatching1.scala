package k

// This is how Scala implements functional decomposition.
// Case classes are special subclasses of traits.
trait Expression
case class Number(n: Int) extends Expression
case class Sum(e1: Expression, e2: Expression) extends Expression
case class Product(e1: Expression, e2: Expression) extends Expression

object PatternMatching1 extends App {
  
  // Case classes are accessed as via the keyword `match`,
  // allowing for parameterized return value types
  // (i.e. depending on what `e` matches)
  def eval(e: Expression): Int = e match {
    case Number(n) => n
    case Sum(e1, e2) => eval(e1) + eval(e2)
    case Product(e1, e2) => eval(e1) * eval(e2)
  }

  // It also conveniently defines companion objects with factory methods
  // e.g. Number(n), Sum(e1, e2)
  val two = Number(2)
  val three = Number(3)
  val five = Sum(two, three)
  val six = Product(two, three)
  println(eval(five))
  println(eval(six))

}