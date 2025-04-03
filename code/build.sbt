organization := "com.jbaysolutions"

name := "play-easymail"

scalaVersion := "2.13.3"
crossScalaVersions := Seq("2.12.6", "2.13.3")

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-mailer" % "8.0.1",
  "com.typesafe.play" %% "play-mailer-guice" % "8.0.1",
  guice
)

lazy val root = (project in file(".")).enablePlugins(PlayJava)

//releasePublishArtifactsAction := PgpKeys.publishSigned.value
