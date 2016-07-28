name := "serach"

version := "1.0"

scalaVersion := "2.11.8"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature", "-Xfatal-warnings", "-Xexperimental")

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)

libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-reflect" % scalaVersion.value,
  "org.scala-lang" % "scala-compiler" % scalaVersion.value,
  "org.scalafx" %% "scalafx" % "8.0.92-R10",
  "org.scalafx" %% "scalafxml-core-sfx8" % "0.2.2",
  "com.sksamuel.elastic4s" %% "elastic4s-core" % "2.3.1",
  "com.typesafe.play" %% "play-json" % "2.5.4"
)

jfxSettings

JFX.mainClass := Some("leviysoft.serach.Serach")

JFX.nativeBundles := "image"

JFX.antLib := Some("C:/Program Files/Java/jdk1.8.0/lib/ant-javafx.jar")

