package forcomp

object Anagrams {

  type Word = String
  type Sentence = List[Word]

  /** `Occurrences` is a `List` of pairs of characters
    *  and positive integers saying how often the character appears.
    *  This list is sorted alphabetically w.r.t. to the character in each pair.
    *  All characters in the occurrence list are lowercase.
    *
    *  Any list of pairs of lowercase characters and their frequency
    *  which is not sorted is **not** an occurrence list.
    *
    *  Note: If the frequency of some character is zero,
    *  then that character should not be in the list.
    */
  type Occurrences = List[(Char, Int)]

  /** The dictionary is simply a sequence of words.
    *  It is predefined and obtained as a sequence
    *  using the utility method `loadDictionary`.
    */
  val dictionary: List[Word] = loadDictionary

  /** Converts the word into its character occurrence list.
    * The uppercase and lowercase version of the character are treated
    * as the same character, represented as a lowercase character.
    * Note: you must use `groupBy` to implement this method!
    */
  def wordOccurrences(w: Word): Occurrences = {
    val grouped = w.toLowerCase groupBy (_.toChar)
    val mapped = grouped map (c => (c._1, c._2.length))
    mapped.toList sortWith (_._1 < _._1)
  }

  /** Converts a sentence into its character occurrence list. */
  def sentenceOccurrences(s: Sentence): Occurrences =
    wordOccurrences(s.mkString)

  /** The `dictionaryByOccurrences` is a `Map` from different occurrences
    *  to a sequence of all the words that have that occurrence count.
    *  This map serves as an easy way to obtain all the anagrams
    *  of a word given its occurrence list.
    *
    *  For example, "eat" has the following character occurrence list:
    *  `List(('a', 1), ('e', 1), ('t', 1))`
    *  Incidentally, so do the words "ate" and "tea".
    *  This means `dictionaryByOccurrences` will contain an entry:
    *  List(('a', 1), ('e', 1), ('t', 1)) -> Seq("ate", "eat", "tea")
    */
  lazy val dictionaryByOccurrences: Map[Occurrences, List[Word]] =
    dictionary groupBy wordOccurrences

  /** Returns all the anagrams of a given word. */
  def wordAnagrams(word: Word): List[Word] = {
    val found = dictionaryByOccurrences find (_._2.contains(word))
    if (found != None) found.get._2 else Nil
  }

  /** Returns the list of all subsets of the occurrence list.
    *  This includes the occurrence itself
    *  i.e. `List(('k', 1), ('o', 1))`
    *  is a subset of `List(('k', 1), ('o', 1))`.
    *  It also includes the empty subset `List()`.
    */
  def combinations(occurrences: Occurrences): List[Occurrences] = {
    val init = List[Occurrences](Nil)
    occurrences.foldRight(init) {
      case ((char, occ), acc) => {
        val combos = for {
          cs <- acc
          n <- 1 to occ
        } yield (char, n) :: cs
        acc ++ combos
      }
    }
  }

  /** Subtracts occurrence list `y` from occurrence list `x`.
    * The precondition is `y` is a subset of `x` --
    * any character appearing in `y` must appear in `x`,
    * and its frequency in `y` must be <= its frequency in `x`.
    * Note: the resulting value is an occurrence list --
    * meaning it is sorted and has no zero-entries.
    */
  def subtract(x: Occurrences, y: Occurrences): Occurrences =
    x map (occ => {
      val subOcc = y.find(_._1 == occ._1)
      if (subOcc == None) occ
      else (occ._1, occ._2 - subOcc.get._2)
    }) filter (occ => occ._2 > 0)

  /** Returns a list of all anagram sentences of the given sentence,
    * the occurrences of all the characters of all words in the sentence,
    * producing all possible combinations of words with those characters,
    * such that the words have to be from the dictionary.
    *
    * Two sentences with the same words but in a different order
    * are considered two different anagrams.
    *
    * In case the words of the sentence are in the dictionary,
    * the sentence is the anagram of itself and has to be included.
    * Note: There is only one anagram of an empty sentence.
    */
  def sentenceAnagrams(sentence: Sentence): List[Sentence] = {
    def subAnas(occs: Occurrences): List[Sentence] = occs match {
      case Nil => List(Nil)
      case _ =>
        for {
          combo <- combinations(occs)
          word <- dictionaryByOccurrences getOrElse (combo, Nil)
          sentence <- subAnas(subtract(occs, wordOccurrences(word)))
          if !combo.isEmpty
        } yield word :: sentence
    }
    subAnas(sentenceOccurrences(sentence))
  }

}
