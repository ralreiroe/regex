package example

import org.scalatest.{FlatSpec, Matchers}

/**
  *
  * Ignore till first match then eat what is outside parenths and capture what's inside
  *
  *                          \^([0-9]+)
  * ignore                 ^^           //everything before the first match is ignored
  * eat                      ^^         //matching and eating
  * capture                     ^^^^^^  //in parentheses
  *
  *
  *
  * https://stackoverflow.com/questions/632204/java-string-replace-using-regular-expressions
  *
  */
class RegexBasicIgnoreTillMatchThenEatThenCapture extends FlatSpec with Matchers {

  "replacing 5 * x^3 with 5 * x<sup>3</sup>" should """work""" in {

    //(1) everything before ^ unchanged, (2) then match and eat ^ (it is not in a group),, (3) then capture digits after ^

    """5 * x^3 - 6 * x^1 + 1""".replaceAll("""[\^]([0-9]+)""", """<sup>$1</sup>""") shouldBe """5 * x<sup>3</sup> - 6 * x<sup>1</sup> + 1"""
    """5 * x^3 - 6 * x^1 + 1""".replaceAll("""\^([0-9]+)""", """<sup>$1</sup>""") shouldBe """5 * x<sup>3</sup> - 6 * x<sup>1</sup> + 1"""

    """[\^]([0-9]+)""".r.findAllIn("""5 * x^3 - 6 * x^1 + 1""").toList shouldBe List("^3", "^1")
    """[\^][0-9]+""".r.findAllIn("""5 * x^3 - 6 * x^1 + 1""").toList shouldBe List("^3", "^1")
    """\^[0-9]+""".r.findAllIn("""5 * x^3 - 6 * x^1 + 1""").toList shouldBe List("^3", "^1")

    println("""5 * x^3 - 6 * x^1 + 1""".replaceAll("\\^([0-9]+)", """<sup>$1</sup>"""))     //note: double-quotes needed to escapes

    println("""[\^]([0-9]+)""".r.findAllIn("""5 * x^3 - 6 * x^1 + 1""").toList)

  }

  "testing " should "work" in  {

    var res = List.empty[String]

    res = """[^a-z]([a-z])""".r.findAllIn(""",,,,a""").toList
    println(res)

    res = """\^([a-z])""".r.findAllIn(""",,,^^a""").toList
    println(res)

    res = """\^([0-9]+)""".r.findAllIn("""5 * x^3 - 6 * x^1 + 1""").toList
    println(res)

    """(?<=[\\\\])(;)""".r.replaceAllIn("quick;brown\\\\;fox;jumps;over;the\\\\lazy;dog", ",") shouldBe """quick;brown\\,fox;jumps;over;the\\lazy;dog"""


  }
}




