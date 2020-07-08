package external.client

import javax.inject.Inject
import play.api.libs.ws.{WSClient, WSResponse}

import scala.concurrent.{ExecutionContext, Future}

class PersonsClient@Inject()(ws: WSClient,
                             implicit val ec: ExecutionContext) {

  private val protocol: String = "http"
  private val host: String = "localhost:8080"
  private val path: String = "persons"

  def getPerson(id: String): Future[WSResponse] = {
    ws.url(s"$protocol://$host/$path/$id").get()
  }
}
