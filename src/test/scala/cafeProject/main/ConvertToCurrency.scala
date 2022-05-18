package cafeProject.main

import org.scalatest.wordspec.AnyWordSpec
import cafeProject.main.Main.convertToCurrency
import cafeProject.models.Currency._


class ConvertToCurrency extends AnyWordSpec{

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

}
