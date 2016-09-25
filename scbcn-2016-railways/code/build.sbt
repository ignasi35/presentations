import sbt._


name := "scbn2016-railways"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.11.7"



scalaVersion  := "2.11.7"

name          := "scbn2016-railways"

javacOptions ++= Seq("-source", "1.8", "-target", "1.8")

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature", "-Xfatal-warnings", "-language:implicitConversions", "-target:jvm-1.7")


libraryDependencies ++= Dependencies.projectDependencies

