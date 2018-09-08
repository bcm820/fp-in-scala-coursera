package t

object CombinatorialSearch extends App {

  /*
  The N-Queens Problem
  Place n queens on a chessboard so that here are
  no two queens in the same row, column, or diagonal.
  Find all possible solutions, returned as a Set of Lists.

  e.g. queens(4)
  Set(List(2,0,3,1), List(1,3,0,2))
   *  *  Q  *        *  Q  *  *
   Q  *  *  *        *  *  *  Q
   *  *  *  Q        Q  *  *  *
   *  Q  *  *        *  *  Q  *

   */
  def queens(n: Int): Set[List[Int]] = {
    def placeQueens(k: Int): Set[List[Int]] =
      if (k == 0) Set(List())
      else
        for {
          queens <- placeQueens(k - 1)
          col <- 0 until n
          if isSafe(col, queens)
        } yield col :: queens
    placeQueens(n)
  }

  def isSafe(col: Int, queens: List[Int]) = {
    val row = queens.length
    val queensWithRow = (row - 1 to 0 by -1) zip queens
    queensWithRow forall {
      case (r, c) => col != c && math.abs(col - c) != row - r
    }
  }

  def show(queens: List[Int]) = {
    val lines = for (col <- queens.reverse)
      yield Vector.fill(queens.length)(" * ").updated(col, " Q ").mkString
    (lines mkString "\n") + "\n"
  }

  (queens(4) map show) map println

}
