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
        .get(uri"https://httpbin.org/get")
        .response(asJson[PersonIdentity])

      new Service {
        override def getPersonExternalInfo(id: String): RIO[Logging with SttpClient, PersonIdentity] =
          //SttpClient.send(request)
        //TODO
        ???
      }
    }

    def getPersonExternalInfo(id: String): RIO[PeopleClient with Logging with SttpClient, PersonIdentity] =
      ZIO.accessM(_.get.getPersonExternalInfo(id))
  }
}
