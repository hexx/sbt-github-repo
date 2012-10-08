resolvers ++= Seq(
  "jgit-repo" at "http://download.eclipse.org/jgit/maven",
  "Hexx releases" at "http://hexx.github.com/maven/releases"
)

addSbtPlugin("com.typesafe.sbt" % "sbt-git" % "0.5.0")

addSbtPlugin("com.github.hexx" % "sbt-github-repo" % "0.0.1")
