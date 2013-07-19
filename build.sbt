seq(githubRepoSettings: _*)

sbtPlugin := true

organization := "com.github.hexx"

name := "sbt-github-repo"

version := "0.1.0"

localRepo := Path.userHome / "github" / "maven"

githubRepo := "git@github.com:hexx/maven.git"

libraryDependencies <++= (scalaVersion, sbtBinaryVersion) { (scalaV, sbtV) => Seq(
  "com.typesafe.sbt" % "sbt-git" % "0.6.2" extra("scalaVersion" -> scalaV, "sbtVersion" -> sbtV)
)}

publishMavenStyle := false
