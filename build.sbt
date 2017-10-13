import Dependencies._
import sbt.Keys.libraryDependencies

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.12.1",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "regex",
    libraryDependencies += scalaTest % Test,
    libraryDependencies += "com.jcraft" % "jsch" % "0.1.54",
    libraryDependencies += "org.apache.commons" % "commons-vfs2" % "2.1",

    libraryDependencies += "com.typesafe.play" %% "play-ahc-ws-standalone" % "1.1.2"

)
