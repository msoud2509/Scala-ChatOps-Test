val scala3Version = "3.6.4"

lazy val root = project
  .in(file("."))
  .settings(
    name := "scala_experiment",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies ++= Seq(
      "org.scalameta" %% "munit" % "1.0.0" % Test,
      "com.softwaremill.sttp.client3" %% "core" % "3.9.0",
      "com.softwaremill.sttp.client3" %% "circe" % "3.9.0",
      "io.circe" %% "circe-generic" % "0.14.5",
      "org.typelevel" %% "cats-effect" % "3.5.2",
      )
  )
