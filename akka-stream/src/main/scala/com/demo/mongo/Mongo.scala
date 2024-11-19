package com.demo.mongo

import reactivemongo.api.bson.collection.BSONCollection
import reactivemongo.api.{DB, MongoConnection}

import scala.concurrent.{ExecutionContext, Future}

final class Mongo(implicit ec: ExecutionContext) {

  private val driver = new reactivemongo.api.AsyncDriver
  private val parsedURIFuture = MongoConnection.fromStringWithDB("mongodb://mongodb:27017/test")
  private val connection: Future[MongoConnection] = parsedURIFuture.flatMap(driver.connect(_))
  private val databaseName: Future[String] = parsedURIFuture.map(_.db)
  private def DB()(implicit ec: ExecutionContext): Future[DB] = databaseName.flatMap(name => connection.flatMap(_.database(name)))

  def collection(collectionName: String)(implicit ec: ExecutionContext): Future[BSONCollection] =
    DB().map(_.collection(collectionName))

}