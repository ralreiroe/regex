package example

import scala.util.matching.Regex

class AfterLastTest extends Spec {

  "capture everything after last /" in {

    val allAfterLastFwSlash = ".*/([^/]+$)".r   //will eat everything greedily until a / then match 1 or more not-slash chars to the end

    val allAfterLastFwSlash(format) = "http://refdata.bankofam/primitive/geography/country/GB"

    "http://refdata.bankofam/primitive/geography/country/GB".replaceAll(allAfterLastFwSlash.toString, "$1") shouldBe "GB"

    format shouldBe "GB"

    def extractCountryCode(str: String) = {
      val allAfterLastFwSlash(format) = str
      format
    }

    extractCountryCode("http://refdata.bankofam/primitive/geography/country/GB") shouldBe "GB"

    val allAfterLastFwSlash3 = ".*/([^/]+)$".r   //will also work

    val allAfterLastFwSlash3(format3) = "http://refdata.bankofam/primitive/geography/country/GB"

    format3 shouldBe "GB"

    val allAfterLastFwSlash2 = ".*([^/]+$)".r   //will not work. will eat everything greedily until a match of a single char before the end. hence B

    val allAfterLastFwSlash2(format2) = "http://refdata.bankofam/primitive/geography/country/GB"

    format2 shouldBe "B"
  }

  "last two letters" in {

    //https://stackoverflow.com/questions/14628229/scala-regular-expression-with-end-of-string

    val lasttwoChars = """.*([a-zA-Z]{2})$""".r

    val lasttwoChars(format) = "http://refdata.bankofam/primitive/geography/country/GB"

    format shouldBe "GB"

  }

}
