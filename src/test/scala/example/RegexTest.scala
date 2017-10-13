package example

import java.util.regex.Pattern

import org.scalatest.{FlatSpec, Matchers}

class RegexTest extends FlatSpec with Matchers {


  "Hello" should "have tests" in {

    val list = List("123", "123.45")
    val testRegex = """\d+""".r

    println(testRegex.findFirstMatchIn(list(0)))

    list.foreach { x =>
      x match {
        case testRegex() => println("TEST")
        case _ => println("NO MATCHING")
      }
    }

    val EmailRegex = """(\w+)@([\w\.]+)""".r

    "david@example.com".matches(EmailRegex.toString)
    println(List("david@example.com", "notanemailadr").filter(_.matches(EmailRegex.toString)))

    //To have the regex compiled only once (to a pattern)....


    val compiled = EmailRegex.pattern
    println(List("david@example.com", "notanemailadr").filter(compiled.matcher(_).matches))


    //or...

    object RegexUtils {

      class RichPattern(self: Pattern) {

        def =~(s: String) = self.matcher(s).matches
      }

      implicit def patternToRichPattern(r: Pattern) = new RichPattern(r)
    }

    import RegexUtils._

    println(List("david@example.com", "notanemailadr").filter(compiled =~ _) )



  }
}




