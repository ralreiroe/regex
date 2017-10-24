package example

import org.scalatest.{FlatSpec, Matchers}

/**
  * https://alvinalexander.com/scala/how-to-extract-parts-strings-match-regular-expression-regex-scala
  */
class ExtractionTest extends FlatSpec with Matchers {

  "pattern extraction" should "work" in {

    val pattern = "([0-9]+) ([A-Za-z]+)".r

    val pattern(count, fruit) = "100 Bananas"

    val pattern2 = """.*from(.*)$""".r

    val pattern2(df) = "fromYYYY"

    df shouldBe "YYYY"

    val pattern3 = """.*from(.*?)[,;|\s].*""".r

    def extractDateFormat(str: String) = str match {
      case pattern3(df) => df
      case pattern2(df) => df
    }

    extractDateFormat("""entity, from""") shouldBe empty
    extractDateFormat("""entity, fromYYYY""") shouldBe "YYYY"
    extractDateFormat("""entity, fromYYY, sc""") shouldBe "YYY"
    extractDateFormat("""entity; fromYYY, sc""") shouldBe "YYY"
    extractDateFormat("""entity| fromYYY, sc""") shouldBe "YYY"
    extractDateFormat("""entity fromYYY, sc""") shouldBe "YYY"
    extractDateFormat("""entity, from, sc""") shouldBe empty

  }

}
