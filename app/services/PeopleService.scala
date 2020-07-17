package services

import external.drivinglicences.drivingLicencesFacade.DrivingLicencesFacade
import external.people.facade.peopleFacade.PeopleFacade
import external.people.model.PersonIdentity
import javax.inject.Inject
import model.{DrivingLicence, PersonInformation}
import zio._
import zio.logging._

import scala.concurrent.ExecutionContext

class PeopleService @Inject()() {

  import PeopleFacade._
  import DrivingLicencesFacade._
  //Code reduction...used to return Future[Either[Throwable, PersonInformation]] with its correspond monad handle stuff...
  def getCompletePersonInformation(id: String)(implicit ec: ExecutionContext): ZIO[Logging with PeopleFacade with DrivingLicencesFacade, Throwable, PersonInformation] =
    for {
      _               <- log.info("People service...!")
      person          <- getPerson(id)
      //This used to return Future[Either[Throwable, Option[DrivingLicence]]] D:
      drivingLicence  <- retrieveDrivingLicence(id)
    } yield mapToPersonInformation(person, drivingLicence)

  private def mapToPersonInformation(person: PersonIdentity, drivingLicenceOption: Option[DrivingLicence]) = {
    PersonInformation(id = person.id,
                      name = person.name,
                      lastname = person.lastname,
                      dateOfBirth = person.dateOfBirth,
                      drivingLicence = drivingLicenceOption)
  }
}
