FROM openjdk:8u201-jre-alpine3.9
RUN mkdir /app
COPY ./target/scala-2.13/play-example-*.jar /app/
WORKDIR /app
ENTRYPOINT java -jar play-example-*.jar