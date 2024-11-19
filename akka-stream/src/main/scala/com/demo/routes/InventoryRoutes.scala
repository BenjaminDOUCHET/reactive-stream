package com.demo.routes

import akka.NotUsed
import akka.http.scaladsl.model.HttpMethods.{DELETE, GET, OPTIONS, POST, PUT}
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.Materializer
import akka.stream.alpakka.csv.scaladsl.CsvFormatting
import akka.stream.scaladsl.{Flow, Framing, Source}
import akka.util.ByteString
import ch.megard.akka.http.cors.scaladsl.CorsDirectives.cors
import ch.megard.akka.http.cors.scaladsl.model.{HttpHeaderRange, HttpOriginMatcher}
import ch.megard.akka.http.cors.scaladsl.settings.CorsSettings
import com.demo.core.{InventoryRepository, Item}

import scala.concurrent.duration.{Duration, FiniteDuration, MILLISECONDS}
import scala.concurrent.{ExecutionContext, Future}
import scala.util.Properties.lineSeparator

class InventoryRoutes(inventoryRepository: InventoryRepository)(implicit m: Materializer, ec: ExecutionContext) extends {

  private def completeAsCsv(itemSource: Source[Item, NotUsed]): Route = {

    val sourceCsv = Source
      .single(ItemWriter.header().toList)
      .concat(
        itemSource.map(
          ItemWriter.writes(_).toList
        )
      )
      .via(
        CsvFormatting.format[List[String]](delimiter = ';', endOfLine = lineSeparator)
      )
      .via(
        Framing.delimiter(
          ByteString(lineSeparator),
          maximumFrameLength = 200,
          allowTruncation = false
        )
      )
      .map(_.concat(ByteString(lineSeparator)))

    complete(HttpEntity(ContentTypes.`text/csv(UTF-8)`, sourceCsv))
  }

  private val allowedHeaders: HttpHeaderRange = HttpHeaderRange("Content-Type", "Authorization")
  private val corsSettings = CorsSettings.defaultSettings
    .withAllowedOrigins(HttpOriginMatcher.*)
    .withAllowedMethods(List(GET, POST, PUT, DELETE, OPTIONS))
    .withAllowedHeaders(allowedHeaders)
    .withAllowCredentials(false)

  val route: Route = {
    cors(corsSettings) {
      pathPrefix("items") {
        get {
          parameter("delay".as[Long].optional) { delayOpt =>
            val delayDuration = delayOpt.map(duration => FiniteDuration(duration, MILLISECONDS)).getOrElse(FiniteDuration(0, MILLISECONDS))
            val itemSourceWithDelay = inventoryRepository.getAll.via(simulateProcessingTime(delayDuration))
            completeAsCsv(itemSourceWithDelay)
          }
        } ~
          path("hello") {
            get {
              complete("hello world")
            }
          }
      }
    }
  }

  private def simulateProcessingTime[A](duration: FiniteDuration): Flow[A, A, NotUsed] = {
    if (duration > Duration.Zero) {
      Flow[A].throttle(1, duration)
    } else {
      Flow[A]
    }
  }

}
