package example

import org.scalatest.{FlatSpec, Matchers}

/**
  * https://alvinalexander.com/scala/how-to-extract-parts-strings-match-regular-expression-regex-scala
  */
class ExtractionTest extends FlatSpec with Matchers {

  "pattern extraction" should "work" in {

    val pattern = "([0-9]+) ([A-Za-z]+)".r

    val pattern(count, fruit) = "100 Bananas"

    //this pattern matches ONLY when between from and end ONLY characters OTHER THAN commas,semiscolons,pipes and whitespaces are found
    val fromNotFollowedBySeparator = """.*from([^,;|\s]*)$""".r
    //when it matches, it will capture all those characters between from and end

    val fromNotFollowedBySeparator(df) = "fromYYYY"
    df shouldBe "YYYY"
    val fromNotFollowedBySeparator(d2) = "from"
    d2 shouldBe ""

    intercept[MatchError] {
      val fromNotFollowedBySeparator(df2) = "fromYYYY sc"
      val fromNotFollowedBySeparator(df3) = "fromYYYY "
      val fromNotFollowedBySeparator(df4) = "fromYYYY|sc "
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
    extractDateFormat("""entity,from,sc""") shouldBe empty
    intercept[MatchError] { extractDateFormat("""entity, fro""") }
    intercept[MatchError] { extractDateFormat("""entity, sc""") }

  }

}
