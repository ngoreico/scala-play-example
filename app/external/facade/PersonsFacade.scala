package external.facade

import external.client.PersonsClient
import javax.inject.Inject
import model.{ErrorOr, Person, PersonClientException}
import play.api.libs.json.{JsError, JsSuccess}
import play.api.libs.ws
import play.libs.ws.WSResponse

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

class PersonsFacade @Inject()(client: PersonsClient) {

  def getPerson(id: String)(implicit ec: ExecutionContext): Future[ErrorOr[Person]] = {
    client.getPerson(id).map{ response =>
      if (response.status == 200) {
        response.json.validate[Person] match {
          case JsSuccess(person, _) => Right(person)
          case e: JsError           => Left(PersonClientException(s"Errors: ${JsError.toJson(e)}"))
        }
      } else {
       Left(PersonClientException(s"Status: ${response.status}. Body: ${response.body}"))
      }
    }
  }

}
