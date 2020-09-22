package services

import external.people.model.PersonIdentity
import external.people.peopleClient.PeopleClient
import model.{DrivingLicence, PersonInformation}
import sttp.client.asynchttpclient.zio.SttpClient
import zio._
import zio.logging._

import scala.concurrent.ExecutionContext

class PeopleService() {

  import external.people.facade.PeopleFacade._
  import external.drivinglicences.DrivingLicencesFacade._
  //Code reduction...used to return Future[Either[Throwable, PersonInformation]] with its correspond monad handle stuff...
  def getCompletePersonInformation(id: String)(implicit ec: ExecutionContext): ZIO[DrivingLicencesFacade with Logging with PeopleFacade with PeopleClient with SttpClient, Throwable, PersonInformation] =
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
