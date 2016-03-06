name := "spark-titan-connector"

version := "0.1"

scalaVersion := "2.11.7"

scalacOptions += "-feature"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "1.6.0" % "provided",
  "com.thinkaurelius.titan" % "titan-core" % "1.0.0" % "provided",
  "com.thinkaurelius.titan" % "titan-berkeleyje" % "1.0.0" % Test,
  "com.thinkaurelius.titan" % "titan-es" % "1.0.0" % Test,
  "org.scalatest" %% "scalatest" % "2.2.6" % Test
)