package example

import org.scalatest.{FlatSpec, Matchers}

class RegexOrTest extends FlatSpec with Matchers {


  "Hello" should "have tests" in {

    """11111111111111111000001111111111""".replaceAll("""(0|1)""", """0""") shouldBe("""00000000000000000000000000000000""")


//    """algo1, employeeE""".replaceAll(""".*(client|employee|algo).*""", "$1") shouldBe "employee"
//    println("""algo1, """.replaceAll(""".*(client|algo|employee).*""", "$1"))
//    println("""algo1, employeeE""".replaceAll(""".*(client|algo|employee).*""", "$1"))
//    println("""employeeE, algo1""".replaceAll(""".*(client?|algo?|employee?).*""", "$1"))
//    println("""algo1, employeeE""".replaceAll(""".*(client?|algo?|employee?).*""", "$1"))
//    println("===")
//    println("""algo1, algoE""".replaceAll("""(algo){1}""", "$1"))
////    """algo1, employeeE""".replaceAll(""".*(client|algo|employee).*""", "$1") shouldBe "algo"
    println("ddalgo1\nemployeeE".replaceAll(""".*(client|employee|algo).*""", "$1"))        //eat everything before and after on a line

    println("===")
    println("""(algo){1}""".r.findAllIn("""algo algo""").toList)
    println("""(algo){1}""".r.findAllIn("""algo5 algo""").toList)
    println("""(algo)""".r.findAllIn("""algo5 algo""").toList)
    println("""(algo)""".r.findAllIn("""algo5 algo6""").toList)

    println("algo5 algo6".replaceAll("""(algo)[^\s]+""", "$1"))         //<============= eat all non-space after
    println("algo5 algo6 algoE".replaceAll("""(algo)[0-9]""", "$1"))      //matches algo5 and algo6, , captures algo, eats [0-9]      //<=============
    println("algo5 algo6 wolf".replaceAll("""(algo)""", "$1"))      //matches all but does not eat anything
    println("algo algo6 wolf".replaceAll("""(algo)""", "'$1'"))      //matches all algo but does not eat anything


    println("===")
    println("algo5 algo6 employeeE ".replaceAll("""(algo|employee)[^\s]+""", "$1"))         //<============= eat all non-space after


  }

  "extraction based on matching one or the other pattern" should "work" in {

    //this pattern matches when from is not followed by a separator and captures every char to the end
    val fromNotFollowedBySeparator = """.*from([^,;|\s]*)$""".r

    //this pattern matches when from is followed by a separator and captures every char between from and the separator
    val fromfollowedBySeparator = """.*from(.*?)[,;|\s].*""".r

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




