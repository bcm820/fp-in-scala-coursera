package u

// Given a phoneNumber string,
// create a set of all possible strings
// that the phoneNumber mnemonically generates
object FinalCollections extends App {
  val mnem = Map('2' -> "ABC",
                 '3' -> "DEF",
                 '4' -> "GHI",
                 '5' -> "JKL",
                 '6' -> "MNO",
                 '7' -> "PQRS",
                 '8' -> "TUV",
                 '9' -> "WXYZ")

  // invert the mnem map as letter to digit ('A' -> '2')
  val charCode: Map[Char, Char] = for {
    (digit, str) <- mnem // k-v pair of mnem map
    ltr <- str // letter from string
  } yield ltr -> digit

  // Map a word to the digit string it can represent
  // e.g. wordCode("Java") -> "5282"
  def wordCode(word: String): String = word.toUpperCase map charCode

  // Map a digit string to a Set of all its possible words
  // e.g. digitWords("5282") -> Set("JAVA", "KATA", "LAVA")
  def digitWords(digits: String): Set[String] = {
    val ds = for {
      d <- digits
      if (mnem contains d)
    } yield mnem(d)
    combos(ds toList) toSet
  }

  def combos(strs: Seq[String]): Seq[String] = {
    strs match {
      case Nil => List("")
      case xs :: rss =>
        for {
          x <- xs
          cs <- combos(rss)
        } yield x.toString ++ cs
    }
  }

  println(digitWords("722"))
}
