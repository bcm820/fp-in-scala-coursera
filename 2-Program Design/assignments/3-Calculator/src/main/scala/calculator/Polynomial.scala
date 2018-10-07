package calculator

object Polynomial {

  // b² - 4ac
  def computeDelta(a: Signal[Double],
                   b: Signal[Double],
                   c: Signal[Double]): Signal[Double] =
    Signal {
      val bv = b()
      val bsq = bv * bv
      val ac = a() * c()
      bsq - (4 * ac)
    }

  // (-b ± √Δ) / 2a
  def computeSolutions(a: Signal[Double],
                       b: Signal[Double],
                       c: Signal[Double],
                       delta: Signal[Double]): Signal[Set[Double]] = {

    val bn = Signal(-1 * b())
    val a2 = Signal(2 * a())
    val dSqrt = Signal(math.sqrt(delta()))

    Signal {
      delta() match {
        case d if (d < 0) => Set()
        case _            => Set((bn() + dSqrt()) / a2(), (bn() - dSqrt()) / a2())
      }
    }
  }
}
