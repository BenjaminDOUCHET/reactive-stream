package com.demo

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.stream.Materializer
import com.demo.mongo.{InventoryCollection, Mongo}
import com.demo.routes.InventoryRoutes

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

//#main-class
object QuickstartApp {

  private def startHttpServer(routes: Route)(implicit system: ActorSystem[_]): Unit = {

    import system.executionContext
    val serverHost = sys.env.getOrElse("SERVER_HOST", "0.0.0.0")
    val serverPort = sys.env.getOrElse("SERVER_PORT", "9000").toInt

    val futureBinding = Http().newServerAt(serverHost, serverPort).bind(routes)
    futureBinding.onComplete {
      case Success(binding) =>
        val address = binding.localAddress
        system.log.info("Server online at http://{}:{}/items", address.getHostString, address.getPort)
      case Failure(ex) =>
        system.log.error("Failed to bind HTTP endpoint, terminating system", ex)
        system.terminate()
    }
  }

  def main(args: Array[String]): Unit = {

    val rootBehavior = Behaviors.setup[Nothing] { context =>
      implicit val system: ActorSystem[Nothing] = context.system
      implicit val materializer: Materializer = Materializer(system)

      val routes = new InventoryRoutes(new InventoryCollection(new Mongo))
      startHttpServer(routes.route)(context.system)

      Behaviors.empty
    }
    val system = ActorSystem[Nothing](rootBehavior, "HelloAkkaHttpServer")

  }
}
