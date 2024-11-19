package com.demo.mongo

import akka.NotUsed
import akka.stream.Materializer
import akka.stream.scaladsl.Source
import com.demo.core.{InventoryRepository, Item}
import reactivemongo.akkastream.cursorProducer
import reactivemongo.api.bson.BSONDocument
import reactivemongo.api.bson.collection.BSONCollection

import scala.concurrent.{ExecutionContext, Future}

class InventoryCollection (mongo: Mongo)(implicit val ec : ExecutionContext,m:Materializer) extends InventoryRepository{
  val collectionName = "inventory"
  private def collection()(implicit ec:ExecutionContext) :Future[BSONCollection] = mongo.collection(collectionName)

  def getAll(implicit m:Materializer): Source[Item, NotUsed] = {
    Source
      .future(collection())
      .flatMapConcat(collection =>
        collection
          .find(BSONDocument.empty)
          .cursor[Item]()
          .documentSource()
      )
  }
}
