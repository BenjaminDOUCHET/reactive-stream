package com.demo.routes

import com.demo.core.Item

object ItemWriter {
  def header() : Seq[String] = Seq(
    "brand",
    "sellerRef",
    "quantity",
    "price"
  )

  def writes(item:Item) : Seq[String] = {
    Seq(
      item.brand,
      item.sellerRef.toUpperCase,
      item.quantity.toString,
      item.unitPrice.toString
    )
  }
}
