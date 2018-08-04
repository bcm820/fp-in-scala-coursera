package funsets

object Main extends App {
  import FunSets._
  
  // singletonSet
  println(contains(singletonSet(1), 1))

  // union
  println(contains(
    union(singletonSet(2),
    union(singletonSet(3), singletonSet(56))
  ), 56))

  // intersect
  println(contains(intersect(
    singletonSet(99),
    union(
      singletonSet(3),
      union(singletonSet(4), singletonSet(99))
    )
  ), 99))

  // diff
  println(contains(diff(
    union(
      singletonSet(1),
      union(singletonSet(3), singletonSet(5))
    ),
    singletonSet(7)
  ), 5))

  // filter
  println(!contains(filter(
    union(
      union(
        union(singletonSet(1), singletonSet(2)),
        union(singletonSet(3), singletonSet(4))
      ),
      union(
        union(singletonSet(5), singletonSet(6)),
        union(singletonSet(7), singletonSet(8))
      )
    ),
    x => x % 2 == 0
  ), 7))

  
  println(forall(
    union(singletonSet(1), singletonSet(3)),
    x => x % 2 == 1)
  )

  println(exists(
    union(singletonSet(2), singletonSet(4)),
    x => x % 2 == 0)
  )

  println(
    contains(
      map(
        union(
          union(singletonSet(1), singletonSet(2)),
          union(singletonSet(3), singletonSet(4))
        ),
        x => x + 1
      ), 5
    )
  )

}
