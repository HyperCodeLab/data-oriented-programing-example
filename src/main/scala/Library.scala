package wiki.dataengineering.dop
import org.json4s.DefaultFormats
import org.json4s.native.Json
import wiki.dataengineering.utils.Lodash

object Library {

  def searchBookByTitleJSON(libraryData: Map[String, Any], query: String): String = {
    val catalogData = Lodash._get(libraryData, Array("catalog")).asInstanceOf[Map[String, Any]]
    val results = Catalog.searchBookByTitle(catalogData, query)
    Json(DefaultFormats).write(results)
  }

  def getBookLendings(libraryData: Map[String, Any], unserId: String, memberId: String) = {
    //if(UserManagement.isLibrarian(libraryData.))
  }

  def addMember(libraryData: Map[String, Any], member: Map[String, Any]): Map[String, Any] = {
    val currentUserManagement = Lodash._get(libraryData, Array("userManagement"))
    val nextUserManagement = UserManagement.addMember(currentUserManagement.asInstanceOf[Map[String, Any]], member)

    Lodash._set(libraryData, Array("userManagement"), nextUserManagement)
  }

}
