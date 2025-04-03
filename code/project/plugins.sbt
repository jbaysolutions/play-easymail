// Comment to get more information during initialization
logLevel := Level.Warn

// The Typesafe repository
resolvers += "Typesafe Snapshots Repository" at "https://repo.typesafe.com/typesafe/snapshots/"

resolvers += "Typesafe repository" at "https://repo.typesafe.com/typesafe/releases/"

// Use the Play sbt plugin for Play projects
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % Option(System.getProperty("play.version")).getOrElse("2.8.22"))

//addSbtPlugin("com.github.sbt" % "sbt-pgp" % "1.1.2-1")

addSbtPlugin("com.github.gseitz" % "sbt-release" % "1.0.13")
