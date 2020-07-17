package external.people.client

import external.people.facade.PersonIdentity
import javax.inject.Inject
import play.api.MarkerContext
import play.api.libs.ws.{WSClient, WSResponse}

import scala.concurrent.{ExecutionContext, Future}
import zio._

class PeopleClient@Inject()(ws: WSClient) {

  private val protocol: String = "http"
  private val host: String = "localhost:8080"
  private val path: String = "persons"

  def getPerson(id: String)(implicit ec: ExecutionContext): Task[WSResponse] =
    ZIO.fromFuture(_ => ws.url(s"$protocol://$host/$path/$id").get())
}

trait RestClientProvider {

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
}

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
