
ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.11"

lazy val akkaHttpVersion = "10.5.2"
lazy val akkaVersion    = "2.8.3"

fork := true
lazy val root = (project in file("."))
  .settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.13.4"
    )),
    name := "akka-stream",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
      "com.typesafe.akka" %% "akka-stream" % akkaVersion,
      "de.heikoseeberger" %% "akka-http-play-json" % "1.34.0",
      "ch.qos.logback" % "logback-classic" % "1.4.7",

      "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion % Test,
      "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion % Test,
      "org.scalatest" %% "scalatest" % "3.2.15" % Test,
      "org.reactivemongo" %% "reactivemongo-akkastream" % "1.0.10",
      "org.reactivemongo" %% "reactivemongo" % "1.0.10",
      "org.reactivemongo" %% "reactivemongo-play-json-compat" % "1.0.10".concat("-play29"),

      "com.lightbend.akka" %% "akka-stream-alpakka-csv" % "2.0.1",
      "ch.megard" %% "akka-http-cors" % "1.2.0",
      "io.gatling.highcharts" % "gatling-charts-highcharts" % "3.6.1",
      "io.gatling" % "gatling-test-framework" % "3.6.1" % "test"
    )

)

