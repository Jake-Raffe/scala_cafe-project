package cafeProject.main

import org.scalatest.wordspec.AnyWordSpec
import cafeProject.main.Main._
import cafeProject.models.{Cold, Drink, Food, Hot, MenuItem, Premium}


class RemoveOrReturnPremiumItems extends AnyWordSpec {
  val coke: MenuItem = MenuItem(0.5, Cold, Drink)
  val coffee: MenuItem = MenuItem(1.0, Hot, Drink)
  val cheeseSandwich: MenuItem = MenuItem(2.0, Cold, Food)
  val steakSandwich: MenuItem = MenuItem(4.5, Hot, Food)
  val lobster: MenuItem = MenuItem(25, Hot, Premium)
  val testPremiumReceipt: List[MenuItem] = List(coke, coffee, cheeseSandwich, steakSandwich, lobster)

  "removePremiumItemsFromList" should {
    "return only non-premium items from a list" in {
      assert(removePremiumItemsFromList(testPremiumReceipt) == List(coke, coffee, cheeseSandwich, steakSandwich))
    }
  }
  "returnPremiumItemsFromList" should {
    "return only premium items from a list" in {
      assert(returnPremiumItemsFromList(testPremiumReceipt) == List(lobster))
    }
  }

}
