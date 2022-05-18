package cafeProject.main

import org.scalatest.wordspec.AnyWordSpec
import Main._

class AddTipToTotalSpec extends AnyWordSpec{

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

}
