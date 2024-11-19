package com.demo.core

import play.api.libs.json._
import reactivemongo.api.bson.{BSONDocumentHandler, Macros}

case class Item(
    brand : String,
    sellerRef: String,
    quantity : BigDecimal,
    unitPrice : BigDecimal) {
}

object Item{
  implicit val formatter: OFormat[Item] = Json.format[Item]
  implicit val bsonHandler: BSONDocumentHandler[Item] = Macros.handler[Item]
}