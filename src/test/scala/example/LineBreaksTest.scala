package example

import org.scalatest.{FlatSpec, Matchers}

/**
  * https://stackoverflow.com/questions/1171284/regex-to-match-eof
  */

class LineBreaksTest extends FlatSpec with Matchers {

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

    """^(.*?)$""".r.findAllIn("""auser1 home1b""").toList shouldBe List("auser1 home1b")    //match start/end of INPUT

    """(?m)^(.*?)$""".r.findAllIn(        //multiline mode - makes ^ and $ apply to start/end of LINE
      """auser1 home1b
        |auser1 home1b""".stripMargin).toList shouldBe List("auser1 home1b", "auser1 home1b")

    """^(.*?)$""".r.findAllIn(              //in single-line mode the . does not match an EOL char, hence .* does not match
      """auser1 home1b
        |auser1 home1b""".stripMargin).toList shouldBe List()

    val str = """auser1 home1b
                |auser1 home1b""".stripMargin
    val pattern = """(?s)^(.*?)$""".r
    val pattern(df) = str
    println(df)

    val res = """(?m).*from([^,;|\t]*)$""".r.findAllIn(
      """fromYYYY
        |
      """.stripMargin).toList

    println(res)


  }

  "replacing empty lines" should "work" in {


  }
}
