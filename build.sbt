lazy val root = (project in file(".")).
  settings(
    name := "pew-sbang",
    version := "0.1",
    scalaVersion := "2.11.7",
    scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature")
  )

libraryDependencies += "scala-game" %  "scala-game-library-core_2.11" % "0.0.2" changing()

exportJars := true
