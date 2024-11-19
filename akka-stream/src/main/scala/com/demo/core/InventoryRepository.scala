package com.demo.core

import akka.NotUsed
import akka.stream.Materializer
import akka.stream.scaladsl.Source

trait InventoryRepository {
  def getAll(implicit m:Materializer) : Source[Item,NotUsed]
}
