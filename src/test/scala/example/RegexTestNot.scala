package example

import org.scalatest.{FlatSpec, Matchers}

/**
  * https://stackoverflow.com/questions/2403122/regular-expression-to-extract-text-between-square-brackets
  */
class RegexTestNot extends FlatSpec with Matchers {


  "Matches" can "be NOT" in {

    //these are all the same:


    println("""\[(.+?)\]""".r.findAllIn("""this is a [sample] string with [some] special words. [another one]""").toList)     //non-greedy

    println("""\[([^]]+)\]""".r.findAllIn("""this is a [sample] string with [some] special words. [another one]""").toList)   //match many ^] (not ])

    println("""\[([a-z ]+)\]""".r.findAllIn("""this is a [sample] string with [some] special words. [another one]""").toList) //match many a-z and whitespace

  }

}




