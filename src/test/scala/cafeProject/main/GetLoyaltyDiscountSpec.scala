package cafeProject.main

import cafeProject.main.Main.getLoyaltyDiscount
import org.scalatest.wordspec.AnyWordSpec

class GetLoyaltyDiscountSpec extends AnyWordSpec {

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

}
