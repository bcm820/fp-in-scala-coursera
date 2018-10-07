/* Functional Reactive Programming
FRP lets us aggregate an event sequence into a `signal`.
Rather than propagate updates to mutable state,
new signals are defined in terms of existing ones.

Implementation Idea: Each signal maintains
- its current value
- an expression that defines the signal value (used for re-evaluating)
- a set of observers (other signals that depend on its value)
If the signal changes, all observers need to be re-evaluated. */

import scala.util.DynamicVariable

class Signal[T](expr: => T) {
  import Signal._
  private var myExpr: () => T = _
  private var myValue: T = _
  private var observers: Set[Signal[_]] = Set()
  private var observed: List[Signal[_]] = Nil
  update(expr)

  protected def computeValue(): Unit = {
    for (sig <- observed)
      sig.observers -= this
    observed = Nil
    val newValue = caller.withValue(this)(myExpr())
    if (myValue != newValue) {
      myValue = newValue
      val obs = observers
      observers = Set()
      obs.foreach(_.computeValue())
    }
  }

  protected def update(expr: => T): Unit = {
    myExpr = () => expr
    computeValue()
  }

  def apply() = {
    observers += caller.value
    assert(!caller.value.observers.contains(this), "cyclic signal definition")
    caller.value.observed ::= this
    myValue
  }
}

class Var[T](expr: => T) extends Signal[T](expr) {
  override def update(expr: => T): Unit = super.update(expr)
}

object Var {
  def apply[T](expr: => T) = new Var(expr)
}

object NoSignal extends Signal[Nothing](???) {
  override def computeValue() = ()
}

object Signal {
  val caller = new DynamicVariable[Signal[_]](NoSignal)
  def apply[T](expr: => T) = new Signal(expr)
}

// Bank Accounts example with Signal `balance`
// and function `consolidated` which produces the sum...

class FRPBankAccount {
  val balance = Var(0)
  def currentBalance = balance

  def deposit(amount: Int) = {
    val b = balance()
    balance() = b + amount
  }

  def withdraw(amount: Int) = {
    val b = balance()
    if (amount <= b)
      balance() = b - amount
    else throw new Error("insufficient funds")
  }
}

object j_FRP extends App {
  def consolidated(accts: List[FRPBankAccount]): Signal[Int] =
    Signal(accts.map(_.balance()).sum)
  val a, b = new FRPBankAccount
  val c = consolidated(List(a, b))
  println(c()) // 0
  a deposit 20
  b deposit 30
  println(c()) // 50
  val xchange = Signal(246.00)
  val inDollar = Signal(c() * xchange())
  println(inDollar()) // 12300.0
  b withdraw 10
  println(inDollar()) // 9840.0

}
