package wiki.dataengineering
package utils

import org.json4s.jackson.JsonMethods.parse
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should

class StateManagementTest extends AnyFunSuite with should.Matchers  {

  val libraryDataSTR =
    """{
      |  "catalog": {
      |    "booksByIsbn": {
      |      "978-1779501127": {
      |        "isbn": "978-1779501127",
      |        "title": "Watchmen",
      |        "publicationYear": 1987,
      |        "authorIds": ["alan-moore",
      |          "dave-gibbons"],
      |        "bookItems": [
      |          {
      |            "id": "book-item-1",
      |            "libId": "nyc-central-lib",
      |            "isLent": true
      |          },
      |          {
      |            "id": "book-item-2",
      |            "libId": "nyc-central-lib",
      |            "isLent": false
      |          }
      |        ]
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
  val libraryData = parse(libraryDataSTR).values.asInstanceOf[Map[String, Any]]


  test ("") {
    val nextLlibraryData = Lodash._set(libraryData, Array("catalog", "booksByIsbn", "978-1779501127", "publicationYear"), 1986)
    println(nextLlibraryData)
  }


}
