/* Classical imperative approach to the Observer Pattern

Good:
- Decouples views from state
- Allows multiple views of one state

Bad:
- Forces imperative style, since handlers are Unit-typed
- Many moving parts need to be co-ordinated
- Concurrency makes things more complicated
- Views tightly bound to one state; update hpapens immediately

 */

trait Publisher {
  private var subscribers: Set[Subscriber] = Set()
  def subscribe(subscriber: Subscriber) = subscribers += subscriber
  def unsubscribe(subscriber: Subscriber) = subscribers -= subscriber
  def publish() = subscribers.foreach(_.handler(this))
}

trait Subscriber {
  def handler(pub: Publisher)
}

// The `Model`
class BankAccount extends Publisher {
  private var balance = 0
  def currentBalance = balance

  def deposit(amount: Int) = {
    balance = balance + amount
    publish() // notify subscribers
  }

  def withdraw(amount: Int) = {
    if (amount <= balance) {
      balance = balance - amount
      publish()
    } else throw new Error("insufficient funds")
  }

}

// The `View`
class Consolidator(observed: List[BankAccount]) extends Subscriber {
  private var total: Int = _ // uninitialized
  def totalBalance = total

  // When called, `compute` maps over all observed bank accounts,
  // takes the sum of the current balance, and stores the total result
  private def compute() =
    total = observed.map(_.currentBalance).sum

  // When instantiated, subscribe to all observed bank accounts
  // and then immediately compute the total's initial value
  observed.foreach(_.subscribe(this))
  compute()

  // When `publish` is called in any account, the total is recomputed
  def handler(pub: Publisher) = compute()

}

object i_ObserverPattern extends App {
  val a, b = new BankAccount
  val c = new Consolidator(List(a, b))
  println(c.totalBalance) // 0
  a deposit 20
  b deposit 30
  println(c.totalBalance) // 50
}
