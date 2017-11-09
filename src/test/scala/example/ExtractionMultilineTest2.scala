package example

import org.scalatest.{FreeSpecLike, Matchers}

/**
  * https://stackoverflow.com/questions/1691002/scala-regex-multiple-block-capturing
  */
class ExtractionMultilineTest2 extends FreeSpecLike with Matchers {

  private var corePattern = """begin \{(.*?)\}"""

  "Matching a single-line block in a single-line string" in {
    val SingleLineBlock = corePattern.r

    (SingleLineBlock findAllIn """begin {content}""") shouldNot be(empty)
    (SingleLineBlock findAllIn """xxbegin {content}""") shouldBe(empty)

    val GreedyBlockOnFirstLineOnly = s""".*${corePattern}.*""".r //without (?s) the dot only does not match EOL chars (everything else but \n, \r etc.)

    """xxbegin {content}""" match {
      case GreedyBlockOnFirstLineOnly(content) => fail
      case _ => succeed
    }


  }

  corePattern = """begin \{(.*?)\}"""

  "Matching a multi-line block without (?s)" in { //without (?s) the dot does not match EOL chars

    val SingleLineBlock = corePattern.r


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

    (SingleLineBlock findAllIn
      """
        |begin {content
        |conetn}
      """.stripMargin) shouldBe empty

  }

  corePattern = """begin \{(.*?)\}"""

  val MultiLineBlock = s"""(?s)${corePattern}""".r

  "Matching a multi-line block" in {

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


  corePattern = """begin \{(.*?)\}"""

  "Multiline string - greedy advance to match but on first line only" in {

    val GreedyBlockOnFirstLineOnly = s""".*${corePattern}.*""".r //without (?s) the dot only does not match EOL chars (everything else but \n, \r etc.)

    input match {
      case GreedyBlockOnFirstLineOnly(content) => fail
      case _ => succeed
    }

    "begin " match {
      case GreedyBlockOnFirstLineOnly(content) => fail
      case _ => succeed
    }
  }

  "Multiline string - greedy advance to match across multiline string" in {

    val GreedyBlockAcrossMultipleLines = s"""(?s).*${corePattern}.*""".r //with (?s) the dot also matches EOL chars like \n

    input match {
      case GreedyBlockAcrossMultipleLines(content) => content shouldBe "\n  other content to extract\n"
      case _ => fail
    }
  }

  "ggg" in {

    val str =
      """<row>
        |<a>fromYYYY</a>
        |<b>notinterested</b>
        |<c>fromxyz</c>
        |</row>
      """.stripMargin

    val core = "from([^<]*)<"

    val corePattern = s"${core}".r

    val ints = Stream.from(0).iterator
    val matches = List("YYYY", "xyz")

    corePattern findAllIn str foreach (_ match {
      case corePattern(df) => df shouldBe matches(ints.next)
      case _ => fail
    })
  }


}
