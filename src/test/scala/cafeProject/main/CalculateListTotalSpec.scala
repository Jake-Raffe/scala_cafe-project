package cafeProject.main

import org.scalatest.wordspec.AnyWordSpec
import cafeProject.main.Main.{calculateListTotal, checkIfIsHappyHour}
import cafeProject.models.{Cold, Drink, Food, Hot, MenuItem, Premium}

class CalculateListTotalSpec extends AnyWordSpec {
  val coke: MenuItem = MenuItem(0.5, Cold, Drink)
  val coffee: MenuItem = MenuItem(1.0, Hot, Drink)
  val cheeseSandwich: MenuItem = MenuItem(2.0, Cold, Food)
  val steakSandwich: MenuItem = MenuItem(4.5, Hot, Food)
  val lobster = MenuItem(25, Hot, Premium)
  val premiumList = List(coke, coffee, cheeseSandwich, steakSandwich, lobster)

  "calculateListTotal" should {
    "return total cost of list with half price drinks when is happy hour" in {
      checkIfIsHappyHour(18)
      assert(calculateListTotal(premiumList) == 32.25)
    }
    "return total cost of list when is not happy hour" in {
      assert(calculateListTotal(premiumList) == 33)
    }
  }

}
