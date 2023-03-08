package wiki.dataengineering
package utils

import org.json4s.jackson.JsonMethods
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should
import wiki.dataengineering.dop.Library

class LibraryTest extends AnyFunSuite with should.Matchers {

  var libraryDataSTR =
    """{
      |  "catalog": {
      |    "booksByIsbn": {
      |      "978-1779501127": {
      |        "isbn": "978-1779501127",
      |        "title": "Watchmen",
      |        "publicationYear": 1987,
      |        "authorIds": ["alan-moore",
      |          "dave-gibbons"]
      |      }
      |    },
      |    "authorsById": {
      |      "alan-moore": {
      |        "name": "Alan Moore",
      |        "bookIsbns": ["978-1779501127"]
      |      },
      |      "dave-gibbons": {
      |        "name": "Dave Gibbons",
      |        "bookIsbns": ["978-1779501127"]
      |      }
      |    }
      |  }
      |}""".stripMargin
  val libraryData = JsonMethods.parse(libraryDataSTR).values.asInstanceOf[Map[String, Any]]

  test("Library searchBooksByTitleJSON"){
    val expectedBook: Map[String, Any] = Map("isbn" -> "978-1779501127", "title"->"Watchmen", "authorNames"-> List("Alan Moore", "Dave Gibbons"))
    val output1 = Library.searchBookByTitleJSON(libraryData, "Watchmen")
    Lodash._isEqual(JsonMethods.parse(output1).values, List(expectedBook)) should be (true)

    val output2 = Library.searchBookByTitleJSON(libraryData, "Batman")
    Lodash._isEqual(JsonMethods.parse(output2).values, List.empty) should be (true)
  }

  test ("Library addMember") {

  }

}
