package example

import org.scalatest.{FreeSpecLike, Matchers}

import scala.util.matching.Regex

/**
  * https://www.scala-lang.org/api/2.12.3/scala/util/matching/Regex.html
  * http://blog.xebia.com/matching-strings-in-scala/
  */
class Basic extends FreeSpecLike with Matchers {

  "BASIC I: Scala regexes are extractor object, ie. implement unapplySeq" in {

    val r1 = "(.*Conflicting.*)".r

    "blah blah Conflicting blah" match {
      case r1(captured) => captured
    }

    //is same as
    val captured = r1.unapplySeq("blah blah Conflicting blah")
    captured shouldBe Some(List("blah blah Conflicting blah"))


    val regex = """([A-Z]{2})""".r

    regex.unapplySeq("") shouldBe None
    regex.unapplySeq("-1") shouldBe None
    regex.unapplySeq("GB") shouldBe Some(List("GB"))

    regex.findFirstIn("GB") shouldBe Some("GB")

  }

  "BASIC II: for extraction, you need at least one capture group (ie. open and close brackets)!!! otherwise you don't match anything" in {

    val r1 = "(.*Conflicting.*)".r
    val r2 = ".*Conflicting.*".r

    "blah blah Conflicting blah" match {
      case r1(captured) => captured
    }

    println(r1.unapplySeq("blah blah Conflicting blah"))

    val patternToCaptureWholeLineWithWord = r1
    val patternToCaptureWholeLineWithWord(captured) = "blah blah Conflicting blah"
    captured shouldBe "blah blah Conflicting blah"

    val patternWithoutCaptureGroup = r2
    intercept[MatchError]{val patternWithoutCaptureGroup(nothingCaptured) = "blah blah Conflicting blah"}


    //NOTE: FINDFIRSTIN - IN CONTRAST - DOES *NOT* CARE ABOUT CAPTURE GROUPS:

    r2.findFirstIn("blah blah Conflicting blah").get shouldBe "blah blah Conflicting blah"

    //THEY DON'T SEEM TO MAKE ANY DIFFERENCE EITHER:
    val r3 = ".*(Conflicting).*".r
    r3.findFirstIn("blah blah Conflicting blah").get shouldBe "blah blah Conflicting blah"

    val r4 = "Conflicting".r
    r4.findFirstIn("blah blah Conflicting blah").get shouldBe "Conflicting"



  }

  "BASIC III: for extraction, the regex must match entire line or text by including .*" in {

    val regex = ".*(q)(?=u).*".r

    val res = "qu" match {
      case regex(e) => e
      case _ => "not"
    }
    res shouldBe "q"

    val regex2 = "(q)(?=u)".r   //regex without the .* will not match at all

    val res2 = "qu" match {
      case regex2(e) => e
      case _ => "not"
    }
    res2 shouldBe "not"

    /**
      * For find instead, they both match but the first finds the whole line, while the second finds q
      */

    regex.findFirstIn("blah blah qu ").get shouldBe "blah blah qu "
    regex2.findFirstIn("blah blah qu ").get shouldBe "q"


  }

  }
