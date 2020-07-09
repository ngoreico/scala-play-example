package services

import java.time.{Instant, LocalDate}
import java.util.Date

import external.drivinglicences.DrivingLicencesFacade
import external.people.facade.{PeopleFacade, PersonIdentity}
import javax.inject.Inject
import model.{DrivingLicence, ErrorOr, PersonInformation}

import scala.concurrent.ExecutionContext.Implicits
import scala.concurrent.{ExecutionContext, Future}

class PeopleService @Inject()(peopleFacade: PeopleFacade,
                              drivingLicencesFacade: DrivingLicencesFacade) {

  //TODO get custom threadpool or get by implicit parameters...
  implicit val ioExecutionContext: ExecutionContext = Implicits.global

  def getCompletePersonInformation(id: String): Future[ErrorOr[PersonInformation]] = {
    val personFuture          = peopleFacade.getPerson(id)
    val drivingLicenceFuture  = drivingLicencesFacade.retrieveDrivingLicence(id)

    /* First option... */
    for {
      eitherPerson          <- personFuture
      eitherDrivingLicence  <- drivingLicenceFuture
    } yield zipPersonWithDrivingLicence(eitherPerson, eitherDrivingLicence)

    /* Other option...*/
    personFuture.zipWith(drivingLicenceFuture)(zipPersonWithDrivingLicence)

  }

  private def zipPersonWithDrivingLicence(eitherPerson: ErrorOr[PersonIdentity], eitherDrivingLicence: ErrorOr[Option[DrivingLicence]]) = for {
    person                <- eitherPerson
    drivingLicenceOption  <- eitherDrivingLicence
  } yield mapToPersonInformation(person, drivingLicenceOption)

  private def mapToPersonInformation(person: PersonIdentity, drivingLicenceOption: Option[DrivingLicence]) = {
    PersonInformation(id = person.id,
                      name = person.name,
                      lastname = person.lastname,
                      dateOfBirth = person.dateOfBirth,
                      drivingLicence = drivingLicenceOption)
  }

}
