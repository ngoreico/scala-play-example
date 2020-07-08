name := "playExample"
 
version := "1.0" 
      
lazy val `playexample` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
      
resolvers += "Akka Snapshot Repository" at "https://repo.akka.io/snapshots/"
      
scalaVersion := "2.13.0"

libraryDependencies ++= Seq( jdbc , ehcache , ws , specs2 % Test , guice )
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.3" % Test


unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  

      