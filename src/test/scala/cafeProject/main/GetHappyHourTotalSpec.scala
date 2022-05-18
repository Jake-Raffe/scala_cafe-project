package cafeProject.main

import org.scalatest.wordspec.AnyWordSpec
import cafeProject.main.Main._
import cafeProject.models.{Cold, Drink, Food, Hot, MenuItem, Premium}

class GetHappyHourTotalSpec extends AnyWordSpec {
  val coke: MenuItem = MenuItem(0.5, Cold, Drink)
  val coffee: MenuItem = MenuItem(1.0, Hot, Drink)
  val cheeseSandwich: MenuItem = MenuItem(2.0, Cold, Food)
  val steakSandwich: MenuItem = MenuItem(4.5, Hot, Food)
  val lobster = MenuItem(25, Hot, Premium)
  val premiumList = List(coke, coffee, cheeseSandwich, steakSandwich, lobster)


//  "getHappyHourTotal" should {
//    "return total cost of list with half price drink when is happy hour" in {
//      assert(getHappyHourTotal())
//    }
//  }

}
