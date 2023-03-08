package wiki.dataengineering
package utils

import org.json4s.jackson.JsonMethods
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should
import wiki.dataengineering.dop.Catalog

class CatalogTest extends AnyFunSuite with should.Matchers {

  var libraryDataSTR =
    """{
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
      |  }
      |}""".stripMargin
  val libraryData = JsonMethods.parse(libraryDataSTR).values.asInstanceOf[Map[String, Any]]

  val expectedBook =  Map("authorNames" -> List("Alan Moore", "Dave Gibbons"), "isbn" -> "978-1779501127", "title" -> "Watchmen")

  test("Catalog autorNames") {
    val data = Map("authorsById" -> Map(
        "alan-moore" -> Map("name" -> "Alan Moore"),
        "dave-gibbons" -> Map("name" -> "Dave Gibbons")
    ))

    Lodash._isEqual(Catalog.authorNames(data, List.empty[String]), List.empty[String]) should be (true)

    Lodash._isEqual(Catalog.authorNames(data, List("alan-moore")), List("Alan Moore")) should be (true)

    Lodash._isEqual(Catalog.authorNames(data, List("alan-moore", "dave-gibbons")), List("Alan Moore", "Dave Gibbons")) should be (true)

    Lodash._isEqual(Catalog.authorNames(Map.empty[String, Any], List.empty[String]), List.empty[String]) should be (true)

    Lodash._isEqual(Catalog.authorNames(Map.empty[String, Any], List("alan-moore")), List.empty[String]) should be (true)

    Lodash._isEqual(Catalog.authorNames(data, List("alan-moore", "albert-einstein")), List("Alan Moore")) should be (true)

    Lodash._isEqual(Catalog.authorNames(data,  List("albert-einstein")), List.empty[String]) should be (true)
  }

  test("Catalog bookInfo") {
    val catalogData = Map("authorsById" -> Map("alan-moore" -> Map("name" -> "Alan Moore"), "dave-gibbons" -> Map("name" -> "Dave Gibbons")))
    val book = Map("isbn"-> "978-1779501127", "title"-> "Watchmen", "publicationYear" -> 1987, "authorIds" -> List("alan-moore", "dave-gibbons"))

    val expectedResult = Map("authorNames" -> List("Alan Moore", "Dave Gibbons"), "isbn" -> "978-1779501127", "title" -> "Watchmen")

    val result = Catalog.bookInfo(catalogData, book)

    Lodash._isEqual(result, expectedResult)
  }

  test("Catalog Search Book By Title") {

    Lodash._isEqual(Catalog.searchBookByTitle(libraryData, "Watchmen"), List(expectedBook)) should be (true)
    Lodash._isEqual(Catalog.searchBookByTitle(libraryData, "watchmen"), List(expectedBook)) should be (true)

    Lodash._isEqual(Catalog.searchBookByTitle(libraryData, "Batman"), List.empty) should be (true)
  }

}
