import BuildHelper.stdSettings

addCommandAlias("build", "prepare; test")
addCommandAlias("prepare", "fix; fmt")
addCommandAlias("check", "fixCheck; fmtCheck")
addCommandAlias("fix", "all compile:scalafix test:scalafix")
addCommandAlias(
  "fixCheck",
  "compile:scalafix --check; test:scalafix --check"
)
addCommandAlias("fmt", "all scalafmtSbt scalafmt test:scalafmt")
addCommandAlias(
  "fmtCheck",
  "all scalafmtSbtCheck scalafmtCheck test:scalafmtCheck"
)

inThisBuild(
  List(
    organization := "com.schuwalow",
    homepage := Some(url("https://github.com/mschuwalow/scala-jsonforms")),
    licenses := List("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
    developers := List(
      Developer(
        "mschuwalow",
        "Maxim Schuwalow",
        "mschuwalow@uos.de",
        url("https://github.com/mschuwalow")
      )
    ),
    semanticdbEnabled := true,
    semanticdbVersion := scalafixSemanticdb.revision,
    scalaVersion := "2.13.8",
    scalafixDependencies ++= Dependencies.ScalaFix
  )
)

lazy val root = project
  .in(file("."))
  .enablePlugins(ScalaJSPlugin)
  .aggregate(rjson.jvm, rjson.js, schema.jvm, schema.js)
  .settings(
    publish / skip := true
  )

lazy val rjson = crossProject(JSPlatform, JVMPlatform)
  .in(file("modules/rjson"))
  .settings(stdSettings("json-reference"))
  .settings(
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework"),
    libraryDependencies ++= Dependencies.RJson
  )

lazy val schema = crossProject(JSPlatform, JVMPlatform)
  .in(file("modules/schema"))
  .dependsOn(rjson)
  .settings(stdSettings("jsonschema"))
  .settings(
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework"),
    libraryDependencies ++= Dependencies.Schema
  )
