object c_TranslationOfFor extends App {

  // Scala's compilser translates for-expressions to
  // three main HOFs: map, flatMap, and filter
  val xs = List(1, 2, 3, 4, 5)

  // MAP
  for (x <- xs) yield x + 1 // evaluates to `e1.map(_ + 1)`

  // FILTER
  val p: Int => Boolean = x => x > 2
  for (x <- xs if p(x)) yield x
  // evaluates to `for (x <- e1.withFilter(x => f)) yield x`
  // withFilter is a lazy variant of filter that does not allocate a list
  // but forwards the passing elements to the  map/flatMap function
  // It is in a sense an `enhanced` filter HOF
  xs.withFilter(e => e > 3) // TraversableLike$WithFilter@61c8ce67
    .map(_ + 1) // List(4, 5) => List(5, 6)

  // FLATMAP
  def f(x: Int): List[Int] = List(x - 1, x, x + 1)
  for (x <- xs; y <- f(x)) yield y
  // evalutes to `xs.flatMap(x => for (y <- f(x)) yield y)`

  // Example
  def isPrime(n: Int) = (2 until n) forall (d => n % d != 0)

  // This for-expression...
  for {
    i <- 1 until 10
    j <- 1 until i
    if isPrime(i + j)
  } yield (i, j)

  // ...translates to these calls.
  (1 until 10) flatMap (i =>
    (1 until i)
      .withFilter(j => isPrime(i + j))
      .map(j => (i, j)))

}
