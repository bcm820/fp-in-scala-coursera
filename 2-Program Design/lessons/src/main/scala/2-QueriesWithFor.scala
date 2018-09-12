object b_QueriesWithFor extends App {

  case class Book(title: String, authors: List[String])

  // Mini database using named parameters (vs positional)
  val books: Set[Book] = Set(
    Book(title = "Structure and Interpretation of Computer Programs",
         authors = List("Abelson, Harald", "Sussman, Gerald J.")),
    Book(title = "Intro to FP",
         authors = List("Bird, Richard", "Wadler, Phil")),
    Book(title = "Effective Java", authors = List("Bloch, Joshua")),
    Book(title = "Java Puzzlers",
         authors = List("Bloch, Joshua", "Gafter, Neal")),
    Book(title = "Programming in Scala",
         authors = List("Odersky, Martin", "Spoon, Lex", "Venners, Bill"))
  )

  // Find titles of books matching author's last name is Bird
  for {
    b <- books
    a <- b.authors
    if a startsWith "Bird"
  } yield b.title
  // Set("Intro to FP")

  // Find titles of books with the word "Program" in the title
  for {
    b <- books
    if b.title.indexOf("Program") >= 0
  } yield b.title
  // Set(
  // "Structure and Interpretation of Computer Programs",
  // "Programming in Scala"
  // )

  // Find names of all authors having written two books
  for {
    b1 <- books // Iterate over books twice
    b2 <- books // to get pairs of books
    if b1.title < b2.title // Filter lexiographically for uniqueness
    a1 <- b1.authors // Iterate over book authors twice
    a2 <- b2.authors // to get pairs of book authors
    if a1 == a2 // Filter for pairs of matching authors
  } yield a1
  // Set("Bloch, Joshua")

}
