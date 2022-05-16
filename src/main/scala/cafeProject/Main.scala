package cafeProject

import Customer.*
import cafeProject.Currency.{EUR, GBP, USD}

import java.time.*
import java.time.format.DateTimeFormatter

object Main extends App {

  val timeFormat = DateTimeFormatter.ofPattern("h.mma")

  // menu items
  val coke = MenuItem(0.5, Cold, Drink)
  val coffee = MenuItem(1.0, Hot, Drink)
  val cheeseSandwich = MenuItem(2.0, Cold, Food)
  val steakSandwich = MenuItem(4.5, Hot, Food)
  val lobster = MenuItem(25, Hot, Premium)

  // varying lists  of items
  val premiumReceipt = List(coke, coffee, cheeseSandwich, steakSandwich, lobster)
  val hotFoodReceipt = List(coke, coffee, cheeseSandwich, steakSandwich)
  val coldFoodReceipt = List(coke, coffee, cheeseSandwich)
  val drinksReceipt = List(coke, coffee, coke, coffee, coke, coffee, coke, coffee)
  val bigReceipt = List(coke, coffee, cheeseSandwich, steakSandwich, steakSandwich, steakSandwich, steakSandwich, steakSandwich, steakSandwich,
    steakSandwich, steakSandwich, steakSandwich, steakSandwich, lobster, lobster, lobster, lobster, lobster)


  // if between 6-8pm, is happy hour
  def isHappyHour(time: Int = LocalTime.now().getHour): Boolean = if time > 18 && time < 20 then true else false

  // check if list contains any food items
  def checkAnyFood(items: List[MenuItem]): Boolean = {
    items.exists(_.itemType == Food)
  }

  // check if list contains any hot food items
  def checkHotFood(items: List[MenuItem]): Boolean = {
    items.exists(item => item.temp == Hot && item.itemType == Food)
  }

  // check if list contains premium items
  def checkPremium(items: List[MenuItem]): Boolean = {
    items.exists(_.itemType == Premium)
  }


  // calculate total cost of list, factoring in happy hour
  def calculateListTotal(items: List[MenuItem]): BigDecimal = {
    def getHappyHourTotal(): BigDecimal = {
      // sum non drinks + 1/2 sum drinks
      items.filter(_.itemType != Drink).map(_.cost).sum + (0.5 * items.filter(_.itemType == Drink).map(_.cost).sum)
    }
    if isHappyHour() then getHappyHourTotal() else items.map(_.cost).sum
  }

  // calculate total + any food tip
  def anyFoodTotalWithTip(total: BigDecimal): BigDecimal = {
    1.1 * total
  }

  // calculate total +  hot food tip
  def hotFoodTotalWithTip(total: BigDecimal): BigDecimal = {
    val tip = total * 0.2
    if (tip >= 20) 20 + total else tip + total
  }

  // calculate total + premium tip
  def premiumTotalWithTip(total: BigDecimal): BigDecimal = {
    val tip = total * 0.25
    if (tip >= 40) 40 + total else tip + total
  }


  // return list without premium items
  def removePremiumFromList(items: List[MenuItem]): List[MenuItem] = {
    items.filter(_.itemType != Premium)
  }

  // return only premium items
  def returnPremiumFromList(items: List[MenuItem]): List[MenuItem] = {
    items.filter(_.itemType == Premium)
  }


  // calculate total bill, plus tip depending on items in list
  def totalWithTip(total: BigDecimal, items: List[MenuItem]): BigDecimal = {
    if (checkPremium(items)) premiumTotalWithTip(total)
    else if (checkHotFood(items)) hotFoodTotalWithTip(total)
    else if (checkAnyFood(items)) anyFoodTotalWithTip(total)
    else total
  }

  // get loyalty discount
  def getLoyaltyDiscount(loyalty: Option[Int]): Double = {
    val stars = loyalty.getOrElse(0)
    val discount = 0.025
    if (stars < 3) 0
    else if (stars >= 8) 8 * discount
    else stars * discount
  }

  // convert currency
  def convertCurrency(total: Double, currency: Currency) = {
    currency match
      case EUR => total * 1.17
      case USD => total * 1.22
      case _ => total
  }

  // see if loyal, if yes -> get discount
  def calculateBill(customer: Customer): BigDecimal = {
    // discount to non premium items if loyalty: premium-items + (discount* non-premium-items)
    val totalBeforeTip =
      calculateListTotal(returnPremiumFromList(customer.receipt)) + (calculateListTotal(removePremiumFromList(customer.receipt)) * (1 - getLoyaltyDiscount(customer.loyalty)))
    totalWithTip(totalBeforeTip, customer.receipt)
  }


  // print out bill with formatting
  def printBill(customer: Customer, currency: Currency = GBP): String = {
    val symbol = currency match
      case EUR => "€"
      case USD => "$"
      case _ => "£"
    val discountString =
      customer match {
        case Customer(_, _, None) =>
          "No loyalty discount"
        case Customer(_, _, _) if customer.loyalty.get < 3 =>
          "No loyalty discount (3 star minimum)"
        case Customer(_, _, _) =>
          val discount = String.format("%,1.2f", convertCurrency((calculateListTotal(removePremiumFromList(customer.receipt)) * getLoyaltyDiscount(customer.loyalty)).doubleValue(), currency))
          s"${customer.loyalty.get} star loyalty discount applied ($symbol$discount)."
      }
    val tipString =
      if (checkPremium(customer.receipt)) "Premium service charge applied."
      else if (checkHotFood(customer.receipt)) "Hot food service charge applied."
      else if (checkAnyFood(customer.receipt)) "Food service charge applied."
      else "No service charge."

    val totalBill = String.format("%,1.2f", convertCurrency(calculateBill(customer).doubleValue(), currency))
    s"\n${customer.name}'s total bill: $symbol$totalBill \n - $discountString\n - $tipString\n - Sale completed: ${LocalTime.now().format(timeFormat)}"
  }

  // customers
  val james = Customer("James", drinksReceipt, Some(5))
  val lucy = Customer("Lucy", hotFoodReceipt, Some(2))
  val taylor = Customer("Taylor", premiumReceipt, None)
  val jake = Customer("Jake", bigReceipt, Some(20))

  println(printBill(james, USD))
  println(printBill(lucy, GBP))
  println(printBill(taylor))
  println(printBill(jake, EUR))
}
