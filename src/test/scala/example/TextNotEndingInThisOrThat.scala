package example

//NOT
//OR
//MULTILINE
class TextNotEndingInThisOrThat extends Spec {

  "if text not ends in , or whitespace, match last character in single line string" in {

    """[^,\s]$""".r.findAllIn("""SE,MLI,Client-Entity,longcode,date,,""") shouldBe empty
    """[^,\s]$""".r.findAllIn("""SE,MLI,Client-Entity,longcode,date,,"ddd"""").toList shouldBe List("\"")
  }

  "if line not ends in , or whitespace, match last character in each line" in {

    """(?m)[^,\s]$""".r.findAllIn(
      """SE,MLI,Client-Entity,longcode,date,,"reason"
        |SE,MLI,Client-Entity,longcode,date,,
        |SE,MLI,Client-Entity,longcode,date,,"reason
      """.stripMargin).toList shouldBe List("\"", "n")
  }

  "if text not ends in , or whitespace, match last character in text" in {

    """[^,\s]$""".r.findAllIn(
      """SE,MLI,Client-Entity,longcode,date,,"reason"
        |SE,MLI,Client-Entity,longcode,date,,
        |SE,MLI,Client-Entity,longcode,date,,"reason""".stripMargin).toList shouldBe List("n")
  }

}
