object g_State extends App {

  /* Functions and State

    When programs are side-effect free, time isn't important.
    For programs that terminate, any sequence of actions give
    the same results, regardless of when they terminate.

    For a pure function, all its rewriting terminates with the
    same solution. This is an important result of λ-calculus.
    This is sometimes called `confluence`.

    However, sometimes we must work objects that have a state
    that changes over the course of time (`stateful`).
    Mutable state is constructed from variables (`var`), name
    associations that can be changed through assignments.

    Another feature of imperative programming is loops. Consider
    these implementations of `while` and `repeat`... */

  def WHILE(condition: => Boolean)(command: => Unit): Unit =
    if (condition) {
      command
      WHILE(condition)(command)
    } else ()

  def REPEAT(command: => Unit)(condition: => Boolean): Unit = {
    command
    if (condition) ()
    else REPEAT(command)(condition)
  }

  /* Observations:
    - Both `condition` and `command` are passed by name so that
      their values are re-evaluated with each recursive call
    - The function is tail recursive (constant stack size)

    For-Loops:
    Scala doesn't have the same syntax as others for `for` loops.
    But they can be modeled using syntax similar to for-expressions.
    While for-expressions evaluate to `map`, `flatMap`, and `withFilter`,
    for-loops evaluate to `foreach`. Note that using nested generators
    to iterate over will be like nesting another for-loop. */

  for (i <- 1 to 3) println(i)
  for (i <- 1 to 3; j <- "abc") println(s"$i-$j")

}
