package external.people.facade

import java.time.Instant

import external.people.client.PeopleClient
import javax.inject.Inject
import model.{ErrorOr, PersonClientException}
import play.api.libs.json.{JsError, JsSuccess, Json, Reads}

import scala.concurrent.{ExecutionContext, Future}

trait PeopleFacade {
  def getPerson(id: String)(implicit ec: ExecutionContext): Future[ErrorOr[PersonIdentity]]
}

class PeopleFacadeImpl @Inject()(client: PeopleClient) {

  def getPerson(id: String)(implicit ec: ExecutionContext): Future[ErrorOr[PersonIdentity]] = {
    client.getPerson(id).map{ response =>
      if (response.status == 200) {
        response.json.validate[PersonIdentity] match {
          case JsSuccess(person, _) => Right(person)
          case e: JsError           => Left(PersonClientException(s"Errors: ${JsError.toJson(e)}"))
        }
      } else {
       Left(PersonClientException(s"Status: ${response.status}. Body: ${response.body}"))
      }
    }
  }

}

case class PersonIdentity(id: String, name: String, lastname: String, dateOfBirth: Instant)
object PersonIdentity {
  implicit val personResponseReads: Reads[PersonIdentity] = Json.reads[PersonIdentity]
}