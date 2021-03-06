package cafeProject.main

import cafeProject.models.{Cold, Drink, Food, Hot, Premium}
import cafeProject.models.Customer
import cafeProject.models.Currency.{EUR, GBP, USD}
import cafeProject.models.MenuItem

import java.time.LocalTime
import java.time.format.DateTimeFormatter

object Main extends App {

  val timeFormat = DateTimeFormatter.ofPattern("h.mma")

  // menu items
  val coke = MenuItem(0.5, Cold, Drink)
  val coffee = MenuItem(1.0, Hot, Drink)
  val cheeseSandwich = MenuItem(2.0, Cold, Food)
  val steakSandwich = MenuItem(4.5, Hot, Food)
  val lobster = MenuItem(25, Hot, Premium)

  // varying lists of items
  val premiumReceipt = List(coke, coffee, cheeseSandwich, steakSandwich, lobster)
  val hotFoodReceipt = List(coke, coffee, cheeseSandwich, steakSandwich)
  val coldFoodReceipt = List(coke, coffee, cheeseSandwich)
  val drinksReceipt = List(coke, coffee, coke, coffee, coke, coffee, coke, coffee)
  val bigReceipt = List(coke, coffee, cheeseSandwich, steakSandwich, steakSandwich, steakSandwich, steakSandwich, steakSandwich, steakSandwich,
    steakSandwich, steakSandwich, steakSandwich, steakSandwich, lobster, lobster, lobster, lobster, lobster)

  def checkForAnyFood(items: List[MenuItem]): Boolean = items.exists(_.itemType == Food)
  def checkForHotFood(items: List[MenuItem]): Boolean = items.exists(item => item.temp == Hot && item.itemType == Food)
  def checkForPremiumFood(items: List[MenuItem]): Boolean = items.exists(_.itemType == Premium)

  def anyFoodTotalWithTip(total: BigDecimal): BigDecimal = 1.1 * total
  def hotFoodTotalWithTip(total: BigDecimal): BigDecimal = {
    val tip = total * 0.2
    if (tip >= 20) 20 + total else tip + total
  }
  def premiumFoodTotalWithTip(total: BigDecimal): BigDecimal = {
    val tip = total * 0.25
    if (tip >= 40) 40 + total else tip + total
  }

  def removePremiumItemsFromList(items: List[MenuItem]): List[MenuItem] = items.filter(_.itemType != Premium)
  def returnPremiumItemsFromList(items: List[MenuItem]): List[MenuItem] = items.filter(_.itemType == Premium)

  def checkIfIsHappyHour(hourNow: Option[Int]): Boolean =
    hourNow match {
      case Some(value) => value >= 18 && value < 20 // 6-8pm
      case None =>
        val currentHour = LocalTime.now().getHour
        currentHour >= 18 && currentHour < 20
    }
  def getHappyHourTotal(items: List[MenuItem]): BigDecimal = {
    items.filter(_.itemType != Drink).map(_.cost).sum + (0.5 * items.filter(_.itemType == Drink).map(_.cost).sum)
  }
  def calculateListTotal(items: List[MenuItem], overrideHour: Option[Int] = None): BigDecimal = {
    // if is happy hour, drinks are half price
    if (checkIfIsHappyHour(overrideHour)) getHappyHourTotal(items) else items.map(_.cost).sum
  }

  def addRelevantTipToTotal(total: BigDecimal, items: List[MenuItem]): BigDecimal = {
    if (checkForPremiumFood(items)) premiumFoodTotalWithTip(total)
    else if (checkForHotFood(items)) hotFoodTotalWithTip(total)
    else if (checkForAnyFood(items)) anyFoodTotalWithTip(total)
    else total
  }

  def getLoyaltyDiscount(loyalty: Option[Int]): Double = {
    val stars = loyalty.getOrElse(0)
    val discount = 0.025
    if (stars < 3) 0 // 3 star minimum
    else if (stars >= 8) 8 * discount // 8 star maximum
    else stars * discount
  }

  def convertToCurrency(total: Double, currency: cafeProject.models.Currency.Value): Double = {
    currency match {
      case EUR => total * 1.17
      case USD => total * 1.22
      case _ => total
    }
  }

  def calculateBillWithLoyaltyAndTip(customer: Customer): BigDecimal = {
    // get totals with discount to non premium items
    val premiumItems = calculateListTotal(returnPremiumItemsFromList(customer.receipt))
    val discountedNonPremiumItems = calculateListTotal(removePremiumItemsFromList(customer.receipt)) * (1 - getLoyaltyDiscount(customer.loyalty))
    // add tip to re-combined total
    addRelevantTipToTotal(premiumItems + discountedNonPremiumItems, customer.receipt)
  }

  def printBill(customer: Customer, currency: cafeProject.models.Currency.Value = GBP): String = {
    // get relevant symbol, pound is default
    val symbol = currency match {
      case EUR => "???"
      case USD => "$"
      case _ => "??"
    }

    val discountString =
      customer match {
        case Customer(_, _, None) =>
          "No loyalty discount"
        case Customer(_, _, _) if customer.loyalty.get < 3 =>
          "No loyalty discount (3 star minimum)"
        case Customer(_, _, _) =>
          val discount = String.format("%,1.2f", convertToCurrency((calculateListTotal(removePremiumItemsFromList(customer.receipt)) * getLoyaltyDiscount(customer.loyalty)).doubleValue, currency))
          s"${customer.loyalty.get} star loyalty discount applied ($symbol$discount)."
      }

    val tipString =
      if (checkForPremiumFood(customer.receipt)) "Premium service charge applied."
      else if (checkForHotFood(customer.receipt)) "Hot food service charge applied."
      else if (checkForAnyFood(customer.receipt)) "Food service charge applied."
      else "No service charge."

    val totalBillString = String.format("%,1.2f", convertToCurrency(calculateBillWithLoyaltyAndTip(customer).doubleValue, currency))

    // Format the different bill elements with transaction time
    s"\n${customer.name}'s total bill: $symbol$totalBillString \n - $discountString\n - $tipString\n - Sale completed: ${LocalTime.now().format(timeFormat)}"
  }

  // example customers
  val james = Customer("James", drinksReceipt, Some(5))
  val lucy = Customer("Lucy", hotFoodReceipt, Some(2))
  val taylor = Customer("Taylor", premiumReceipt, None)
  val jake = Customer("Jake", bigReceipt, Some(20))

  // example outputs
  println(printBill(james, USD))
  println(printBill(lucy, GBP))
  println(printBill(taylor))
  println(printBill(jake, EUR))
}
