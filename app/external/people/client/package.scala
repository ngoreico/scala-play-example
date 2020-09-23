package external.people

import java.time.{Duration, Instant}

import external.people.model.PersonIdentity
import sttp.client.asynchttpclient.zio.SttpClient
import sttp.client.basicRequest
import sttp.client.circe.asJson
import zio.{Has, Layer, RIO, ULayer, ZIO, ZLayer}

import scala.concurrent.ExecutionContext
import sttp.client._
import sttp.client.circe._
import sttp.client.asynchttpclient.zio._
import io.circe.generic.auto._
import zio.logging.Logging

package object peopleClient {

  type PeopleClient = Has[PeopleClient.Service]
  object PeopleClient {

    trait Service {
      def getPersonExternalInfo(id: String): RIO[Logging with SttpClient, PersonIdentity]
    }


    val mockedClient: ULayer[Has[PeopleClient.Service]] = ZLayer.succeed {
      new Service {
        override def getPersonExternalInfo(id: String): RIO[Logging with SttpClient, PersonIdentity] =
          ZIO.succeed(PersonIdentity(id, "nameStub", "lastnameStub", Instant.now().minus(Duration.ofDays(300000))))
      }
    }

    val live: ZLayer[Logging, Throwable, Has[PeopleClient.Service]] = ZLayer.succeed {
      val request = basicRequest
        .get(uri"http://mockbin.org/bin/9f53a076-7182-4357-a088-2395583feb4b")
        .response(asJson[PersonIdentity])

      new Service {
        override def getPersonExternalInfo(id: String): RIO[Logging with SttpClient, PersonIdentity] = for {
          response <- SttpClient.send(request)
          personIdentity <- ZIO.fromEither(response.body)
        } yield personIdentity
      }
    }

    def getPersonExternalInfo(id: String): RIO[PeopleClient with Logging with SttpClient, PersonIdentity] =
      ZIO.accessM(_.get.getPersonExternalInfo(id))
  }
}
