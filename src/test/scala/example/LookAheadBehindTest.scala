package example

import org.scalatest.{FlatSpec, Matchers}

/**
  * https://stackoverflow.com/questions/16985567/java-regex-matching-a-char-except-when-preceded-by-another-char
  *
  * http://www.regular-expressions.info/lookaround.html
  *
  */
class LookAheadBehindTest extends Spec {

  "" in {

    val regex = ".*(q(?=u)).*".r

      "qu" match {
     case regex(e) => println(e)
     case _ => println("not")
   }


    "q(?=u)".r.findAllIn("qa qv qx qz qu qi qo qu").toList.size shouldBe 2
    "q(?!u)".r.findAllIn("qa qv qx qz qu qi qo qu").toList.size shouldBe 6
 }

  "positive (negative) lookbehind should match semicolons (not) preceded by double backslash" in {

    val NotPreceededBy = """(?<![\\\\])(;)""".r
    val matches = """(?<![\\\\])(;)""".r.findAllIn("quick;brown\\\\;fox;jumps;over;the\\\\lazy;dog").flatMap(txt => NotPreceededBy.unapplySeq(txt)).flatten
    println(matches.toList)

    println("quick;brown\\\\;fox;jumps;over;the\\\\lazy;dog".replaceAll("""(?<![\\\\])(;)""", ","))
    """(?<![\\\\])(;)""".r.replaceAllIn("quick;brown\\\\;fox;jumps;over;the\\\\lazy;dog", ",") shouldBe """quick,brown\\;fox,jumps,over,the\\lazy,dog"""
    """(?<=[\\\\])(;)""".r.replaceAllIn("quick;brown\\\\;fox;jumps;over;the\\\\lazy;dog", ",") shouldBe """quick;brown\\,fox;jumps;over;the\\lazy;dog"""



    val string = """Utils5.showProgressWhile(ttt, 500, "xyz++   abc5   ProgressBarTest95"""

    """(?<=[a-z])5""".r.findAllIn(string).toList shouldBe List("5","5")             //5 preceded by a-z will match Utils5 and abc5
    """(?<![ ])5""".r.findAllIn(string).toList shouldBe List("5", "5", "5")         //5 not preceded by whitespace will match all but 500


  }

  "negative lookbehind should work to match all 1s except the one at the start of the line" in {

    println("""(?<!^)1""".r.findAllIn("""100111000""").toList)      //>>>>matches ALL except the one at the start
    println("""^1""".r.findAllIn("""100111000""").toList)
    println("""[^1]1""".r.findAllIn("""100111000""").toList)        //>>>>matches the FIRST preceded by something other than 1

  }

  """quote all words followd by ->  ; Use look ahead for the "followed by"""" in {

//    \w+ ~= [A-Za-z_0-9]+

    //https://stackoverflow.com/questions/1751301/regex-match-entire-words-only

    """(\w+)(?= ->)""".r.replaceAllIn(
      """Map(foo -> List(3, 4), bar -> List(42))""",      """"$1"""") shouldBe
      """Map("foo" -> List(3, 4), "bar" -> List(42))"""

  }


}




