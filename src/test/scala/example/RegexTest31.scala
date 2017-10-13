package example

import org.scalatest.{FlatSpec, Matchers}

class RegexTest31 extends FlatSpec with Matchers {


  "b" should """match on word (\w+)""" in {

    val Pattern = """[^a-z]([a-z])""".r

    var list = Pattern.findAllIn(",a))b,xyz").toList

    println("--")
    println(list)
    list = """(?<![a-z])([a-z])""".r.findAllIn(",a))b,xyz").toList      //not preceded by [a-z]
    println(list)
    list = """(?<=,)([a-z])""".r.findAllIn(",a))b,xyz").toList          //preceded by comma
    println(list)
    list = """([a-z])(?=z)""".r.findAllIn(",a))b,xyz").toList
    println(list)
    list = """(?<=,)([a-z])|([a-z])(?=z)""".r.findAllIn(",a))b,xyz").toList
    println(list)


    val res2: Option[List[String]] = Pattern.unapplySeq(",a)")

    println(res2)


    val res3 = Pattern.findAllIn(",a))b,").flatMap(txt => Pattern.unapplySeq(txt)).flatten

    println(res3.toList)

    val res4 = Pattern.findAllIn("Map(a -> List((a,b), (a,f)), c -> List((c,d))))").flatMap(txt => Pattern.unapplySeq(txt)).flatten


    println(res4.toList)

    val precededByOpenParen = """([a-z])(?=\))""".r
    println("====")
    println(precededByOpenParen.findAllIn("""Map(a -> List((a,b), (a,f)), c -> List((c,d))))""").toList)


  }
}




