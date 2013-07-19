# sbt-github-repo

creates your own Maven repository on Github.

## Installation

Add the following to `project/plugins.sbt`.

    resolvers ++= Seq(
      "jgit-repo" at "http://download.eclipse.org/jgit/maven",
      "hexx-releases" at "http://hexx.github.io/maven/releases"
    )

    addSbtPlugin("com.github.hexx" % "sbt-github-repo" % "0.1.0")

## Publishing

If you want to create a repository on https://github.com/hexx/repo and a local repository on `~/github/repo`,
add the following to `build.sbt`.

    seq(githubRepoSettings: _*)

    localRepo := Path.userHome / "github" / "repo

    githubRepo := "git@github.com:hexx/repo.git"

Run `sbt publish-to-github-repo` to publish your artifacts.

## Resolvers

Users of your artifacts have to add the following to `resolvers`.

    resolvers += "hexx-releases" at "http://hexx.github.io/repo/releases"
