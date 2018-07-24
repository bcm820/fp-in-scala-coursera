package example

object Lists {

  /**
   * Computes the sum of all elements in the list xs.
   * Use the following methods in class `List`:
   *  - `xs.isEmpty: Boolean` returns `true` if the list `xs` is empty.
   *  - `xs.head: Int` returns the head element of the list `xs`.
   *    If empty an exception is thrown.
   *  - `xs.tail: List[Int]` returns the list without the `head` element.
   *  ''Hint:'' instead of writing a loop, think of a recursive solution.
   * @param xs A list of natural numbers
   * @return The sum of all elements in `xs`
  */
  def sum(xs: List[Int]): Int = {
    if (xs.isEmpty) 0
    else _trackSum(xs, 0)
  }
  def _trackSum(xs: List[Int], sum: Int): Int = {
    if (xs.isEmpty) sum
    else _trackSum(xs.tail, sum + xs.head)
  }

  /**
   * This method returns the largest element in a list of integers.
   * If empty an exception is thrown.
   * ''Hint:'' Again, think of a recursive solution.
   * @param xs A list of natural numbers
   * @return The largest element in `xs`
   * @throws java.util.NoSuchElementException if `xs` is an empty list
  */
  def max(xs: List[Int]): Int = {
    if (xs.isEmpty) throw new NoSuchElementException("no ints")
    else _trackMax(xs, xs.head)
  }
  def _trackMax(xs: List[Int], max: Int): Int = {
    if (xs.isEmpty) max
    else _trackMax(xs.tail, if(xs.head > max) xs.head else max)
  }

}
