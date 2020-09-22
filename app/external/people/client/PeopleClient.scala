//package external.people.client

import java.time.Instant

import external.people.model.PersonIdentity
import io.circe
import play.api.Logging
import zio.{Has, IO, Layer, RIO, UIO, ZIO, ZLayer}
import sttp.client._
import sttp.client.circe._
import sttp.client.asynchttpclient.zio._
import io.circe.generic.auto._
import zio._
import zio.console.Console

import scala.concurrent.ExecutionContext

/*package external.people.client

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import external.people.model.PersonIdentity
import play.api.libs.ws.ahc.AhcWSClient
import play.libs.ws.WSClient

import scala.concurrent.{ExecutionContext, Future}
import zio._
import zio.logging.Logging

object PeopleClient {
  trait Service {
    def getPerson(id: String)(implicit ec: ExecutionContext): RIO[Logging with WSClient, PersonIdentity]
  }
}
trait PeopleClient {
  def client: PeopleClient.Service
}

object peopleClient {
  def getPerson(id: String)(implicit ec: ExecutionContext): RIO[Logging with WSClient with PeopleClient, PersonIdentity] =
    ZIO.accessM(_.client.getPerson(id))
}

trait PeopleLiveClient extends PeopleClient {
  override def client: PeopleClient.Service =
    new PeopleClient.Service {
      private val protocol: String = "http"
      private val host: String = "localhost:8080"
      private val path: String = "persons"

      implicit val system       = ActorSystem()
      implicit val materializer = ActorMaterializer()
      val ws = AhcWSClient()

      override def getPerson(id: String)(implicit ec: ExecutionContext): RIO[Logging with PeopleClient, PersonIdentity] = {
        wsC
        ZIO.fromFuture(_ => _.)
      }
    }
}
object PeopleLiveClient extends PeopleLiveClient*/
//type PeopleClient = Has[PeopleClient.Service]
  /*val live: URLayer[WSClient, Has[Service]] = ZLayer.succeed {
    new Service {
      private val protocol: String = "http"
      private val host: String = "localhost:8080"
      private val path: String = "persons"

      override def getPerson(id: String)(implicit ec: ExecutionContext): RIO[Logging, PersonIdentity] = {
        ZIO.accessM[WSClient](_.url(s"$protocol://$host/$path/$id").get())
        //ZIO.fromFuture(_ =>)
      }
    }
  }*/
/*
trait RestClientProvider {
  type PeopleClient

  def restClientProvider: RestClientProvider.Service

}

object RestClientProvider {
  trait Service {
    def client: UIO[WSClient]
  }
}

class LiveWSClientProvider(wsClient: WSClient) extends RestClientProvider {
  override def restClientProvider: RestClientProvider.Service = new RestClientProvider.Service {
    override def client: UIO[WSClient] = ZIO.effectTotal(wsClient)
  }
}*/

/*
object AppLogger {
  import play.api.Logger

  trait Service {
    def info(message: => String)(implicit mc: MarkerContext): UIO[Unit]
    def debug(message: => String)(implicit mc: MarkerContext): UIO[Unit]
  }

  def info(message: => String)(implicit mc: MarkerContext): URIO[Has[AppLogger.Service], Unit] =
    ZIO.accessM(_.get.info(message))

  def debug(message: => String)(implicit mc: MarkerContext): URIO[Has[AppLogger.Service], Unit] =
    ZIO.accessM(_.get.debug(message))

  val live: ZLayer[Any, Nothing, Has[AppLogger.Service]] = ZLayer.succeed(new ProdLogger())

  class ProdLogger(logger: Logger = Logger("application")) extends AppLogger.Service {
    override def info(message: => String)(implicit mc: MarkerContext): UIO[Unit]  = UIO(logger.info(message))
    override def debug(message: => String)(implicit mc: MarkerContext): UIO[Unit] = UIO(logger.debug(message))
  }
}
*/

