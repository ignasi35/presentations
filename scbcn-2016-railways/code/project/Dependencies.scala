import sbt._

object Dependencies {

  val scalaTest = "2.2.6"

  val projectDependencies = {
    Seq(
       "org.scalatest" %% "scalatest" % scalaTest
    )
  }

}


