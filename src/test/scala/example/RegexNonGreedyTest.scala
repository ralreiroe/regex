package example

import org.scalatest.{FlatSpec, Matchers}


/**
  * https://stackoverflow.com/questions/2403122/regular-expression-to-extract-text-between-square-brackets
  */
class RegexNonGreedyTest extends FlatSpec with Matchers {

  "Matches" can "be greedy" in {

    println("""\[(.+)\]""".r.findAllIn("""this is a [sample] string with [some] special words. [another one]""").toList)
    println("""(\[)(.+)(\])""".r.findAllIn("""this is a [sample] string with [some] special words. [another one]""").toList)

    """(?<=\[)(.+)(?=\])""".r.findAllIn("""this is a [sample] string with [some] special words. [another one]""").toList shouldBe(List("""sample] string with [some] special words. [another one"""))

    """\[(.+)\]""".r.findAllIn("""this is a [sample] string with [some] special words. [another one]""").toList shouldBe(List("""[sample] string with [some] special words. [another one]"""))


    println("""===""")

    println("""this is a [sample] string with [some] special words. [another one]""".replaceAll("""\[(.+)\]""", """xxxx"""))
    """this is a [sample] string with [some] special words. [another one]""".replaceAll("""\[(.+)\]""", """xxxx""") shouldBe ("""this is a xxxx""")

    println("""this is a [sample] string with [some] special words. [another one]""".replaceAll("""(?<=\[)(.+)(?=\])""", """xxxx"""))

    """this is a [sample] string with [some] special words. [another one]""".replaceAll("""(?<=\[)(.+)(?=\])""", """xxxx""") shouldBe ("""this is a [xxxx]""")

  }

  "Matches" can "be non-greedy" in {

    println("""\[(.+?)\]""".r.findAllIn("""this is a [sample] string with [some] special words. [another one]""").toList)
    println("""(\[)(.+?)(\])""".r.findAllIn("""this is a [sample] string with [some] special words. [another one]""").toList)

    """(?<=\[)(.+?)(?=\])""".r.findAllIn("""this is a [sample] string with [some] special words. [another one]""").toList shouldBe(List("sample", "some", "another one"))

    """\[(.+?)\]""".r.findAllIn("""this is a [sample] string with [some] special words. [another one]""").toList shouldBe(List("[sample]", "[some]", "[another one]"))


    println("""===""")

    println("""this is a [sample] string with [some] special words. [another one]""".replaceAll("""\[(.+?)\]""", """xxxx"""))
    """this is a [sample] string with [some] special words. [another one]""".replaceAll("""\[(.+?)\]""", """xxxx""") shouldBe ("""this is a xxxx string with xxxx special words. xxxx""")

    println("""this is a [sample] string with [some] special words. [another one]""".replaceAll("""(?<=\[)(.+?)(?=\])""", """xxxx"""))

    """this is a [sample] string with [some] special words. [another one]""".replaceAll("""(?<=\[)(.+?)(?=\])""", """xxxx""") shouldBe ("""this is a [xxxx] string with [xxxx] special words. [xxxx]""")

  }


  "Matches" can "be non-greedy 2" in {

    """this is a [sample] string with [some] special words. [another one]""".replaceAll("""\[(.*?)\]""", """$1""") shouldBe ("""this is a sample string with some special words. another one""")
    """this is a [sample] string with [some] special words. [another one]""".replaceAll("""(?<=\[)(.+?)(?=\])""", """$1""") shouldBe ("""this is a [sample] string with [some] special words. [another one]""")   //no change, capture group contains brackets

  }
}




