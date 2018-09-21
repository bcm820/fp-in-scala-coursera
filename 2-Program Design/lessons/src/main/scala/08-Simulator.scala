/* Discrete Event Simulator

This simulator is represented as a mutable list of actions.
When actions are called, they change the state of objects
and can also install other actions to execute in the future.

A digital circuit is composed of `wires` and components.
Wires transport signals that are transformed by components.
We represent signals using booleans.

The base components (gates) are:
- The `inverter`, whose output is the inverse of its input.
- The `andGate`, whose output is the conjunction of its inputs.
- The `orGate`, whose output is the disjunction of its inputs.

Then `circuit` components can be built by combining base components.

The components have a reaction time (i.e. `delay`). Their outputs
don't change immediately after a change to their inputs. */

import DiscreteEventSimulator._

object h_Simulator extends App {

  object sim extends Circuits with Parameters
  import sim._
  val in1, in2, sum, carry = new Wire

  halfAdder(in1, in2, sum, carry)
  probe("sum", sum)
  probe("carry", carry)

  in1 setSignal true
  run()

  in2 setSignal true
  run()

  in1 setSignal false
  run()

}
