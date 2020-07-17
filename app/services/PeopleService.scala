package services

import java.time.{Instant, LocalDate}
import java.util.Date

import external.drivinglicences.{DrivingLicencesFacade, DrivingLicencesFacadeStub}
import external.people.client.LiveWSClientProvider
import external.people.facade.{PeopleFacade, PeopleFacadeStub, PersonIdentity}
import javax.inject.Inject
import model.{DrivingLicence, PersonInformation}
import zio._

import scala.concurrent.ExecutionContext.Implicits
import scala.concurrent.ExecutionContext

class PeopleService @Inject()(peopleFacade: PeopleFacade,
                              drivingLicencesFacade: DrivingLicencesFacade) {

  //TODO get custom threadpool or get by implicit parameters...
  implicit val ioExecutionContext: ExecutionContext = Implicits.global

  def getCompletePersonInformation(id: String): ZIO[Any, Throwable, PersonInformation] =
    for {
      person          <- peopleFacade.getPerson(id)
      drivingLicence  <- drivingLicencesFacade.retrieveDrivingLicence(id)
    } yield mapToPersonInformation(person, drivingLicence)

  private def mapToPersonInformation(person: PersonIdentity, drivingLicenceOption: Option[DrivingLicence]) = {
    PersonInformation(id = person.id,
                      name = person.name,
                      lastname = person.lastname,
                      dateOfBirth = person.dateOfBirth,
                      drivingLicence = drivingLicenceOption)
  }

  /*def getCompletePersonInformation(id: String): ZIO[Any, Throwable, PersonInformation] = {
    val personFuture          = peopleFacadeStub.getPerson(id)
    val drivingLicenceFuture  = drivingLicencesFacadeStub.retrieveDrivingLicence(id)

    /*/* First option... */
    for {
      eitherPerson          <- personFuture
      eitherDrivingLicence  <- drivingLicenceFuture
    } yield mapToPersonInformation(eitherPerson, eitherDrivingLicence)*/

    /* Other option...*/
    personFuture.zipWith(drivingLicenceFuture)(mapToPersonInformation)

  }*/

  /*private def zipPersonWithDrivingLicence(eitherPerson: ErrorOr[PersonIdentity], eitherDrivingLicence: ErrorOr[Option[DrivingLicence]]) = for {
    person                <- eitherPerson
    drivingLicenceOption  <- eitherDrivingLicence
  } yield mapToPersonInformation(person, drivingLicenceOption)*/

}
