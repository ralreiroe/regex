package example

import org.scalatest.{FlatSpec, FreeSpecLike, Matchers}

/**
  * https://alvinalexander.com/scala/how-to-extract-parts-strings-match-regular-expression-regex-scala
  */
class ExtractionTest extends FreeSpecLike with Matchers {

  "pattern extraction should work" in {

    val pattern = "([0-9]+) ([A-Za-z]+)".r

    val pattern(count, fruit) = "100 Bananas"

    //this pattern matches ONLY when between from and end ONLY characters OTHER THAN commas,semiscolons,pipes,tabs and whitespaces are found
    val fromNotFollowedBySeparator = """.*from([^,;|\t\s]*)$""".r
    //when it matches, it will capture all those characters between from and end

    val fromNotFollowedBySeparator(df) = "fromYYYY"
    df shouldBe "YYYY"
    val fromNotFollowedBySeparator(d2) = "from"
    d2 shouldBe ""

    intercept[MatchError] {  //no capture of 0 or more sequences of [^,;|\t\s] starting after from and ending at end
      val fromNotFollowedBySeparator(df2) = "fromYYYY sc"
      val fromNotFollowedBySeparator(df3) = "fromYYYY "
      val fromNotFollowedBySeparator(df4) = "fromYYYY|sc "
      val fromNotFollowedBySeparator(df5) = "fromYYYYtab  "
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

  "unanchro" in {

    val NameExtractor = "name=\"(.*)\"".r.unanchored
    val NameExtractorAnchored = "name=\"(.*)\"".r.anchored

    def extractNamesAnywhere(html: List[String]): List[String] = html.flatMap { html =>
      html match {
        case NameExtractor(name) => List(name)
        case otherwise => Nil
      }
    }

    println(extractNamesAnywhere(List("""name="Ralf"""", """...name="Fred"""")))
  }

  "extract all but forward-slashes in the form of getting what is left when all forward-slashes are removed" in {

    val allFwSlash = "[/]*"

    println("http://refdata.bankofam/primitive/geography/country/GB".replaceAll(allFwSlash, ""))

  }

}
