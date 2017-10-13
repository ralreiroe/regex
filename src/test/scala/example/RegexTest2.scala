package example

import org.scalatest.{FlatSpec, Matchers}

class RegexTest2 extends FlatSpec with Matchers {


//  "Hello" should "have tests" in {
//
//    val Pattern = """(\w+) (\w+)""".r
//    val res =
//      "123 RunNifi" match {
//        case Pattern(a, "RunNifi") => a
//        case _ =>
//      }
//
//    println(res)
//
//  }
  "Hello2" should """match on word (\w+)""" in {

    val Pattern = """(.*) pkey =(\w+).*""".r
    val res =
      "blah di bla where pkey =129) )bla" match {
        case Pattern(a, b) => b
        case _ =>
      }

    res shouldBe("129")
  }
  "Hello2" should """match on anything but space (ie. everything until the next space""" in {

    val Pattern = """(.*) pkey =([^ ]*).*""".r
    val res =
      "blah di bla where pkey =129£ )bla" match {
        case Pattern(a, b) => b
        case _ =>
      }

    res shouldBe("129£")
  }
  "Hello2" should """match on anything but space (ie. everything until the next space) followed by £""" in {

    val Pattern = """(.*) pkey =([^ ]*)£.*""".r
    val res =
      "blah di bla where pkey =129£ )bla" match {
        case Pattern(a, b) => b
        case _ =>
      }

    res shouldBe("129")
  }
}




