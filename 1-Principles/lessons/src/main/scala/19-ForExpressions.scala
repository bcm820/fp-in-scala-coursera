package s

object ForExpressions extends App {

  // Given a positive integer n,
  // find all pairs (i, j) of positive integers
  // between 1 and n, such that i + j is prime.
  def isPrime(n: Int) = (2 until n) forall (d => n % d != 0)

  // Basic Functional Approach
  // 1. Generate sequence of pairs (i, j) up to n.
  // 2. Filter the pairs for which i + j is prime.
  def seqPosPairs(n: Int) =
    // Generate all integers between 1 and n
    (1 until n) flatMap (i =>
      // For each i, generate a sequence of pairs (i, j)
      (1 until i) map (j => (i, j))
      // Filter the list of pairs via isPrime
    ) filter (t => isPrime(t._1 + t._2))

  // For-Expression Approach
  def genPosPairs(n: Int) =
    for {
      i <- 1 until n
      j <- 1 until i
      if isPrime(i + j)
    } yield (i, j)

  println(seqPosPairs(7))
  println(genPosPairs(7))

  // Re-implement scalarProduct using a for-expression
  def scalarProduct(xs: Vector[Int], ys: Vector[Int]) =
    (for ((x, y) <- xs zip ys) yield x * y) sum

  println(scalarProduct(Vector(1, 2, 3), Vector(4, 5, 6)))

}
