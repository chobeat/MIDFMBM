import sbt._
import Keys._

object Settings {
  import LibgdxBuild.libgdxVersion


  lazy val desktopJarName =
    SettingKey[String]("desktop-jar-name", "name of JAR file for desktop")

  lazy val core = plugins.JvmPlugin.projectSettings ++ Seq(
      version := (version in LocalProject("all-platforms")).value,
      libgdxVersion := (libgdxVersion in LocalProject("all-platforms")).value,
      scalaVersion := (scalaVersion in LocalProject("all-platforms")).value,
      libraryDependencies ++= Seq(
        "com.badlogicgames.gdx" % "gdx" % libgdxVersion.value,
        "com.badlogicgames.gdx" % "gdx-box2d" % libgdxVersion.value,
        "com.github.xaguzman" % "pathfinding" % "0.2.6",
        "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.5"
      ),
      javacOptions ++= Seq(
        "-Xlint",
        "-encoding",
        "UTF-8",
        "-source",
        "1.7",
        "-target",
        "1.7"
      ),
      scalacOptions ++= Seq(
        "-Xlint",
        "-Ywarn-dead-code",
        "-Ywarn-value-discard",
        "-Ywarn-numeric-widen",
        "-unchecked",
        "-deprecation",
        "-feature",
        "-encoding",
        "UTF-8",
        "-target:jvm-1.7"
      ),
      cancelable := true,
      exportJars := true
    )

  lazy val desktop = core ++ Seq(
      libraryDependencies ++= Seq(
        "com.badlogicgames.gdx" % "gdx-backend-lwjgl" % libgdxVersion.value,
        "com.badlogicgames.gdx" % "gdx-platform" % libgdxVersion.value classifier "natives-desktop",
        "com.badlogicgames.gdx" % "gdx-box2d" % libgdxVersion.value
      ),
      fork in Compile := true,
      unmanagedResourceDirectories in Compile += file("android/assets"),
      desktopJarName := "midfmbm"

    )
}


object LibgdxBuild extends Build {
  lazy val libgdxVersion = settingKey[String]("version of Libgdx library")

  lazy val core = Project(
    id = "core",
    base = file("core"),
    settings = Settings.core
  )

  lazy val desktop = Project(
    id = "desktop",
    base = file("desktop"),
    settings = Settings.desktop
  ).dependsOn(core)

  lazy val all = Project(
    id = "all-platforms",
    base = file("."),
    settings = Settings.core
  ).aggregate(core, desktop)
}
