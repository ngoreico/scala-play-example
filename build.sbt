name := "playExample"
 
version := "1.0" 
      
lazy val `playexample` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
      
resolvers += "Akka Snapshot Repository" at "https://repo.akka.io/snapshots/"
      
scalaVersion := "2.13.3"

libraryDependencies ++= Seq( jdbc , ehcache , ws , specs2 % Test , guice )
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.3" % Test

// https://mvnrepository.com/artifact/com.beachape/enumeratum
// https://github.com/lloydmeta/enumeratum
libraryDependencies += "com.beachape" %% "enumeratum" % "1.6.1"

// https://mvnrepository.com/artifact/org.mockito/mockito-core
//libraryDependencies += "org.mockito" % "mockito-core" % "3.3.3" % Test

//Since module-info.class files are only useful for Java 9+ modules, you can safely discard them:
//https://stackoverflow.com/questions/60114651/play-json-library-and-sbt-assembly-merge-error/60114988#60114988
assemblyMergeStrategy in assembly := {
  case "module-info.class" => MergeStrategy.discard
  case PathList("play", "reference-overrides.conf") => MergeStrategy.concat
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}

assemblyJarName := s"play-example-${version.value}.jar"