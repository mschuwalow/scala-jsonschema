import sbt._
import Keys._

object BuildHelper {

  val Scala211: String = "2.11.12"
  val Scala212: String = "2.12.16"
  val Scala213: String = "2.13.8"

  def stdSettings(prjName: String) = Seq(
    name := s"$prjName",
    crossScalaVersions := Seq(Scala213, Scala212, Scala211),
    scalaVersion := crossScalaVersions.value.head,
    scalacOptions := Options.scalacOptions(scalaVersion.value, isSnapshot.value),
    Test / parallelExecution := true
  )

}
