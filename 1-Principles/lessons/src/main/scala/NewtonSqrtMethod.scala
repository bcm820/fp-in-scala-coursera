object NewtonSqrtMethod {

  /* Find the square root with Newton's method
    - Start with an initial estimate y (start with y = 1)
    - Repeatedly improve the estimate by taking the mean of y and x/y.

    To compute sqrt(x), given x = 2:
    Estimate    Quotient                Mean
    1           2 / 1 = 2               1.5
    1.5         2 / 1.5 = 1.333         1.4167
    1.4167      2 / 1.4.167 = 1.4118    1.4142 ...

    Our approach as pseudocode:
    def sqrtIter(estimate: Double, x: Double): Double =
      if (isCloseEnough) estimate
      else sqrtIter(applyNewtonMethod(estimate, x), x)

  */
  def abs(i: Double) = if (i < 0) -i else i

  def sqrt(x: Double): Double = {

    def sqrtIter(estimate: Double): Double =
      if (isCloseEnough(estimate)) estimate
      else sqrtIter(applyNewtonMethod(estimate))

    def isCloseEnough(estimate: Double) = {
      abs(estimate * estimate - x) / x < 0.001
    }

    def applyNewtonMethod(estimate: Double) =
      (estimate + x / estimate) / 2

    sqrtIter(1)
  }

}