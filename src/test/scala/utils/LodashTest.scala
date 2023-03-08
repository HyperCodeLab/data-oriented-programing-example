package wiki.dataengineering
package utils

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should

class LodashTest extends AnyFunSuite with should.Matchers {

  val data: Map[String, Any] = Map("Value1"->1,
                                   "Value2" -> Map("Nested1" -> "Value" ,
                                                   "Nested2" -> Map("NestedNested1" -> "Nested Value")))


  test("_has_oneLevel_rightParameters_rightOutput") {
    Lodash._has(data, Array("Value1")) should be (true)
    Lodash._has(data, Array("Value3")) should be (false)
  }


  test ("_has_multipleLevel_rightParameters_rightOutput") {
    Lodash._has(data, Array("Value2", "Nested1")) should be (true)
    Lodash._has(data, Array("Value2", "Nested3")) should be (false)
  }

  test ("_set_rightParameters_rightOutput") {
    // Check original value
    Lodash._get(data, Array("Value1")).asInstanceOf[Int] should be (1)

    //Modify initial path value
    val data2 = Lodash._set(data, Array("Value1"), "OK")
    Lodash._get(data2, Array("Value1")).asInstanceOf[String] should be ("OK")

    //Modify Second path value
    val data3 = Lodash._set(data, Array("Value2", "Nested1"), "New value")
    Lodash._get(data3, Array("Value2", "Nested1")).asInstanceOf[String] should be("New value")

    //Modify third path value
    val data4 = Lodash._set(data, Array("Value2", "Nested2", "NestedNested1"), "New Nested value")
    Lodash._get(data4, Array("Value2", "Nested2", "NestedNested1")).asInstanceOf[String] should be("New Nested value")

    // Set on empty map
    val data5 = Lodash._set(Map[String, Any](), Array("Value1", "Nested1"), "OK")
    println(data5)
  }




  test("Comparing data collections") {
    val data1 = Map("name"->"Alan Moore", "bookIsbns" -> Array("978-1779501127"))
    val data2 = Map("name"->"Alan Moore", "bookIsbns" -> Array("Bad ISBN"))

    Lodash._isEqual(data1, data1) should be (true)
    Lodash._isEqual(data1, data2) should be (false)
  }

}
