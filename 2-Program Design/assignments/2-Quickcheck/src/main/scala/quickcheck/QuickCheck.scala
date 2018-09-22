package quickcheck

import common._

import org.scalacheck._
import Arbitrary._
import Gen._
import Prop._

abstract class QuickCheckHeap extends Properties("Heap") with IntHeap {

  lazy val genHeap: Gen[H] = oneOf(
    const(empty),
    for {
      i <- arbitrary[A]
      h <- oneOf(const(empty), genHeap)
    } yield insert(i, h)
  )

  implicit lazy val arbHeap: Arbitrary[H] = Arbitrary(genHeap)

  /* Given any heap, you should get a sorted sequence of elements
  when continually finding and deleting the minimum int. */

  def remove(h: H): List[Int] =
    if (isEmpty(h)) Nil
    else findMin(h) :: remove(deleteMin(h))

  def insertAll(xs: List[Int], h: H): H = xs match {
    case Nil     => empty
    case y :: ys => insert(y, insertAll(ys, h))
  }

  property("sorted") = forAll { (xs: List[Int]) =>
    xs.sorted == remove(insertAll(xs, empty))
  }

}
