package r

object Combinations extends App {

  // List all combinations of numbers x and y
  // where x is drawn from 1..M and y from 1..N:
  def listCombos(M: Int, N: Int) =
    (1 to M) flatMap (x => 1 to N map (y => (x, y)))
  println(listCombos(4, 5))

  // Compute the scalar product of two vectors
  // The sum of corresponding elements
  // Here we pattern match against the pair to get each x and y
  def scalarProduct(xs: Vector[Int], ys: Vector[Int]): Int =
    (xs zip ys).map { case (x, y) => x * y }.sum
  // Note: This is a shorthand version of match case
  println(scalarProduct(Vector(1, 2, 3), Vector(4, 5, 6)))

  // isPrime
  def isPrime(n: Int): Boolean = (2 until n) forall (d => n % d != 0)

}
