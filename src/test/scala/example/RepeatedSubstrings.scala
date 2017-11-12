package example

/**
  * https://stackoverflow.com/questions/928179/matching-on-repeated-substrings-in-a-regex
  */
class RepeatedSubstrings extends Spec {

  "back references " in {

    //he \1 refers back to whatever is matched by the contents of the first capture group
    val re = """^(.{3}).*\1$""".r.findAllIn("xyz abc xyz").toList
    println(re)
  }
}
