scalaVersion := "2.13.12"

name := "s3-file-uploading-sandbox"
organization := "ch.epfl.scala"
version := "1.0"

libraryDependencies ++= Seq(
  "org.scala-lang.modules" %% "scala-parser-combinators" % "2.3.0",
  "software.amazon.awssdk" % "s3" % "2.31.0"
)
