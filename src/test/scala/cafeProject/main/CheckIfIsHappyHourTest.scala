package cafeProject.main

import org.scalatest.wordspec.AnyWordSpec
import cafeProject.main.Main.checkIfIsHappyHour

class CheckIfIsHappyHourTest extends AnyWordSpec {

  "Main" should {
    "determine it is happy hour at 6pm" in {
      checkIfIsHappyHour(hourNow = 19)
    }
    "determine it is not happy hour at 10am" in {
      !checkIfIsHappyHour(hourNow=10)
    }
  }
}