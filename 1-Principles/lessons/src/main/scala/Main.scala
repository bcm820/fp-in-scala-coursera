object Week1 extends App {

  // NewtonSqrtMethod
  val sqrtOf2 = NewtonSqrtMethod.sqrt(2)
  val closeEnough = NewtonSqrtMethod.isCloseEnough(sqrtOf2, 2)
  println(s"The square root of 2 is ${sqrtOf2}")
  println(s"Close enough? ${closeEnough}!")

}