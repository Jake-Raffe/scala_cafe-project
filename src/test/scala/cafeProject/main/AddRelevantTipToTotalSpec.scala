package cafeProject.main

import cafeProject.models.{Cold, Drink, Food, Hot, MenuItem, Premium}
import org.scalatest.wordspec.AnyWordSpec
import cafeProject.main.Main._

class AddRelevantTipToTotalSpec extends AnyWordSpec {
  val coke = MenuItem(0.5, Cold, Drink)
  val coffee = MenuItem(1.0, Hot, Drink)
  val cheeseSandwich = MenuItem(2.0, Cold, Food)
  val steakSandwich = MenuItem(4.5, Hot, Food)
  val lobster = MenuItem(25, Hot, Premium)
  val premiumReceipt = List(coke, coffee, cheeseSandwich, steakSandwich, lobster)
  val hotFoodReceipt = List(coke, coffee, cheeseSandwich, steakSandwich)
  val coldFoodReceipt = List(coke, coffee, cheeseSandwich)
  val drinksReceipt = List(coke, coffee, coke, coffee, coke, coffee, coke, coffee)

  "addRelevantTipToTotal" should {
    "add no tip when list contains only drinks" in {
      assert(addRelevantTipToTotal(6, drinksReceipt) == 6)
    }
    "add anyFoodTip when list contains cold food but not hot or premium" in {
      assert(addRelevantTipToTotal(3.5, coldFoodReceipt) == 3.85)
    }
    "add hotFoodTip when list contains hot food but not premium" in {
      assert(addRelevantTipToTotal(8, hotFoodReceipt) == 9.6)
    }
    "add PremiumFoodTip when list contains premium items" in {
      assert(addRelevantTipToTotal(33, premiumReceipt) == 33*1.25)
    }
  }

}
