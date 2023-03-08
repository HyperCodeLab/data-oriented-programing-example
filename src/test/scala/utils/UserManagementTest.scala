package wiki.dataengineering
package utils

import dop.UserManagement

import org.json4s.jackson.JsonMethods
import org.scalatest._
import flatspec._
import matchers._
import org.scalatest.funsuite.AnyFunSuite

class UserManagementTest extends AnyFunSuite with should.Matchers {

  val userManagementDataSTR =
    """{
      |  "librariansByEmail": {
      |    "franck@gmail.com" : {
      |      "email": "franck@gmail.com",
      |      "encryptedPassword": "bXlwYXNzd29yZA=="
      |    },
      |    "franckVip@gmail.com" : {
      |      "email": "franck@gmail.com",
      |      "encryptedPassword": "bXlwYXNzd29yZA==",
      |      "isVIP": true,
      |      "isSuper": true
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
  val userManagementData = JsonMethods.parse(userManagementDataSTR).values.asInstanceOf[Map[String, Any]]


  test("isLibrarian_rightParameters_rightOutput") {
    val output = UserManagement.isLibrarian(userManagementData, "franck@gmail.com")
    assert(output)
  }


  test ("isVipMember_rightParameters_rightOutput") {
    val output1 = UserManagement.isVIPMember(userManagementData, "franck@gmail.com")
    output1 should be (false)

    val output2 = UserManagement.isVIPMember(userManagementData, "franckVip@gmail.com")
    output2 should be (true)
  }


  test ("isSuperMember_rightParameters_rightOutput") {
    val output = UserManagement.isSuperMember(userManagementData, "franck@gmail.com")
    output should be (false)

    val output2 = UserManagement.isSuperMember(userManagementData, "franckVip@gmail.com")
    output2 should be (true)
  }


  test("addMember_rightParameters_rightOutput") {
    val newMember: Map[String, Any] = Map("email" -> "test@.com", "encryptedPassword" -> "", "isBlocked" -> false, "bookLendings" -> List.empty[Map[String, Any]])

    // Add new user
    Lodash._has(userManagementData, Array("membersByEmail", "test@.com")) should be (false)

    val output = UserManagement.addMember(userManagementData, newMember)
    Lodash._has(output, Array("membersByEmail", "test@.com")) should be (true)

    // Add existing user
    assertThrows[Exception]{UserManagement.addMember(output, newMember) }
  }

  test ("UserManagement addMember without members") {
    val member: Map[String, Any] = Map("email" ->"jessie@gmail.com", "password" -> "my-secret")
    val userManagementStateBefore: Map[String, Any] = Map.empty

    val expectedUserManagementStateAfter: Map[String, Any] = Map(
      "membersByEmail" -> Map(
        "jessie@gmail.com" -> Map(
          "email" -> "jessie@gmail.com",
          "password" -> "my-secret"
        )
      )
    )
    val result = UserManagement.addMember(userManagementStateBefore, member)
    println(result)
    Lodash._isEqual(result, expectedUserManagementStateAfter)
  }

  test ("UserManagement addMember new with existing member") {
    val jessie: Map[String, Any] = Map("email" -> "jessie@gmail.com", "password" -> "my-pass")
    val franck: Map[String, Any] = Map("email" -> "franck@gmail.com", "password" -> "my-pass-2")

    val userManagementStateBefore: Map[String, Any] = Map(
      "membersByEmail" -> Map(
        "franck@gmail.com" -> Map(
          "email" -> "franck@gmail.com",
          "password" -> "my-pass-2"
        )
      )
    )

    val expectedUserManagementStateAfter: Map[String, Any] = Map(
      "membersByEmail" -> Map(
        "franck@gmail.com" -> franck,
        "jessie@gmail.com" -> jessie
      )
    )

    val result = UserManagement.addMember(userManagementStateBefore, jessie)
    println(result)
    Lodash._isEqual(result, expectedUserManagementStateAfter)
  }

  test ("UserManagement addMember exiting member twice") {
    val jessie: Map[String, Any] = Map("email" -> "jessie@gmail.com", "password" -> "my-pass")
    val userManagementStateBefore: Map[String, Any] = Map(
      "membersByEmail" -> Map(
        "jessie@gmail.com" -> jessie
      )
    )

    val message = intercept[Exception] {
      val result = UserManagement.addMember(userManagementStateBefore, jessie)
    }
    
    message.getMessage should be ("Member already Exists")
  }

}
