package example

import scala.annotation.tailrec

/**
  * sliding goes from start to finish
  * recursion goes from the whole string to smaller parts
  */
class OccurrencesInString extends Spec {
  val source = """sldkfjl "status":"ok" slflsk "status":"ok" lsdfjl   """
  val term = "status"

  "count occurrences of term with sliding" in {

    val res: Iterator[String] = source.sliding(term.size).filter {
      case substr => substr == term
    }

    res.size shouldBe 2


  }


  "count with regex" in {


    val iterator = term.r.findAllIn(source)

    iterator.size shouldBe 2

  }


  "count recursively" in {

    def find(str: String, substr: String): Int = {
      if (str.size < substr.size) 0 else {
        val cnt = if (str.substring(0, substr.size) == substr) 1 else 0
        cnt + find(str.tail, substr)
      }
    }

    find(source, term) shouldBe 2
  }

  "count tailrec" in {

    @tailrec
    def find0(source: String, term: String, res: Int): Int = {

      if (source.size < term.size) res else {
        if (source.substring(0, term.size)==term) find0(source.tail, term, res+1) else {
          find0(source.tail, term, res)
        }
      }
    }

    find0(source,term,0) shouldBe 2
    find0(source,"k",0) shouldBe 4
  }

  "count tailrec2" in {

    import scala.annotation.tailrec
    def find0(str1:String, str2:String):Int={
      @tailrec def count(pos:Int, c:Int):Int={
        val idx=str1 indexOf(str2, pos)
        if(idx == -1) c else count(idx+str2.size, c+1)
      }
      count(0,0)
    }
    find0(source,term) shouldBe 2
    find0(source,"k") shouldBe 4
  }

}
