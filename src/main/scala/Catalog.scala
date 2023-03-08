package wiki.dataengineering.dop

import wiki.dataengineering.utils.Lodash
import wiki.dataengineering.utils.Lodash._get

object Catalog {

  def authorNames(catalogData: Map[String, Any], authorsIds: List[String]): List[String] = {
    val names = authorsIds.map(x => {
      val o = _get(catalogData, Array("authorsById", x, "name"))
      if(o != null) o.toString
      else ""
    })
    names.filter(!_.isBlank)
  }

  def bookInfo(catalogData: Map[String, Any], book: Map[String, Any]): Map[String, Any] = {
    Map[String, Any] (
      "title" -> Lodash._get(book, Array("title")),
      "isbn" -> Lodash._get(book, Array("isbn")),
      "authorNames" -> authorNames(catalogData, _get(book, Array("authorIds")).asInstanceOf[List[String]])
    )
  }

  def searchBookByTitle(catalogData: Map[String, Any], query: String): List[Map[String, Any]] = {
    val allBooks = _get(catalogData, Array("booksByIsbn"))
    val queryLowerCased = query.toLowerCase()
    val matchingBooks = allBooks.asInstanceOf[Map[String, Map[String, Any]]].values.toList.filter( book => {
      _get(book, Array("title")).asInstanceOf[String].toLowerCase().contains(queryLowerCased)
    })

    val bookInfos = matchingBooks.map(book => bookInfo(catalogData, book))
    bookInfos
  }

}
