package cafeProject.models

import org.scalatest.wordspec.AnyWordSpec

class MenuItemSpec extends AnyWordSpec {
  val fish = MenuItem(3.2, Hot, Food)

  "MenuItem" should {
    "Serialize a menu item object" in {
      assert(fish == MenuItem(3.2, Hot, Food))
    }
    "Deserialize a menu item object" in {
      assert(fish.temp == Hot)
    }
  }
}