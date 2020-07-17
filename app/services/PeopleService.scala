package services

import external.drivinglicences.DrivingLicencesFacade
import external.people.facade.{PeopleFacade, PersonIdentity}
import javax.inject.Inject
import model.{DrivingLicence, PersonInformation}
import zio._

import scala.concurrent.ExecutionContext

class PeopleService @Inject()(peopleFacade: PeopleFacade,
                              drivingLicencesFacade: DrivingLicencesFacade) {

  //Code reduction...used to return Future[Either[Throwable, PersonInformation]] with its correspond monad handle stuff...
  def getCompletePersonInformation(id: String)(implicit ec: ExecutionContext): ZIO[Any, Throwable, PersonInformation] =
    for {
      person          <- peopleFacade.getPerson(id)
      //This used to return Future[Either[Throwable, Option[DrivingLicence]]] D:
      drivingLicence  <- drivingLicencesFacade.retrieveDrivingLicence(id)
    } yield mapToPersonInformation(person, drivingLicence)

  private def mapToPersonInformation(person: PersonIdentity, drivingLicenceOption: Option[DrivingLicence]) = {
    PersonInformation(id = person.id,
                      name = person.name,
                      lastname = person.lastname,
                      dateOfBirth = person.dateOfBirth,
                      drivingLicence = drivingLicenceOption)
  }
}
