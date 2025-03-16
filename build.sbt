scalaVersion := "2.13.12"

name := "s3-file-uploading-sandbox"
organization := "ch.epfl.scala"
version := "1.0"

libraryDependencies ++= Seq(
  "org.scala-lang.modules" %% "scala-parser-combinators" % "2.3.0",
  "software.amazon.awssdk" % "s3" % "2.31.0",
  "ch.qos.logback" % "logback-classic" % "1.5.17",
  "org.scalactic" %% "scalactic" % "3.2.19",
  "org.scalatest" %% "scalatest" % "3.2.19" % Test,
  "org.scalatestplus" %% "mockito-5-12" % "3.2.19.0" % Test
)
