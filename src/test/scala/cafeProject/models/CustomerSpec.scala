package cafeProject.models

import org.scalatest.wordspec.AnyWordSpec



class CustomerSpec extends AnyWordSpec {
  val john = Customer("John", List(MenuItem(3, Hot, Food)), Some(3))

  "Customer" should {
    "Serialize a customer object" in {
      assert(john == Customer("John", List(MenuItem(3, Hot, Food)), Some(3)))
    }
    "Deserialize a customer" in {
      assert(john.name == "John")
    }
  }
}
