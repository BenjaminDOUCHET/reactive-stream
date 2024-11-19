import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class SpringReactor extends Simulation {

 val httpConf = http
    .baseURL("http://localhost:8585")
    .acceptHeader("text/csv")

  val scn = scenario("Spring Reactor Scenario")
    .exec(http("request_1")
    .get("/items?delay=1"))
    .pause(1)

   setUp(
    scn.inject(
      atOnceUsers(10),
      rampUsers(20000) over (30 seconds)
    ).protocols(httpConf)
  )
}

