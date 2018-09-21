package DiscreteEventSimulator

abstract class Gates extends Simulation {

  def InverterDelay: Int
  def AndGateDelay: Int
  def OrGateDelay: Int

  class Wire {

    // the wire's state is modeled by two private vars
    private var sigVal = false // current value of signal
    private var actions: List[Action] = List() // actions attached

    def getSignal = sigVal

    def setSignal(sig: Boolean) =
      if (sig != sigVal) {
        sigVal = sig
        for (a <- actions) a()
      }

    // attaches specified procedure to `actions` of the wire.
    // the added action is then executed
    def addAction(a: Action) = {
      actions = a :: actions
      a()
    }
  }

  // installs an action on its input wire
  // produces the input signal's inverse on the output wire
  // The change propagates after a delay of simulated time
  def inverter(input: Wire, output: Wire): Unit = {
    def invertAction(): Unit = {
      val inputSig = input.getSignal
      afterDelay(InverterDelay) {
        output setSignal !inputSig
      }
    }
    input addAction invertAction
  }

  // produces conjunction of input signals on the output wire
  // the change also propagates after a simulated delay
  def andGate(in1: Wire, in2: Wire, output: Wire) = {
    def andAction() = {
      val in1Sig = in1.getSignal
      val in2Sig = in2.getSignal
      afterDelay(AndGateDelay) {
        output setSignal (in1Sig && in2Sig)
      }
    }
    in1 addAction andAction
    in2 addAction andAction
  }

  /** Design orGate analogously to andGate */
  def orGateAlt(in1: Wire, in2: Wire, output: Wire): Unit = {
    def orAction() = {
      val in1Sig = in1.getSignal
      val in2Sig = in2.getSignal
      afterDelay(OrGateDelay) {
        output setSignal (in1Sig || in2Sig)
      }
    }
    in1 addAction orAction
    in2 addAction orAction
  }

  /** Design orGate in terms of andGate, inverter */
  def orGate(in1: Wire, in2: Wire, output: Wire): Unit = {
    val notIn1, notIn2, notOut = new Wire
    inverter(in1, notIn1)
    inverter(in2, notIn2)
    andGate(notIn1, notIn2, notOut)
    inverter(notOut, output)
  }

  def probe(name: String, wire: Wire) = {
    def probeAction() =
      println(s"$name $currentTime value = ${wire.getSignal}")
    wire addAction probeAction
  }

}
