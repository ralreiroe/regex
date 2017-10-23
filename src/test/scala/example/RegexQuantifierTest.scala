package example

import org.scalatest.{FlatSpec, Matchers}

/**
  * file:///Users/admin/Google%20Drive/told/Quantifiers%20regex%20greedy%20reluctant%20possessive.html
  */
class RegexQuantifierTest extends FlatSpec with Matchers {


  "Matches" can "have zero-length" in {

    //? = once or not at all
    """a?""".r.findAllIn("""aaaaa""").toList shouldBe (List("a", "a", "a", "a", "a", ""))   //note extra zero-length match
    //* = zero or more
    """a*""".r.findAllIn("""aaaaa""").toList shouldBe (List("aaaaa", ""))                   //note extra zero-length match
    //+ = one or more
    """a+""".r.findAllIn("""aaaaa""").toList shouldBe (List("aaaaa"))
  }


  "finding line breaks" should "work" in {

    //https://stackoverflow.com/questions/454908/split-java-string-by-new-line
    val multilineString =
      """

    """
    println("""[\r\n]+""".r.findAllIn(multilineString).toList)    //one or more of \r or \n so matches \r, \n, and \r\n
    println("""\r?\n""".r.findAllIn(multilineString).toList)      //one or none of \r and one of \n so matches \r\n and \n
    println("""\R""".r.findAllIn(multilineString).toList)
  }

  "find end of input vs. end of line" should "work" in {

    println("""-------------"""+"""^(.*?)$""".r.findAllIn("""auser1 home1b""").toList)    //match start/end of INPUT

    println("""-------------"""+"""(?m)^(.*?)$""".r.findAllIn(        //multiline mode - makes ^ and $ apply to start/end of LINE
      """auser1 home1b
        |auser1 home1b""".stripMargin).toList)



  }

  "replacing empty lines" should "work" in {


  }

  "quantifiers" can "also attach to capture groups and character classes too" in {

    //http://www.regular-expressions.info/charclass.html

    """(dog){3}""".r.findAllIn("""dogdogdogdogdogdog""").toList shouldBe (List("dogdogdog", "dogdogdog"))
    """[abc]{3}""".r.findAllIn("""abccabaaaccbbbc""").toList shouldBe (List("abc", "cab", "aaa", "ccb", "bbc"))
  }

  "quantifiers" can "be greedy and non-greedy" in {

    /**
      * Greedy quantifiers are considered "greedy" because they force the matcher to read in, or eat, the entire input string prior to attempting the first match. If the first match attempt (the entire input string) fails, the matcher backs off the input string by one character and tries again, repeating the process until a match is found or there are no more characters left to back off from. Depending on the quantifier used in the expression, the last thing it will try matching against is 1 or 0 characters.

The reluctant quantifiers, however, take the opposite approach: They start at the beginning of the input string, then reluctantly eat one character at a time looking for a match. The last thing they try is the entire input string.
      */

    """.*foo""".r.findAllIn("""xfooxxxxxxfoo""").toList shouldBe (List("xfooxxxxxxfoo"))
    """.*?foo""".r.findAllIn("""xfooxxxxxxfoo""").toList shouldBe (List("xfoo", "xxxxxxfoo"))


  }


  "match" should "work case insensitive" in {
    println("(?i)readme".r.findAllIn("README"))
  }

}




