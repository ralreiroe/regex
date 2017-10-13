package example

import org.scalatest.{FlatSpec, Matchers}

class RegexCsvTest extends FlatSpec with Matchers {



  "Hello" should "have tests" in {

  /**
    *https://softwareengineering.stackexchange.com/questions/166454/can-the-csv-format-be-defined-by-a-regex
    */

    val re_valid = """
# Validate a CSV string having single, double or un-quoted values.
^                                   # Anchor to start of string.
\s*                                 # Allow whitespace before value.
(?:                                 # Group for value alternatives.
  '[^'\\]*(?:\\[\S\s][^'\\]*)*'     # Either Single quoted string,
                  | "[^"\\]*(?:\\[\S\s][^"\\]*)*"     # or Double quoted string,
                  | [^,'"\s\\]*(?:\s+[^,'"\s\\]+)*    # or Non-comma, non-quote stuff.
)                                   # End group of value alternatives.
\s*                                 # Allow whitespace after value.
(?:                                 # Zero or more additional values
  ,                                 # Values separated by a comma.
  \s*                               # Allow whitespace before value.
  (?:                               # Group for value alternatives.
    '[^'\\]*(?:\\[\S\s][^'\\]*)*'   # Either Single quoted string,
                  | "[^"\\]*(?:\\[\S\s][^"\\]*)*"   # or Double quoted string,
                  | [^,'"\s\\]*(?:\s+[^,'"\s\\]+)*  # or Non-comma, non-quote stuff.
  )                                 # End group of value alternatives.
  \s*                               # Allow whitespace after value.
)*                                  # Zero or more additional values
      $                                   # Anchor to end of string.
""".r


    println(re_valid.findAllIn(""""sasd","empty values"""").toList)


    /* https://stackoverflow.com/questions/18144431/regex-to-split-a-csv */

    println("""".+?"|[^"]+?(?=,)|(?<=,)[^"]+""".r.findAllIn("""123,2.99,AMO024,Title,"Description, more info",,123987564""").toList)
    println("""".+?"""".r.findAllIn("""123,2.99,AMO024,Title,"Description, more info",,123987564""").toList)
    println("""[^"]+?(?=,)""".r.findAllIn("""123,2.99,AMO024,Title,"Description, more info",,123987564""").toList)
    println("""(?<=,)[^"]+""".r.findAllIn("""123,2.99,AMO024,Title,"Description, more info",,123987564""").toList)

  }
}




