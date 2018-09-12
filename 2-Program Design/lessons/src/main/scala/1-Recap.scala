object a_Recap extends App {

  // Basic representation of JSON data structure in Scala
  abstract class JSON
  case class JObj(bindings: Map[String, JSON]) extends JSON
  case class JSeq(elems: List[JSON]) extends JSON
  case class JNum(num: Double) extends JSON
  case class JStr(str: String) extends JSON
  case class JBool(b: Boolean) extends JSON
  case object JNull extends JSON

  // Example construction of JSON representation
  val exampleJson: JSON = JObj(
    Map(
      "name" -> JStr("Watership Down"),
      "location" -> JObj(
        Map("lat" -> JNum(51.235685), "long" -> JNum(-1.309197))),
      "residents" -> JSeq(
        List(
          JObj(
            Map(
              "name" -> JStr("Fiver"),
              "age" -> JNum(4)
            )),
          JObj(
            Map(
              "name" -> JStr("Bigwig"),
              "age" -> JNum(6)
            ))
        ))
    ))

  // Define stringified k:v binding for JSON for parsing
  type JBinding = (String, JSON)
  def parse(b: JBinding): String = "\"" + b._1 + "\": " + stringify(b._2)
  def stringify(json: JSON): String = json match {
    case JObj(bindings) => "{" + (bindings map (parse) mkString (", ")) + "}"
    case JSeq(elems)    => "[" + elems.map(stringify).mkString(", ") + "]"
    case JNum(num)      => num.toString
    case JStr(str)      => "\"" + str + "\""
    case JBool(b)       => b.toString
    case JNull          => "Null"
  }

  // Type Signature of a Pattern Match
  val f1: String => String = { case "ping" => "pong" }
  f1("ping") // pong
  // m("notping") // Error!

  // Partial Function
  // Allows you to determine if a function is applicable to an argument
  // Note: Caller is responsible for testing via `isDefinedAt`
  // or an error will be thrown. Or you can set a default value.
  val f2: PartialFunction[String, String] = {
    case "ping" => "pong"
    case _      => "default"
  }
  f2.isDefinedAt("ping") // true
  f2.isDefinedAt("pong") // false if no default value set

  // Warning: isDefinedAt only applies one level deep
  // Here g.isDefinedAt(List(1, 2, 3)) will return true
  // but g(List(1, 2, 3)) will throw an error since `rest`
  // does not have a matching pattern
  val f3: PartialFunction[List[Int], String] = {
    case Nil => "one"
    case x :: rest =>
      rest match {
        case Nil => "two"
      }
  }

  // For-Expressions implemented by Scala compiler
  val e1 = List(1, 2, 3, 4, 5)
  val e2 = for (x <- e1) yield x + 1 // evaluates to `e1.map(_ + 1)`

  val f4: Int => Boolean = x => x > 2
  val e3 = for (x <- e1 if f4(x)) yield x
  // evaluates to `for (x <- e1.withFilter(x => f)) yield x`
  // withFilter is a lazy variant of filter that does not allocate a list
  // but forwards the passing elements to the following map/flatMap function
  e1.withFilter(e => e > 3) // TraversableLike$WithFilter@61c8ce67
    .map(_ + 1) // List(4, 5) => List(5, 6)

  val e4 = for (x <- e1; y <- e2) yield x
  // evalutes to `e1.flatMap(x => for (y <- e2) yield x)`

}
