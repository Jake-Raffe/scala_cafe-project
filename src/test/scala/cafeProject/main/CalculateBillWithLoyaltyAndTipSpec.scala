package cafeProject.main

import cafeProject.main.Main.{calculateBillWithLoyaltyAndTip}
import cafeProject.models.{Cold, Customer, Drink, Food, Hot, MenuItem, Premium}
import org.scalatest.wordspec.AnyWordSpec

class CalculateBillWithLoyaltyAndTipSpec extends AnyWordSpec{
  val coke: MenuItem = MenuItem(0.5, Cold, Drink)
  val coffee: MenuItem = MenuItem(1.0, Hot, Drink)
  val cheeseSandwich: MenuItem = MenuItem(2.0, Cold, Food)
  val steakSandwich: MenuItem = MenuItem(4.5, Hot, Food)
  val hotFoodReceipt: List[MenuItem] = List(coke, coffee, cheeseSandwich, steakSandwich)
  val james: Customer = Customer("James", hotFoodReceipt, Some(5))

  "calculateBillWithLoyaltyAndTip" should {
    "calculate a new total taking into account relevant discount and tip" in {
      assert(calculateBillWithLoyaltyAndTip(james) == 8*1.2*0.875)
    }
  }

}
