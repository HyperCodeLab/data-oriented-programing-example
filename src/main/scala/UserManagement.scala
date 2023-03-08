package wiki.dataengineering.dop

import wiki.dataengineering.utils.Lodash.{_get, _has, _set}

object UserManagement {

  def isLibrarian(userManagementData: Map[String, Any], email: String): Boolean = {
    _has(userManagementData, Array("librariansByEmail", email))
  }

  def isVIPMember(userManagementData: Map[String, Any], email: String) : Boolean = {
    _get(userManagementData, Array("librariansByEmail", email, "isVIP")).isInstanceOf[Boolean] == true
  }

  def isSuperMember(userManagementData: Map[String, Any], email: String) : Boolean = {
    _get(userManagementData, Array("librariansByEmail", email, "isSuper")).isInstanceOf[Boolean] == true
  }

  def addMember(userManagementData: Map[String, Any], member: Map[String, Any]): Map[String, Any] = {
    val email = _get(member, Array("email")).asInstanceOf[String]
    val infoPath = Array("membersByEmail", email)

    if(_has(userManagementData, infoPath)) {
      throw new Exception("Member already Exists")
    }
    _set(userManagementData, infoPath, member)
  }


}
