# scala-play-example

This application runs on Java 8 and is built with SBT.

## Run Application
### `SBT` (command line)
Just compile and run application with `sbt run` .

### Docker (command line) (Check installation at docker website)
Create the jar with `sbt assembly` command. 

Build docker image with `docker build -t "play-example:1.0" .` .

Run the application with `docker run -p 9000:9000 play-example:1.0` .



