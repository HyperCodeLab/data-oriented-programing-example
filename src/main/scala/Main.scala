package wiki.dataengineering.dop

import org.json4s.jackson.JsonMethods.parse

object Main {

  def main(args: Array[String]): Unit = {

    val userManagementDataSTR = """{
                                  |  "librariansByEmail": {
                                  |    "franck@gmail.com" : {
                                  |      "email": "franck@gmail.com",
                                  |      "encryptedPassword": "bXlwYXNzd29yZA=="
                                  |    }
                                  |  },
                                  |  "membersByEmail": {
                                  |    "samantha@gmail.com": {
                                  |      "email": "samantha@gmail.com",
                                  |      "encryptedPassword": "c2VjcmV0",
                                  |      "isBlocked": false,
                                  |      "bookLendings": [
                                  |        {
                                  |          "bookItemId": "book-item-1",
                                  |          "bookIsbn": "978-1779501127",
                                  |          "lendingDate": "2020-04-23"
                                  |        }
                                  |      ]
                                  |    }
                                  |  }
                                  |}""".stripMargin
    val userManagementData = parse(userManagementDataSTR).values.asInstanceOf[Map[String, Any]]

    var libraryDataSTR = """{
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

    println(Library.searchBookByTitleJSON(libraryData, "Wat"))

    println(UserManagement.isLibrarian(userManagementData, "franck@gmail.com"))

    println(UserManagement.isVIPMember(userManagementData, "franck@gmail.com"))


  }

}
