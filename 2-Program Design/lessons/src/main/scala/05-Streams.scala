object e_Streams extends App {

  /* Collections and Combinatorial Search
  Scala's immutable collections provide powerful operations,
  in particular for combinatorial search. For example:
  To find the second prime number between 1000 to 10000... */
  def isPrime(n: Int) = (2 until n) forall (d => n % d != 0)
  val range = (1000 to 10000)
  val res1 = range.filter(isPrime)(1)

  /* However, this example's evaluation is very inefficient.
  It has to construct all prime numbers before getting the
  actual result (the 2nd prime number).

  We should avoid computing the tail of a sequence until
  it is needed for the evaluation result (which might be never).
  To do this, we use a lazy collection called the `Stream`.
  A Stream only computes its first value on declaration,
  and then all others are computed when called by name (index).

  Stream.cons(1, Stream.cons(2, Stream.empty))
  // or simply Stream(1, 2)

  The Stream class is similar to List (e.g. isEmpty, head, tail).
  Stream.cons is a non-empty stream, while Stream.empty is empty.
  The core difference is that, for Stream.cons, the tail parameter
  is defined as call-by-name (`=> Stream[T`).

   */

  val stream = (1000 to 10000).toStream // Stream(1000, ?)
  val res2 = stream.filter(isPrime)(1)

  /* Note: The `cons` operator `::` produces a list,
  never a stream, but you can use `#::` to produce a stream
  e.g. `x #:: xs == Stream.cons(x, xs)`

  Note however that you can convert a filtered Stream
  to a list in order to get the whole values... */

  stream.filter(isPrime).take(5).toList
  // List(1009, 1013, 1019, 1021, 1031)

  /* Lazy Evaluation in Scala
  Although Scala uses strict evaluation by default, it allows
  lazy evaluation with the `lazy val` declaration form. Scala
  doesn't default to lazy evaluation because it is unpredictable
  given Scala's allowance of mutable values.

  lazy val x = expr -- computed on first call, then stored
  def(x) = expr -- computed on each call by name

  Another isPrime: The Sieve of Eratosthenes
  - Start with all integers from 2, the first prime number
  - Eliminate all multiples of 2
  - The first element of the resulting list is 3, a prime number
  - Eliminate all multiples of 3...
  - Iterate forever. At each step, the head is the prime. */
  def from(n: Int): Stream[Int] = n #:: from(n + 1)
  def sieve(s: Stream[Int]): Stream[Int] =
    s.head #:: sieve(s.tail filter (_ % s.head != 0))
  val primes = sieve(from(2))
  primes.take(10).toList

  /* Back to Square Roots
  Our previous algorithm for square roots used an `isGoodEnough`
  test to tell when to terminate the iteration. With streams we
  don't need to worry about the termination criteria. */
  def sqrtStream(x: Double): Stream[Double] = {
    def improve(guess: Double) = (guess + x / guess) / 2
    lazy val guesses: Stream[Double] = 1 #:: (guesses map improve)
    guesses
  }

  /* To get the sqrt, we can perform `sqrtStream(4).take(10).toList`.
  But this just gives us a list which might contain the value.
  Note that we can however add the termination criteria as well,
  and then use the `find` method to get the first match in the stream. */
  def isGoodEnough(guess: Double, x: Double) =
    Math.abs((guess * guess - x) / x) < 0.0001
  val sqrt = sqrtStream(4).find(isGoodEnough(_, 4))

  /* Consider two ways to express the infinite stream of multiples of a given 'n'
  The `map` approach is more efficient because it doesn't generate
  unnecessary stream elements that are filtered out afterwards. */
  def xs(n: Int) = from(1) filter (_ % n == 0)
  def ys(n: Int) = from(1) map (_ * n)

}
