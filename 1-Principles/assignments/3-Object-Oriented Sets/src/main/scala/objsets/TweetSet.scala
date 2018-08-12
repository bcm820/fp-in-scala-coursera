package objsets

import TweetReader._

class Tweet(val user: String, val text: String, val retweets: Int) {
  override def toString: String =
    "User: " + user + "\n" +
    "Text: " + text + " [" + retweets + "]"
}

/**
 * A set of objects of type `Tweet` in the form of a binary search tree.
 * The structure requires comparing the length of the texts of two tweets.
 */
abstract class TweetSet {
  def filter(p: Tweet => Boolean): TweetSet = filterAcc(p, new Empty)
  def filterAcc(p: Tweet => Boolean, acc: TweetSet): TweetSet // helper
  def union(that: TweetSet): TweetSet // returns the union of two TweetSets
  def mostRetweeted: Tweet // returns the Tweet with the greatest retweet count
  def compareRetweets(most: Tweet): Tweet // helper for mostReetweeted
  def descendingByRetweet: TweetList // returns a TweetList sorted desc by retweet count
  def incl(tweet: Tweet): TweetSet // returns a TweetSet with the included Tweet
  def contains(tweet: Tweet): Boolean // tests if TweetSet contains the a Tweet
  def remove(tweet: Tweet): TweetSet // returns a TweetSet excluding the Tweet
  def foreach(f: Tweet => Unit): Unit // Applies a function to every Tweet in a TweetSet
  def toTweetList(acc: TweetList): TweetList
}

class Empty extends TweetSet {
  def filterAcc(p: Tweet => Boolean, acc: TweetSet): TweetSet = this
  def union(other: TweetSet): TweetSet = other
  def mostRetweeted: Tweet = throw new NoSuchElementException("Empty TweetSet")
  def compareRetweets(most: Tweet): Tweet = most
  def descendingByRetweet = Nil
  def incl(tweet: Tweet): TweetSet = new NonEmpty(tweet, new Empty, new Empty)
  def contains(tweet: Tweet): Boolean = false
  def remove(tweet: Tweet): TweetSet = this
  def foreach(f: Tweet => Unit): Unit = ()
  def toTweetList(acc: TweetList): TweetList = acc
}

class NonEmpty(elem: Tweet, left: TweetSet, right: TweetSet) extends TweetSet {

  def filterAcc(p: Tweet => Boolean, acc: TweetSet): TweetSet = {
    val testSet = if (p(elem)) acc.incl(elem) else acc
    left.filterAcc(p, acc) union (right.filterAcc(p, acc) union testSet)
  }

  def union(other: TweetSet): TweetSet = left union (right union (other incl elem))

  def mostRetweeted: Tweet = compareRetweets(elem)
  def compareRetweets(most: Tweet): Tweet =
    if (elem.retweets > most.retweets) remove(elem).compareRetweets(elem)
    else remove(elem).compareRetweets(most)

  def descendingByRetweet: TweetList = toTweetList(Nil).reverse(Nil)
  def toTweetList(acc: TweetList): TweetList =
    remove(mostRetweeted).toTweetList(new Cons(mostRetweeted, acc))

  def incl(x: Tweet): TweetSet =
    if (x.text < elem.text) new NonEmpty(elem, left.incl(x), right)
    else if (elem.text < x.text) new NonEmpty(elem, left, right.incl(x))
    else this
  
  def contains(x: Tweet): Boolean =
    if (x.text < elem.text) left.contains(x)
    else if (elem.text < x.text) right.contains(x)
    else true

  def remove(tw: Tweet): TweetSet =
    if (tw.text < elem.text) new NonEmpty(elem, left.remove(tw), right)
    else if (elem.text < tw.text) new NonEmpty(elem, left, right.remove(tw))
    else left.union(right)

  def foreach(f: Tweet => Unit): Unit = {
    f(elem)
    left.foreach(f)
    right.foreach(f)
  }
}

trait TweetList {
  def head: Tweet
  def tail: TweetList
  def isEmpty: Boolean
  def reverse(acc: TweetList): TweetList
  def foreach(f: Tweet => Unit): Unit =
    if (!isEmpty) {
      f(head)
      tail.foreach(f)
    }
}

object Nil extends TweetList {
  def head = throw new java.util.NoSuchElementException("head of EmptyList")
  def tail = throw new java.util.NoSuchElementException("tail of EmptyList")
  def isEmpty = true
  def reverse(acc: TweetList): TweetList = Nil
}

class Cons(val head: Tweet, val tail: TweetList) extends TweetList {
  def isEmpty = false
  def reverse(acc: TweetList): TweetList = {
    if (!tail.isEmpty) tail.reverse(new Cons(head, acc))
    else new Cons(head, acc)
  }
}

object GoogleVsApple {
  val google = List("android", "Android", "galaxy", "Galaxy", "nexus", "Nexus")
  val apple = List("ios", "iOS", "iphone", "iPhone", "ipad", "iPad")

  lazy val googleTweets: TweetSet = allTweets.filter(t => google.exists(w => t.text.contains(w)))
  lazy val appleTweets: TweetSet = allTweets.filter(t => apple.exists(w => t.text.contains(w)))
  lazy val trending: TweetList = googleTweets.union(appleTweets).descendingByRetweet
}

object Main extends App {
  GoogleVsApple.trending foreach println
}
