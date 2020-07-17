package external.people.facade

import java.time.{Duration, Instant}

import external.people.client.{LiveWSClientProvider, PeopleClient}
import javax.inject.Inject
import model.{ErrorOr, PersonClientException}
import play.api.libs.json.{JsError, JsSuccess, Json, Reads}
import play.api.libs.ws.WSResponse
import zio._

import scala.concurrent.{ExecutionContext, Future}

trait PeopleFacade {
  def getPerson(id: String)(implicit ec: ExecutionContext): ZIO[Any, Throwable, PersonIdentity]
}

/*class PeopleFacadeImpl @Inject()(client: PeopleClient) extends PeopleFacade {

  //def getPerson(id: String)(implicit ec: ExecutionContext): Future[ErrorOr[PersonIdentity]] = {
  /*client.getPerson(id).map{ response =>
    if (response.status == 200) {
      response.json.validate[PersonIdentity] match {
        case JsSuccess(person, _) => Right(person)
        case e: JsError           => Left(PersonClientException(s"Errors: ${JsError.toJson(e)}"))
      }
    } else {
      Left(PersonClientException(s"Status: ${response.status}. Body: ${response.body}"))
    }
  }*/
  private def mapToPersonIdentity(wsResponse: WSResponse): IO[PersonClientException, PersonIdentity] = {
    wsResponse.json.validate[PersonIdentity] match {
      case JsSuccess(person, _) => ZIO.succeed(person)
      case e: JsError           => ZIO.fail(PersonClientException(s"Errors: ${JsError.toJson(e)}"))
    }
  }

  def getPerson(id: String)(implicit ec: ExecutionContext): Task[PersonIdentity] = {
    for {
      wsResponse      <- client.getPerson(id)
      personIdentity  <- mapToPersonIdentity(wsResponse)
    } yield personIdentity
  }

}*/

class PeopleFacadeStub @Inject()() extends PeopleFacade {
  override def getPerson(id: String)(implicit ec: ExecutionContext): ZIO[Any, Throwable, PersonIdentity] =
    ZIO.succeed(PersonIdentity(id, "nameStub", "lastnameStub", Instant.now().minus(Duration.ofDays(300000))))
}

case class PersonIdentity(id: String, name: String, lastname: String, dateOfBirth: Instant)
object PersonIdentity {
  implicit val personResponseReads: Reads[PersonIdentity] = Json.reads[PersonIdentity]
}