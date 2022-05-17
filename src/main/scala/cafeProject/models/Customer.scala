package cafeProject.models

case class Customer(name: String, receipt: List[MenuItem], loyalty: Option[Int])
