package wiki.dataengineering
package utils

object Lodash {

  def _get(m: Map[String, Any], path: Array[String]): Any = {
    var mt: Map[String, Any] = m
    var res: Any = null

    if(path.length == 1) {
      res = mt.getOrElse(path(0), null)
    } else {
      for(i <- 0 until path.length) {
        var key = path(i)
        if(i  == path.length -1) {
          res = mt.getOrElse(key, null)
        } else {
          mt = mt.getOrElse(key, Map.empty).asInstanceOf[Map[String, Any]]
        }
      }
    }
    res
  }

  def _has(m:Map[String, Any], path: Array[String] ): Boolean = {
    var mt: Map[String, Any] = m
    var res: Boolean = false

    //Early exit
    if(mt.isEmpty) return false

    if(path.length == 1) {
      res = mt.contains(path(0))
    } else {
      for(i <- 0 until path.length) {
        val key = path(i)
        if(i == path.length -1) {
          res = mt.contains(path(i))
        } else {
          mt = mt.getOrElse(key, null).asInstanceOf[Map[String, Any]]
        }
      }
    }
    res
  }

  def _set(data: Map[String, Any], path: Array[String], value: Any): Map[String, Any] = {
    var modifiedNode: Any = value
    var k: String = path(0)
    var restOfPath: Array[String] = path.slice(1, path.length)

    if(restOfPath.length >0) {
      val newData = if(data.isEmpty) data + (k -> Map.empty[String, Any]) else data
      modifiedNode = _set(newData.getOrElse(k, null).asInstanceOf[Map[String, Any]], restOfPath, value)
    }

    var res = data
    res + (k -> modifiedNode)
  }

  def _isEqual(data: Any, otherData: Any): Boolean = {
    data.equals(otherData)
  }

}
