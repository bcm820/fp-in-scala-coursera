object f_Exercise extends App {

  /* The Water Pouring Problem
  Given a faucet, sink, and a number of different-sized glasses,
  (e.g. one of 4L, another 9L), pour a given quantity (e.g. 6L)
  of water into one of the glasses, measuring by using the
  capacities of the glasses and the sink.

  States and Moves
  - Glass: Int
  - State: Vector[Int] (one entry per glass)
  - Moves:
    1. Empty(glass) into sink
    2. Fill(glass) up entirely
    3. Pour(from, to)
  - Paths

  Generate all possible paths from all possible moves
  until a solution is reached. */

  class Pouring(capacities: Vector[Int]) {

    // States
    type State = Vector[Int]
    val initialState = capacities.map(x => 0) // all empty glasses

    // Moves
    // Each case class will implement `change`,
    // which will track changes to State.
    trait Move {
      def change(state: State): State
    }
    case class Empty(glass: Int) extends Move {
      def change(state: State) = state updated (glass, 0)
    }
    case class Fill(glass: Int) extends Move {
      def change(state: State) = state updated (glass, capacities(glass))
    }
    case class Pour(from: Int, to: Int) extends Move {
      def change(state: State) = {
        val amount = state(from) min (capacities(to) - state(to))
        state
          .updated(from, state(from) - amount)
          .updated(to, state(to) + amount)
      }
    }

    // Paths
    class Path(history: List[Move], val endState: State) {
      def extend(move: Move) = new Path(move :: history, move change endState)
      override def toString =
        (history.reverse.mkString(", ")) + " -> " + endState
    }

    val initialPath = new Path(Nil, initialState)

    def from(paths: Set[Path], explored: Set[State]): Stream[Set[Path]] =
      if (paths.isEmpty) Stream.empty
      else {
        val more = for {
          path <- paths
          next <- moves.map(path.extend)
          if !(explored contains next.endState)
        } yield next
        paths #:: from(more, explored ++ (more map (_.endState)))
      }

    lazy val pathSets = from(Set(initialPath), Set(initialState))

    def solution(target: Int): Stream[Path] =
      for {
        pathSet <- pathSets
        path <- pathSet
        if path.endState contains target
      } yield path

    // Each capacities represents a single glass to use
    // For each glass, we need to define all the possible
    // combinations of moves to take at first.
    val glasses = 0 until capacities.length
    val moves =
      (for (g <- glasses) yield Empty(g)) ++
        (for (g <- glasses) yield Fill(g)) ++
        (for {
          from <- glasses
          to <- glasses
          if from != to
        } yield Pour(from, to))
  }

  val problem = new Pouring(Vector(4, 9, 19))
  println(problem.solution(17))

}
