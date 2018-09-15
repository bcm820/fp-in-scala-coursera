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

  Stream.cons(1, Stream.cons(2, Stream.empty))
  or simply Stream(1, 2)

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

  println(stream.filter(isPrime).take(5).toList)
  // List(1009, 1013, 1019, 1021, 1031)

}
