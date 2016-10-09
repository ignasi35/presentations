import sbt._

object Dependencies {

  val scalaTest = "2.2.6"

  val projectDependencies = {
    Seq(
      "com.h2database" % "h2" % "1.4.192"
      , "org.scalatest" %% "scalatest" % scalaTest
    )
  }

}


