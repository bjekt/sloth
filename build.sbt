inThisBuild(Seq(
  organization := "com.github.cornerman",
  version      := "0.1.0-SNAPSHOT",

  scalaVersion := "2.12.4",
  crossScalaVersions := Seq("2.11.11", "2.12.4"),

  resolvers ++=
    ("jitpack" at "https://jitpack.io") ::
    Nil
))

lazy val commonSettings = Seq(
  scalacOptions ++=
    "-encoding" :: "UTF-8" ::
    "-unchecked" ::
    "-deprecation" ::
    "-explaintypes" ::
    "-feature" ::
    "-language:_" ::
    "-Xfuture" ::
    "-Xlint" ::
    "-Ypartial-unification" ::
    "-Yno-adapted-args" ::
    "-Ywarn-infer-any" ::
    "-Ywarn-value-discard" ::
    "-Ywarn-nullary-override" ::
    "-Ywarn-nullary-unit" ::
    Nil,

  scalacOptions ++= {
    CrossVersion.partialVersion(scalaVersion.value) match {
      case Some((2, 12)) =>
        "-Ywarn-extra-implicit" ::
        Nil
      case _ =>
        Nil
    }
  }
)

enablePlugins(ScalaJSPlugin)

lazy val root = (project in file("."))
  .settings(commonSettings)
  .aggregate(slothJS, slothJVM)
  .settings(
    publish := {},
    publishLocal := {}
  )

lazy val sloth = crossProject
  .settings(commonSettings)
  .settings(
    name := "sloth",
    libraryDependencies ++=
      Deps.scalaReflect.value % scalaVersion.value % Provided ::
      Deps.shapeless.value ::
      Deps.cats.value ::
      Deps.chameleon.value ::

      Deps.mycelium.value % Optional ::

      Deps.boopickle.value % Test ::
      Deps.kittens.value % Test ::
      Deps.scalaTest.value % Test ::
      Nil
  )

lazy val slothJS = sloth.js
lazy val slothJVM = sloth.jvm
