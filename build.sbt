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

// https://mvnrepository.com/artifact/dev.zio/zio
//libraryDependencies += "dev.zio" %% "zio" % "1.0.0-RC21-2"

// https://mvnrepository.com/artifact/org.mockito/mockito-core
//libraryDependencies += "org.mockito" % "mockito-core" % "3.3.3" % Test