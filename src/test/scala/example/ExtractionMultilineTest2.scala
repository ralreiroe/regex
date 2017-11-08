package example

import java.util.regex.Pattern

import org.scalatest.{FreeSpecLike, Matchers}

/**
  * https://stackoverflow.com/questions/1691002/scala-regex-multiple-block-capturing
  */
class ExtractionMultilineTest2 extends FreeSpecLike with Matchers {

  "pattern extraction should work" in {

    val input = """begin {first line}
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

    val BlockSingleLine = """begin \{(.*?)\}""".r

    BlockSingleLine findAllIn input foreach (_ match {
      case BlockSingleLine(content) => content shouldBe "first line"
      case _ => println("NO MATCH")
    })

    val BlockMultipleLine = """(?s)begin \{(.*?)\}""".r

    val expectedResult = List("""first line""","""
                                |  content to extract
                                |  content to extract
                                |""".stripMargin,"""
                                |  other content to extract
                                |
                                |""".stripMargin)

    val ints = Stream.from(0).iterator
    BlockMultipleLine findAllIn input foreach (_ match {
      case BlockMultipleLine(content) => {
        content.trim shouldBe expectedResult(ints.next).trim
      }
      case _ => println("NO MATCH")
    })

    val BlockGreedilyToLastBeginAndOnlyMatchLast = """(?s).*begin \{(.*)\}.*""".r

    input match {
      case BlockGreedilyToLastBeginAndOnlyMatchLast(content) => content shouldBe "\n  other content to extract\n"
      case _ => println("NO MATCH")
    }

  }


}
