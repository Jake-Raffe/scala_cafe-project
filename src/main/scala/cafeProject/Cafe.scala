//package pop
//
//import cafeProject.*
//
//object Cafe extends App {
//
//
//  // customer class
////  case class Customer(name: String, receipt: List[MenuItem], loyalty: Option[Int])
//
//  // menu items
//  val coke = MenuItem(0.5, Cold, Drink, false)
//  val coffee = MenuItem(1.0, Hot, Drink, false)
//  val cheeseSandwich = MenuItem(2.0, Cold, Food, false)
//  val steakSandwich = MenuItem(4.5, Hot, Food, false)
//  val lobster = MenuItem(25, Hot, Food, true)
//
//  // check if list contains any hot food items
//  val checkHotFood: List[MenuItem] => Boolean = (items: List[MenuItem]) => {
//    items.exists(item => item.temp == Hot && item.itemType == Food)
//  }
//  // check if list contains any food items
//  val checkAnyFood: List[MenuItem] => Boolean = (items: List[MenuItem]) => {
//    items.exists(_.itemType == Food)
//  }
//  // check if list contains premium items
//  val checkPremium: List[MenuItem] => Boolean = (items: List[MenuItem]) => {
//    items.exists(_.isPremium)
//  }
//
//
//  // calculate total
//  val calculateTotal: List[MenuItem] => BigDecimal = (items: List[MenuItem]) => {
//    if (items.isEmpty) 0 else items.head.cost + calculateTotal(items.tail)
//  }
//  // calculate total +  hot food tip
//  val hotFoodTotalWithTip: BigDecimal => BigDecimal = (total: BigDecimal) => {
//    val tip = total * 0.2
//    if (tip >= 20) 20 + total else tip + total
//  }
//  // calculate total + any food tip
//  val anyFoodTotalWithTip: BigDecimal => BigDecimal = (total: BigDecimal) => {
//    1.1 * total
//  }
//  // calculate total + premium tip
//  val premiumTotalWithTip: BigDecimal => BigDecimal = (total: BigDecimal) => {
//    val tip = total * 0.25
//    if (tip >= 40) 40 + total else tip + total
//  }
//
//  // return list without premium items
//  val removePremiumFromList: List[MenuItem] => List[MenuItem] = (items: List[MenuItem]) => {
//    items.filter(!_.isPremium)
//  }
//  // return only premium items
//  val returnPremiumFromList: List[MenuItem] => List[MenuItem] = (items: List[MenuItem]) => {
//    items.filter(_.isPremium)
//  }
//
//
//  // calculate total bill, plus tip depending on items in list
//  val totalWithTip: (BigDecimal, List[MenuItem]) => BigDecimal = (total: BigDecimal, items: List[MenuItem]) => {
//    if (checkPremium(items)) premiumTotalWithTip(total)
//    else if (checkHotFood(items)) hotFoodTotalWithTip(total)
//    else if (checkAnyFood(items)) anyFoodTotalWithTip(total)
//    else total
//  }
//
//  // get loyalty discount
//  val getLoyaltyDiscount: Option[Int] => Double = (loyalty: Option[Int]) => {
//    val stars = loyalty.getOrElse(0)
//    val discount = 0.025
//    if (stars < 3) 0
//    else if (stars >= 8) 8 * discount
//    else stars * discount
//  }
//
//  // see if loyal, yes -> get discount
//  val calculateBill: Customer => BigDecimal = (customer: Customer) => {
//    // discount to non premium items if loyalty: premium-items + (discount* non-premium-items)
//    val totalBeforeTip = calculateTotal(returnPremiumFromList(customer.receipt)) + (calculateTotal(removePremiumFromList(customer.receipt)) * (1 - getLoyaltyDiscount(customer.loyalty)))
//    totalWithTip(totalBeforeTip, customer.receipt)
//  }
//
//  // print out bill with formatting
//  val printBill: Customer => String = (customer: Customer) => {
//    val discountString =
//      customer match {
//        case Customer(_, _, None) => "No loyalty discount"
//        case Customer(_, _, _) => f"${customer.loyalty.get} star loyalty discount applied (£${calculateTotal(removePremiumFromList(customer.receipt)) * (getLoyaltyDiscount(customer.loyalty))}%1.2f)"
//      }
//    val tipString =
//      if (checkPremium(customer.receipt)) "Premium service charge applied"
//      else if (checkHotFood(customer.receipt)) "Hot food service charge applied"
//      else if (checkAnyFood(customer.receipt)) "Food service charge applied"
//      else "No service charge"
//    f"${customer.name}'s total bill: £${calculateBill(customer)}%1.2f\n - $discountString.\n - $tipString.\n"
//  }
//
//  // varying lists  of items
//  val premiumReceipt = List(coke, coffee, cheeseSandwich, steakSandwich, lobster)
//  val hotFoodReceipt = List(coke, coffee, cheeseSandwich, steakSandwich)
//  val coldFoodReceipt = List(coke, coffee, cheeseSandwich)
//  val drinksReceipt = List(coke, coffee, coke, coffee, coke, coffee, coke, coffee)
//  val bigReceipt = List(coke, coffee, cheeseSandwich, steakSandwich, steakSandwich, steakSandwich, steakSandwich, steakSandwich, steakSandwich,
//    steakSandwich, steakSandwich, steakSandwich, steakSandwich, lobster, lobster, lobster, lobster, lobster)
//
//
//  // customers
//  val james = Customer("James", drinksReceipt, Some(5))
//  val lucy = Customer("Lucy", hotFoodReceipt, Some(2))
//  val robocop = Customer("Taylor", premiumReceipt, None)
//  val fatHead = Customer("Jake", bigReceipt, Some(20))
//
//  println(printBill(james))
//  println(printBill(lucy))
//  println(printBill(robocop))
//  println(printBill(fatHead))
//}
