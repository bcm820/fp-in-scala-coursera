object Lessons extends App {

  // Newton's square root method
  val sqrtOf2 = NewtonSqrtMethod.sqrt(2)
  println(s"The square root of 2 is ${sqrtOf2}")

  // Tail recursion implementation of factorial
  val factorialOf5 = TailRecFactorial.factorial(5)
  println(s"The factorial of 5 is ${factorialOf5}")

  // HOF
  val sumToThree = CurriedHOF.sumRangeFn(x => x + x, 1, 3)
  println(s"1 + 1 + 2 + 2 + 3 + 3 = ${sumToThree}")

  // Curried HOF
  val sumRangeFn = CurriedHOF.curriedSumRangeFn _
  val multRange = sumRangeFn(x => x * x)
  val multToThree = multRange(1, 3)
  println(s"(1 * 1) + (2 * 2) + (3 * 3) = ${multToThree}")

  // mapReduce -> product -> factorial
  val pRange = CurriedHOF.productRange(x => x + x)(1, 3)
  println(s"(1 + 1) * (2 + 2) * (3 + 3) = ${pRange}")

  val fact = CurriedHOF.factorial(5)
  println(s"The factorial of 5 is ${fact}")

}