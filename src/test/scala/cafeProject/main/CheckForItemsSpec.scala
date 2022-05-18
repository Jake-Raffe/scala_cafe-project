package cafeProject.main

import org.scalatest.wordspec.AnyWordSpec
import cafeProject.main.Main._
import cafeProject.models.{Cold, Drink, Food, Hot, ItemType, MenuItem, Premium}
//import cafeProject.main.Main.{hotFoodReceipt, premiumReceipt, coldFoodReceipt, checkForPremiumFood, checkForAnyFood, checkForHotFood}

class CheckForItemsSpec extends AnyWordSpec{
  val cok = MenuItem(0.5, Cold, Drink)
  val coffe = MenuItem(1.0, Hot, Drink)
  val cheeseSandwic = MenuItem(2.0, Cold, Food)
  val steakSandwic = MenuItem(4.5, Hot, Food)
  val lobste = MenuItem(25, Hot, Premium)
  val premiumReceiptt = List(cok, coffe, cheeseSandwic, steakSandwic, lobste)
  val hotFoodReceiptt = List(cok, coffe, cheeseSandwic, steakSandwic)
  val coldFoodReceiptt = List(cok, coffe, cheeseSandwic)
  val drinksReceiptt = List(cok, coffe, cok, coffe, cok, coffe, cok, coffe)

  "checkForAnyFood" should {
    "return true when list contains food items" in {
      checkForAnyFood(hotFoodReceiptt)
    }
  }
  "checkForHotFood" should {
    "return false when list does not contain hot food items" in {
      !checkForHotFood(coldFoodReceiptt)
    }
  }
  "checkForPremiumFood" should {
    "return true when list contains premium food items" in {
      checkForPremiumFood(premiumReceiptt)
    }
  }

}
