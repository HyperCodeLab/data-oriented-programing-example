package wiki.dataengineering

object SystemState {

  var systemState: Map[String, Any] = null
  var previousSystemData: Map[String, Any] = null

  def get(): Map[String, Any]  = this.systemState

  def commit(previous: Map[String, Any], next: Map[String, Any]): Unit = {
    val systemDataBefireYpdate = this.systemState
    if(!validate(previous, next)) {
      throw new Exception("The system data to be commited is not valid!")
    }
    this.systemState = next
    this.previousSystemData = systemDataBefireYpdate
  }

  def undoLastMutatin(): Unit = {
    this.systemState = this.previousSystemData
  }

  def validate(previous: Map[String, Any], next: Map[String, Any]): Boolean = {
    true
  }

}
