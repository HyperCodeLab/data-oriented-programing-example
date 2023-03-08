ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.12"

lazy val root = (project in file("."))
  .settings(
    name := "DataOriendedProgramming",
    idePackagePrefix := Some("wiki.dataengineering")
  )

libraryDependencies ++= Seq(
  "org.scalactic" %% "scalactic" % "3.2.15",
  "org.scalatest" %% "scalatest" % "3.2.15" % "test",
  "org.json4s" %% "json4s-core" % "4.1.0-M2",
  "org.json4s" %% "json4s-jackson" % "4.1.0-M2",
  "org.json4s" %% "json4s-native" % "4.1.0-M2"
)