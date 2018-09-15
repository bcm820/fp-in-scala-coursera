object d_FunctionalRandomGenerators extends App {

  /* For-expressions are not restricted to collections.
  There are many domains to apply for-expressions to,
  such as random value generators.

  Here is a systematic way to get random values for
  booleans, strings, pairs and tuples, lists, sets, trees. */

  trait Generator[+T] {

    self => // ref to `this` of Generator
    def generate: T // main fn to be implemented

    // map implementation used by for-expression
    def map[S](f: T => S): Generator[S] = new Generator[S] {
      def generate = f(self.generate)
    }

    // flatMap implementation used by for-expression
    def flatMap[S](f: T => Generator[S]): Generator[S] =
      new Generator[S] {
        def generate = f(self.generate).generate
      }
  }

  // basic Int generator, used by other generators
  val randInts = new Generator[Int] {
    val rand = new java.util.Random
    def generate = rand.nextInt()
  }

  // basic boolean generator
  def randBooleans = for (x <- randInts) yield x > 0 // uses map

  // basic pairs generator
  def randPairs[T, U](t: Generator[T], u: Generator[U]) =
    for (x <- t; y <- u) yield (x, y) // uses flatMap

  // Generate a pair from two generators,
  // one that generates a random Boolean
  // and another that generates a random Int
  randPairs(randBooleans, randInts).generate

  /* Additional generator examples:
  - `of` returns a generator with a given wrapped value
  - `intBetween` returns a random Int between a lo and hi Int
  - `oneOf` randomly returns one of many args passed to it */

  def of[T](x: T): Generator[T] = new Generator[T] {
    def generate = x
  }

  def intBetween(lo: Int, hi: Int): Generator[Int] =
    for (x <- randInts) yield Math.abs(lo + x % (hi - lo))

  def oneOf[T](xs: T*): Generator[T] =
    for (idx <- intBetween(0, xs.length)) yield xs(idx)

  // A random List generator (either empty or non-empty)
  // It will keep generating until it returns an empty list
  def randIntsList: Generator[List[Int]] = {

    def emptyList = of(Nil)

    def nonEmptyList =
      for {
        head <- randInts
        tail <- randIntsList
      } yield head :: tail

    for {
      isEmpty <- randBooleans
      list <- if (isEmpty) emptyList else nonEmptyList
    } yield list
  }

  // Exercise: Implement a Tree generator
  trait Tree
  case class Inner(left: Tree, right: Tree) extends Tree
  case class Leaf(x: Int) extends Tree

  def randTrees: Generator[Tree] = {

    def randLeaves: Generator[Leaf] =
      for (x <- randInts) yield Leaf(x)

    def randInners: Generator[Inner] =
      for {
        l <- randTrees
        r <- randTrees
      } yield Inner(l, r)

    for {
      isLeaf <- randBooleans
      tree <- if (isLeaf) randLeaves else randInners
    } yield tree
  }

  // Random Tests
  // Note: See ScalaCheck (based on QuickCheck) for a better implementation
  // Rather than write tests, write properties assumed to hold
  // If a test fails, it can minimize the counter-example
  def test[T](g: Generator[T], times: Int = 100)(test: T => Boolean): Unit = {
    for (i <- 0 until times) {
      val value = g.generate
      assert(test(value), "test failed for " + value)
    }
    println("passed " + times + " tests")
  }

  test(randPairs(randIntsList, randIntsList)) {
    case (xs, ys) => (xs ++ ys).length > xs.length
  }

}
