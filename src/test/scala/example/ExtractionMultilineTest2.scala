package example

import org.scalatest.{FreeSpecLike, Matchers}

/**
  * https://stackoverflow.com/questions/1691002/scala-regex-multiple-block-capturing
  */
class ExtractionMultilineTest2 extends FreeSpecLike with Matchers {

  val input =
    """jjj
      |begin {
      |first line}
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

  private val corePattern = """begin \{(.*?)\}"""

  "Multiline string - stops after first match" in {

    val FirstBlock = corePattern.r

    FirstBlock findAllIn input foreach (_ match {
      case FirstBlock(content) => content shouldBe "\nfirst line"
      case _ => fail
    })
  }

  "Multiline string - carry on matching until end of string" in {

    val BlockMultipleLine = s"""(?s)${corePattern}""".r

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
    BlockMultipleLine findAllIn input foreach (_ match {
      case BlockMultipleLine(content) => {
        content.trim shouldBe expectedResult(ints.next).trim
      }
      case _ => fail
    })
  }

  "Multiline string - greedy advance to match but on first line only" in {

    val GreedyBlockOnFirstLineOnly = s""".*${corePattern}.*""".r //without (?s) the dot only does not match EOL chars (everything else but \n, \r etc.)

    input match {
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


}
