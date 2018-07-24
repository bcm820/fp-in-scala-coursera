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
  def sqrtIter(estimate: Double, x: Double): Double =
    if (isCloseEnough(estimate, x)) estimate
    else sqrtIter(applyNewtonMethod(estimate, x), x)

  def isCloseEnough(estimate: Double, x: Double) = {
    def abs(x: Double) = if (x < 0) -x else x
    abs(estimate * estimate - x) / x < 0.001
  }

  def applyNewtonMethod(estimate: Double, x: Double) =
    (estimate + x / estimate) / 2

  def sqrt(x: Double): Double = sqrtIter(1, x)

}