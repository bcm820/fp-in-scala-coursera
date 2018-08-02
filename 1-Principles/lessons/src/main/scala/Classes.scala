object Classes {

  def run() = {
    val r1 = new Rational(1, 2); val r2 = new Rational(2, 3)
    
    val addr2 = r1.add(r2)
    val multiplyByr1 = addr2.multiply(r1)
    val subtractByr2 = multiplyByr1.subtract(r2)
    val divideByr1 = subtractByr2.divide(r1)
    val negative = divideByr1.negative()
    val gcd = negative.getGCD()
    val reduced = negative.reduce()
    
    println(s"${r1} + ${r2} = ${addr2}")
    println(s"${addr2} * ${r1} = ${multiplyByr1}")
    println(s"${multiplyByr1} - ${r2} = ${subtractByr2}")
    println(s"${divideByr1} / ${r1} = ${divideByr1}")
    println(s"negative of ${divideByr1}: ${negative}")
    println(s"GCD of ${negative}: ${gcd}")
    println(s"${negative} reduces to ${reduced}")
  }

}

class Rational(x: Int, y: Int) {

  def numer = x
  def denom = y

  def add(that: Rational) =
    new Rational(
      this.numer * that.denom + that.numer * this.denom,
      denom * that.denom // `this` syntax can be implied
    )

  // override java.lang.Object.toString method
  override def toString() = numer + "/" + denom

  def subtract(r: Rational) =
    new Rational(
      numer * r.denom - r.numer * denom,
      denom * r.denom
    )
  
  def multiply(r: Rational) =
    new Rational(
      numer * r.numer,
      denom * r.denom
    )

  def divide(r: Rational) =
    new Rational(
      numer * r.denom,
      denom * r.numer
    )

  def negative() =
    new Rational(
      numer - (numer * 2),
      denom
    )

  def getGCD(): Int =
    if (denom == 0) numer
    else {
      new Rational(
        numer,
        denom % numer
      ).getGCD()
    }

  def reduce() =
    new Rational(
      1, this.getGCD()
    )

}