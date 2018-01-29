package example

import scala.util.matching.Regex

class LastMatchUsingNegativeLookAhead extends Spec {

  //    https://frightanic.com/software-development/regex-match-last-occurrence/

  """A negative lookahead advances to the point where a match becomes impossible so
    a negative lookahead is perfect for "last-in-line" situations
  """ in {
    """(?!.*,)$""".r.findAllIn("a,b,c,blah").toList shouldBe List("") //moves the position after the last comma (before blah)
    """(?!.*/)$""".r.findAllIn("a/b/c/blah").toList shouldBe List("") //moves the position after the last slash (before blah)
  }

  "A lookahead is a non-capturing group. To capture, add a capture group" in {
    """(?!.*,)(.+)$""".r.findAllIn("a,b,c,blah").toList shouldBe List("blah") //this proves what position the regex engine was in after the negative lookahead
  }

  "an example of this in a multiline file: extract all strings after the last comma if any" in {

    val input =
      """SE,MLI,Client-Entity,40025006,2018-01-11,,
        |SE,MLI,Client-Entity,40025006,2018-01-11,"longcode: Value length not any of allowed: [20]"
        |SE,MLI,Client-Entity,40025006,2018-01-11,,""".stripMargin

    val res = """(?m)(?!.*,)(.+)$""".r.findAllIn(input).toList
    res shouldBe List("\"longcode: Value length not any of allowed: [20]\"")

  }

  "When using in pattern matching, put non-greedy .*? in front, otherwise the engine will go straight to the last char" in {

    val nongreedy = """^.*?(?!.*,)(.+)$""".r
    val output = "a,b,c,blah" match {
      case nongreedy(ext) => ext
    }
    output shouldBe "blah"
    nongreedy.unapplySeq("a,b,c,blah") shouldBe Some(List("blah"))

    val greedy = """^.*(?!.*,)(.+)$""".r
    greedy.unapplySeq("a,b,c,blah") shouldBe Some(List("h"))
  }


  "using negative lookahead to find rows that do not match a pattern" in {

    """\d{4}-\d{2}-\d{2}""".r.findAllIn("1966-01-03").toList shouldBe List("1966-01-03")
    """\d{4}-\d{2}-\d{2}""".r.findAllIn("1966-01-0").toList shouldBe empty
    """(?!\d{4}-\d{2}-\d{2})(.*)""".r.findAllIn("1966-01-03").toList shouldBe List("966-01-03", "")
    """(?!\d{4}-\d{2}-\d{2})(.*)""".r.findAllIn("1966-01-0").toList shouldBe List("1966-01-0", "")

    //If the negative lookahead stops right at 0, we know that the pattern is not there at all
    val startOfFirstMatch = """(?!\d{4}-\d{2}-\d{2})(.*)""".r.findFirstMatchIn("1966-01-03").get.start
    startOfFirstMatch shouldNot be 0
    """(?!\d{4}-\d{2}-\d{2})(.*)""".r.findFirstMatchIn("1966-01-0").get.start shouldBe 0   //
  }


}
