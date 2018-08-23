package d

// in Scala, class implicitly introduces a constructor
// this is the primary constructor of the class
class Rational(x: Int, y: Int) {
  
  // `require` validates constructor calls
  // can also be used for functions as well
  // if condition is false, throws IllegalArgumentException
  require(y > 0, "Denominator must be positive")

  // `this` an auxiliary constructor
  // if no denominator is given, default to 1
  def this(x: Int) = this(x, 1)
  
  // given any numer and denom, find the GCD
  private def gcd(n: Int, d: Int): Int =
    if (d == 0) n else gcd(d, n % d)

  // store two public members, simplified numer and denom
  // note these are evaluated immediately and values are amortized
  // it is good to simplify right away to minimize computations
  // there are many ways we could've implemented the data
  // but to the client it is all the same (data abstraction)
  val numer = x / gcd(x, y)
  val denom = y / gcd(x, y)

  // override java.lang.Object.toString method
  // when appended to string, this will automatically be called
  // note also that self-refrence does not require `this`
  override def toString() = numer + "/" + denom

  def plus(r: Rational) =
    new Rational(
      numer * r.denom + r.numer * denom,
      denom * r.denom
    )

  def minus(r: Rational) =
    new Rational(
      numer * r.denom - r.numer * denom,
      denom * r.denom
    )
  
  def times(r: Rational) =
    new Rational(
      numer * r.numer,
      denom * r.denom
    )

  def dividedBy(r: Rational) =
    new Rational(
      numer * r.denom,
      denom * r.numer
    )

  def less(that: Rational) =
    this.numer * that.denom < that.numer * this.denom

  def max(that: Rational) = if (this.less(that)) that else this

  def toNegative() =
    new Rational(
      numer - (numer * 2),
      denom
    )
  
  def < (that: Rational) = this less that
  def ^ (that: Rational) = this max that

  def + (that: Rational) = this plus that
  def - (that: Rational) = this minus that
  def * (that: Rational) = this times that
  def / (that: Rational) = this dividedBy that

}


object Classes extends App {
    
  val a = new Rational(1, 3)
  val b = new Rational(3, 4)
  val c = new Rational(5, 6)
  val d = new Rational(8) // see aux constructor below
    
  // method chaining
  val result = a.plus(b).minus(c).times(d).dividedBy(c)
  println(s"// ${result}")

  // infix notation
  // for methods that take one arg (also zero)
  val result2 = a plus b minus c times d dividedBy c
  println(s"// ${result2}")

  // relaxed identifiers
  // NOTE: precedence rules are determined by character
  // alphanumeric characters are first,
  // then special characters have a distinct order
  // so we force precedence with parens
  val result3 = ((((a + b) - c) * d) / c)
  println(s"// ${result3}")

  val comparison = a < b
  val comparison2 = c ^ d
  println(s"// ${comparison}")
  println(s"// ${comparison2}")

}