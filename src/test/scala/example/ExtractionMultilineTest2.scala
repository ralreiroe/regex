package example

import org.scalatest.{FreeSpecLike, Matchers}

/**
  * https://stackoverflow.com/questions/1691002/scala-regex-multiple-block-capturing
  *
  * Gist: to match patterns that span EOLs, use (?s) to make the dot in .* match everything INCLUDING EOL chars.
  * By default the dot does not match EOL chars, only everything else.
  */
class ExtractionMultilineTest2 extends FreeSpecLike with Matchers {

  private var corePattern = """begin \{(.*?)\}"""

  "Matching a multi-line block with (.*) requires (?s)" in { //without (?s) the dot does not match EOL chars

    val SingleLineBlock = corePattern.r

    //the multilineinput
    val oneSingleLineBlockOneMultilineBlock =
      """
        |begin {content}
        |begin {con
        |tent}
      """.stripMargin


    (SingleLineBlock findAllIn
      oneSingleLineBlockOneMultilineBlock) shouldNot be(empty)

    (SingleLineBlock findAllIn
      oneSingleLineBlockOneMultilineBlock).toList.size shouldBe 1

    //but no match here..
    (SingleLineBlock findAllIn
      """
        |begin {content
        |conetn}
      """.stripMargin) shouldBe empty

  }

  corePattern = """begin \{(.*?)\}"""

  val MultiLineBlock = s"""(?s)${corePattern}""".r

  "Matching a multi-line block with (?s)" in {

    (MultiLineBlock findAllIn
      """
        |begin {content
        |conetn}
      """.stripMargin) shouldNot be(empty)

    val input =
      """jjj
        |begin {
        |first line}kk
        |some text
        |begin {
        |  content to extract
        |  content to extract
        |}
        |some text
        |begin {
        |  other content to extract
        |}
        |some text""".stripMargin

    val expectedResult = List("\nfirst line",
      """
        |  content to extract
        |  content to extract
        |""".stripMargin,
      """
        |  other content to extract
        |
        |""".stripMargin)

    val ints = Stream.from(0).iterator
    MultiLineBlock findAllIn input foreach (_ match {
      case MultiLineBlock(content) => {
        content.trim shouldBe expectedResult(ints.next).trim
      }
      case _ => fail
    })
  }

  val multilineinput =
    """jjj
      |begin {
      |first line}kk
      |some text
      |begin {
      |  content to extract
      |  content to extract
      |}
      |some text
      |begin {
      |  other content to extract
      |}
      |some text""".stripMargin





  corePattern = """begin \{(.*?)\}"""

  "Multiline string - greedy advance to match but on first line only" in {

    val GreedyBlockOnFirstLineOnly = s""".*${corePattern}.*""".r //without (?s) the dot only does not match EOL chars (everything else but \n, \r etc.)

    //no match
    multilineinput match {
      case GreedyBlockOnFirstLineOnly(content) => fail
      case _ => succeed
    }

    //match
    "begin {}" match {
      case GreedyBlockOnFirstLineOnly(content) => succeed
      case _ => fail
    }
  }

  "Multiline string - greedy advance to match across multiline string" in {

    val GreedyBlockAcrossMultipleLines = s"""(?s).*${corePattern}.*""".r //with (?s) the dot also matches EOL chars like \n

    //advancing ot last match
    multilineinput match {
      case GreedyBlockAcrossMultipleLines(content) => content shouldBe "\n  other content to extract\n"
      case _ => fail
    }
  }

  "matching of single-line patterns works irrespective of whether they occur in single-line or multi-line String" in {

    val str =
      """<row>
        |<a>fromYYYY</a>
        |<b>notinterested</b>
        |<c>fromxyz</c>
        |</row>
      """.stripMargin

    val core = "from([^<]*)<"

    val corePattern = s"${core}".r

    var ints = Stream.from(0).iterator
    val matches = List("YYYY", "xyz")

    corePattern findAllIn str foreach (_ match {
      case corePattern(df) => df shouldBe matches(ints.next)
      case _ => fail
    })

    val result = corePattern findAllIn str map (_ match {
      case corePattern(df) => df
      case _ =>
    })

    result.toList shouldBe List("YYYY", "xyz")


    //same with input as single-line...

    ints = Stream.from(0).iterator

    str.replaceAll("""\R""", "").trim shouldBe """<row><a>fromYYYY</a><b>notinterested</b><c>fromxyz</c></row>"""

    corePattern findAllIn (str.replaceAll("""\R""", "")) foreach (_ match {
      case corePattern(df) => df shouldBe matches(ints.next)
      case _ => fail
    })
  }


}
