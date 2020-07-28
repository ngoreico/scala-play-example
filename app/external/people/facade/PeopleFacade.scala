package external.people.facade

import java.time.{Duration, Instant}

import external.people.model.PersonIdentity
import zio._
import zio.logging._

import scala.concurrent.ExecutionContext

//object peopleFacade {
//type PeopleFacade = Has[PeopleFacade.Service]

object PeopleFacade {
  type PeopleFacade = Has[PeopleFacade.Service]

  trait Service {
    def getPerson(id: String)(implicit ec: ExecutionContext): RIO[Logging, PersonIdentity]
  }

  def getPerson(id: String)(implicit ec: ExecutionContext): RIO[PeopleFacade with Logging, PersonIdentity] = ZIO.accessM(_.get.getPerson(id))

  //This must be the real implementation.
  val live: ULayer[Has[Service]] = ZLayer.succeed {
    new Service {
      override def getPerson(id: String)(implicit ec: ExecutionContext): RIO[Logging, PersonIdentity] =
        for {
          _                   <- log.info("People facade!")
          //personAsWSResponse  <- getPerson(id)
          person              <- ZIO.succeed(PersonIdentity(id, "nameStub", "lastnameStub", Instant.now().minus(Duration.ofDays(300000))))
        } yield person
    }
  }
}

//}
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

/*class PeopleFacadeStub @Inject()() extends PeopleFacade {
  override def getPerson(id: String)(implicit ec: ExecutionContext): ZIO[Any, Throwable, PersonIdentity] =
    ZIO.succeed(PersonIdentity(id, "nameStub", "lastnameStub", Instant.now().minus(Duration.ofDays(300000))))
}*/

