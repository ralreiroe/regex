import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.12.1",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "client",
    libraryDependencies += scalaTest % Test,
    libraryDependencies += "com.jcraft" % "jsch" % "0.1.54",
    libraryDependencies += "org.apache.commons" % "commons-vfs2" % "2.1"
  )
