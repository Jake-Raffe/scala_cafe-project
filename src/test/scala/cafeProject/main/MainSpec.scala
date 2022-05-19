package cafeProject.main

import cafeProject.models.{Cold, Customer, Drink, Food, Hot, MenuItem, Premium}
import org.scalatest.wordspec.AnyWordSpec
import cafeProject.main.Main.{addRelevantTipToTotal, anyFoodTotalWithTip, calculateBillWithLoyaltyAndTip, calculateListTotal, checkForAnyFood, checkForHotFood, checkForPremiumFood, checkIfIsHappyHour, convertToCurrency, getHappyHourTotal, getLoyaltyDiscount, hotFoodTotalWithTip, premiumFoodTotalWithTip, removePremiumItemsFromList, returnPremiumItemsFromList}
import cafeProject.models.Currency.{EUR, GBP, USD}

class MainSpec extends AnyWordSpec {
  val coke: MenuItem = MenuItem(0.5, Cold, Drink)
  val coffee: MenuItem = MenuItem(1.0, Hot, Drink)
  val cheeseSandwich: MenuItem = MenuItem(2.0, Cold, Food)
  val steakSandwich: MenuItem = MenuItem(4.5, Hot, Food)
  val lobster: MenuItem = MenuItem(25, Hot, Premium)
  val premiumList: List[MenuItem] = List(coke, coffee, cheeseSandwich, steakSandwich, lobster)
  val hotFoodList: List[MenuItem] = List(coke, coffee, cheeseSandwich, steakSandwich)
  val coldFoodList: List[MenuItem] = List(coke, coffee, cheeseSandwich)
  val drinksList: List[MenuItem] = List(coke, coffee, coke, coffee, coke, coffee, coke, coffee)
  val james: Customer = Customer("James", hotFoodList, Some(5))

  "addRelevantTipToTotal" should {
    "add no tip when list contains only drinks" in {
      assert(addRelevantTipToTotal(6, drinksList) == 6)
    }
    "add anyFoodTip when list contains cold food but not hot or premium" in {
      assert(addRelevantTipToTotal(3.5, coldFoodList) == 3.85)
    }
    "add hotFoodTip when list contains hot food but not premium" in {
      assert(addRelevantTipToTotal(8, hotFoodList) == 9.6)
    }
    "add PremiumFoodTip when list contains premium items" in {
      assert(addRelevantTipToTotal(33, premiumList) == 33*1.25)
    }
  }
  "anyFoodTotalWithTip" should {
    "add 10% to the total" in {
      assert(anyFoodTotalWithTip(28) == 30.8)
    }
  }
  "hotFoodTotalWithTip" should {
    "add 20% to the total" in {
      assert(hotFoodTotalWithTip(12) == 14.4)
    }
    "add a maximum of 20 to the total if 20% of total would be greater than 20" in {
      assert(hotFoodTotalWithTip(120) == 140)
    }
  }
  "premiumFoodTotalWithTip" should {
    "add 25% to the total" in {
      assert(premiumFoodTotalWithTip(100) == 125)
    }
    "add a maximum of 40 to the total if tip of total would be >= 40" in {
      assert(premiumFoodTotalWithTip(600) == 640)
    }
  }

  "calculateBillWithLoyaltyAndTip" should {
    "calculate a new total taking into account relevant discount and tip" in {
      assert(calculateBillWithLoyaltyAndTip(james) == 8*1.2*0.875)
    }
  }

  "checkForAnyFood" should {
    "return true when list contains food items" in {
      checkForAnyFood(hotFoodList)
    }
  }
  "checkForHotFood" should {
    "return false when list does not contain hot food items" in {
      !checkForHotFood(coldFoodList)
    }
  }
  "checkForPremiumFood" should {
    "return true when list contains premium food items" in {
      checkForPremiumFood(premiumList)
    }
  }

  "checkIfIsHappyHour" should {
    "determine it is happy hour at 6pm" in {
      checkIfIsHappyHour(Some(19))
    }
    "determine it is not happy hour at 10am" in {
      !checkIfIsHappyHour(Some(10))
    }
  }
  "getHappyHourTotal" should {
    "return total cost of list with half price drink when is happy hour" in {
      assert(getHappyHourTotal(coldFoodList) == 2.75)
    }
  }
  "calculateListTotal" should {
    "calculate total with half price drinks when is happy hour" in {
      assert(calculateListTotal(coldFoodList, Some(18)) == 2.75)
    }
    "calculate total with full price drinks when is not happy hour" in {
      assert(calculateListTotal(coldFoodList, Some(10)) == 3.5)
    }
  }

  "convertToCurrency" should {
    "return the same total when currency is GBP" in {
      assert(convertToCurrency(20, GBP) == 20)
    }
    "return the total * 1.17 when currency is EUR" in {
      assert(convertToCurrency(20, EUR) == 20*1.17)
    }
    "return the total * 1.22 when currency is USD" in {
      assert(convertToCurrency(20, USD) == 20*1.22)
    }
  }

  "getLoyaltyDiscount" should {
    "return 0 when loyalty = None" in {
      assert(getLoyaltyDiscount(None) == 0)
    }
    "return 0 when loyalty < 3>" in {
      assert(getLoyaltyDiscount(Some(2)) == 0)
    }
    "return 0.025 * loyalty when loyalty is less than 8 but minimum 3" in {
      assert(getLoyaltyDiscount(Some(3)) == 3*0.025)
    }
    "return 0.2 when loyalty >= 8" in {
      assert(getLoyaltyDiscount(Some(10)) == 0.2)
    }
  }

  "removePremiumItemsFromList" should {
    "return only non-premium items from a list" in {
      assert(removePremiumItemsFromList(premiumList) == List(coke, coffee, cheeseSandwich, steakSandwich))
    }
  }
  "returnPremiumItemsFromList" should {
    "return only premium items from a list" in {
      assert(returnPremiumItemsFromList(premiumList) == List(lobster))
    }
  }
}
