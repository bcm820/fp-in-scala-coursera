object TailRecFactorial {

  def run() = println(s"The factorial of 5 is ${factorial(5)}")

  // A tail recursive version of the factorial algorithm.
  def factorial(n: Int): Int = {
    def trFactorial(n: Int, acc: Int): Int =
      if (n == 0) acc
      else trFactorial(n - 1, n * acc)
    trFactorial(n, 1)
  }

}