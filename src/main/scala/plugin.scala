package com.github.hexx

import sbt._
import sbt.Keys._

import com.typesafe.sbt.SbtGit.GitKeys._
import com.typesafe.sbt.git.GitRunner

trait GithubRepoKeys {
  val localRepo = SettingKey[File]("local-repo")
  val githubRepo = SettingKey[String]("github-repo")
  val createRepo = TaskKey[Unit]("create-repo")
  val publishToGithubRepo = TaskKey[Unit]("publish-to-github-repo")
}

object GithubRepoPlugin extends Plugin with GithubRepoKeys {
  def createRepoTask(r: GitRunner, l: File, g: String, s: TaskStreams) {
    def git(args: String*) = r(args:_*)(l, s.log)
    if (l / ".git" exists) return
    git("init")
    l / "index.html" createNewFile()
    git("add", ".")
    git("commit", "-a", "-m", "First commit")
    git("checkout", "--orphan", "gh-pages")
    git("rm", "-rf", ".")
    IO.write(l / "index.html", "Maven Repository created by sbt-github-repo")
    git("add", ".")
    git("commit", "-a", "-m", "First commit")
    git("remote", "add", "origin", g)
    git("push", "origin", "gh-pages")
  }

  def publishToGithubRepoTask(r: GitRunner, l: File, s: TaskStreams) {
    def git(args: String*) = r(args:_*)(l, s.log)
    git("add", ".")
    git("commit", "-a", "-m", "publish by sbt-github-repo")
    git("push", "origin", "gh-pages")
  }

  def localPublishTo(v: String, l: File) =
    Some(Resolver.file("file", l / (if (v.trim.endsWith("SNAPSHOT")) "snapshots" else "releases")))

  lazy val githubRepoSettings = Seq(
    publishTo <<= (version, localRepo)(localPublishTo(_, _)),
    createRepo <<= (gitRunner, localRepo, githubRepo, streams) map (createRepoTask(_, _, _, _)),
    publishToGithubRepo <<=
      (createRepo, publish, gitRunner, localRepo, streams) map ((_, _, r, l, s) => publishToGithubRepoTask(r, l, s))
  )
}
