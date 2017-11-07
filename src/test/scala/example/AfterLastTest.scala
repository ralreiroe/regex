package example

import org.scalatest.{FlatSpec, Matchers}

class AfterLastTest extends FlatSpec with Matchers {

  "capture everything after last /" should "work" in {

    val allAfterLastFwSlash = ".*/([^/]+$)".r   //will eat everything greedily until a / then match 1 or more not-slash chars to the end

    val allAfterLastFwSlash(format) = "http://refdata.bankofam/primitive/geography/country/GB"

    "http://refdata.bankofam/primitive/geography/country/GB".replaceAll(allAfterLastFwSlash.toString, "$1") shouldBe "GB"

    format shouldBe "GB"

    val allAfterLastFwSlash3 = ".*/([^/]+)$".r   //will also work

    val allAfterLastFwSlash3(format3) = "http://refdata.bankofam/primitive/geography/country/GB"

    format3 shouldBe "GB"

    val allAfterLastFwSlash2 = ".*([^/]+$)".r   //will not work. will eat everything greedily until a match of a single char before the end. hence B

    val allAfterLastFwSlash2(format2) = "http://refdata.bankofam/primitive/geography/country/GB"

    format2 shouldBe "B"






  }

}
