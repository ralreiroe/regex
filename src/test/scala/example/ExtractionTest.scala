package example

import org.scalatest.{FlatSpec, Matchers}

/**
  * https://alvinalexander.com/scala/how-to-extract-parts-strings-match-regular-expression-regex-scala
  */
class ExtractionTest extends FlatSpec with Matchers {

  "pattern extraction" should "work" in {

    val pattern = "([0-9]+) ([A-Za-z]+)".r

    val pattern(count, fruit) = "100 Bananas"

    //this pattern matches when from is not followed by a separator and captures every char to the end
    val fromNotFollowedBySeparator = """.*from([^,;|\s]*)$""".r

    val fromNotFollowedBySeparator(df) = "fromYYYY"
    df shouldBe "YYYY"

    intercept[MatchError] {
      val fromNotFollowedBySeparator(df2) = "fromYYYY sc"
    }

    //this pattern matches when from is followed by a separator and captures every char between from and the separator
    val fromfollowedBySeparator = """.*from(.*?)[,;|\s].*""".r

    val fromfollowedBySeparator(df2) = "fromYYYY, sc"
    df2 shouldBe "YYYY"

    intercept[MatchError] {
      val fromfollowedBySeparator(df2) = "fromYYYY"
    }


    def extractDateFormat(str: String) = str match {
      case fromNotFollowedBySeparator(df) => df
      case fromfollowedBySeparator(df) => df
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
