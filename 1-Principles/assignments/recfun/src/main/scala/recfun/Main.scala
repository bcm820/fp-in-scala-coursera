package recfun

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }


  /* Exercise 1: Pascal's Triangle

        1       This pattern of numbers is called Pascal’s triangle.
       1 1      The numbers at the edge of the triangle are all 1.
      1 2 1     Each inside number is the sum of the two numbers above it.
     1 3 3 1    Write a recursive function that takes a column and row and,
    1 4 6 4 1   counts from 0 to return the number at the given positione.

    Examples:
    pascal(0, 2) = 1
    pascal(1, 2) = 2
    pascal(1, 3) = 3

    Note: Later I will try to implement tail recursion.
  */
  def pascal(c: Int, r: Int): Int =
    if (c == 0 || c == r) 1
    else pascal(c - 1, r - 1) + pascal(c, r - 1)
  

  /* Exercise 2: Parentheses Balancing
    Write a recursive function which verifies the balancing of parentheses in a string,
    which we represent as a List[Char] not a String.

    There are three methods on List[Char] that are useful for this exercise:
    * chars.isEmpty: Boolean returns whether a list is empty
    * chars.head: Char returns the first element of the list
    * chars.tail: List[Char] returns the list without the first element
  */
  def balance(chars: List[Char]): Boolean = {
    def trBalance(chars: List[Char], bools: List[Boolean]): Boolean =
      if (chars.isEmpty) bools.isEmpty
      else if (chars.head == '(') trBalance(chars.tail, bools :+ true)
      else if (chars.head == ')')
        if (bools.isEmpty) false
        else trBalance(chars.tail, bools.tail)
      else trBalance(chars.tail, bools)
    trBalance(chars, List())
  }
  
  /* Exercise 3: Counting Change
    Write a recursive function that counts how many different ways,
    to make change given an amount and a list of coin denominations.
    For example, there are 3 ways to give change for 4
    if you have coins with denomination 1 and 2: 1+1+1+1, 1+1+2, 2+2.
    Consider edge cases (i.e. 0 money, coins.isEmpty).
  */
  def countChange(money: Int, coins: List[Int]): Int = {
    if (money < 0) 0
    else if (coins.isEmpty) if (money == 0) 1 else 0
    else countChange(money, coins.tail) + countChange(money - coins.head, coins)
  }

}