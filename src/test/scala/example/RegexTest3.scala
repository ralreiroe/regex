package example

import org.scalatest.{FlatSpec, Matchers}

class RegexTest3 extends FlatSpec with Matchers {


  "b" should """match on word (\w+)""" in {

    val Pattern = """[^a-z]([a-z])[^a-z]""".r

    val list = Pattern.findAllIn(",a))b,xyz").toList

    println("--")
    println(list)

    val res2: Option[List[String]] = Pattern.unapplySeq(",a)")

    println(res2)

//https://stackoverflow.com/questions/2066170/scala-regexps-how-to-return-matches-as-array-or-list
    val res3 = Pattern.findAllIn(",a))b,").flatMap(txt => Pattern.unapplySeq(txt)).flatten

    println(res3.toList)

    val res4 = Pattern.findAllIn("Map(a -> List((a,b), (a,f)), c -> List((c,d))))").flatMap(txt => Pattern.unapplySeq(txt)).flatten


    println(res4.toList)

    val precededByOpenParen = """(?<=[,])([a-z])""".r
    println("====")
    println(precededByOpenParen.findAllIn("""Map(a -> List((a,b), (a,f)), c -> List((c,d))))""").flatMap(precededByOpenParen.unapplySeq(_)).flatten.toList)


  }
}




