object Lessons extends App {

  // Newton's square root method
  val sqrtOf2 = NewtonSqrtMethod.sqrt(2)
  println(s"The square root of 2 is ${sqrtOf2}")

  // Tail recursion implementation of factorial
  val factorialOf5 = TailRecFactorial.factorial(5)
  println(s"The factorial of 3 is ${factorialOf5}")

}