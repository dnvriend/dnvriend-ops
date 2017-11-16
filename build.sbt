name := "dnvriend-ops"

organization := "com.github.dnvriend"

version := "1.0.0-SNAPSHOT"

scalaVersion := "2.12.4"

libraryDependencies += "org.scalaz" %% "scalaz-core" % "7.2.16"
libraryDependencies += "com.chuusai" %% "shapeless" % "2.3.2"
libraryDependencies += "com.typesafe.play" %% "play-json" % "2.6.6"
libraryDependencies += "com.sksamuel.avro4s" %% "avro4s-core" % "1.8.0"
libraryDependencies += "org.scala-sbt" %% "io" % "1.0.2"
libraryDependencies += "org.scalaj" %% "scalaj-http" % "2.3.0"
libraryDependencies += "org.typelevel" %% "scalaz-scalatest" % "1.1.2" % Test
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.3" % Test

// testing configuration
fork in Test := true
parallelExecution := false

scalacOptions ++= Seq(
  "-unchecked",
  "-deprecation",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-feature",
  "-Xfatal-warnings",
  "-target:jvm-1.8",
)

// enable scala code formatting //
import scalariform.formatter.preferences._
import com.typesafe.sbt.SbtScalariform

// Scalariform settings
SbtScalariform.autoImport.scalariformPreferences := SbtScalariform.autoImport.scalariformPreferences.value
   .setPreference(AlignSingleLineCaseStatements, true)
   .setPreference(AlignSingleLineCaseStatements.MaxArrowIndent, 100)
   .setPreference(DoubleIndentConstructorArguments, true)
   .setPreference(DanglingCloseParenthesis, Preserve)