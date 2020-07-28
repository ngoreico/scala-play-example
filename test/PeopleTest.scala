import java.time.{Duration, Instant, LocalDate}

import external.people.model.PersonIdentity

import external.drivinglicences.DrivingLicencesFacade
import external.people.facade.PeopleFacade
import model.{DrivingLicence, LicenceType, PersonInformation}
import org.scalatestplus.mockito.MockitoSugar
import org.mockito.Mockito._
import org.scalatestplus.play.PlaySpec
import org.mockito.ArgumentMatchers._
import services.PeopleService
import zio.test._
import Assertion._


import scala.concurrent.ExecutionContext

class PeopleTest extends PlaySpec with MockitoSugar {

  implicit val executionContext = ExecutionContext.global

  /*def fixture = new {
    val peopleFacadeMock: PeopleFacade = mock[PeopleFacade]
    val drivingLicencesFacadeMock: DrivingLicencesFacade = mock[DrivingLicencesFacade]

    private val now: Instant = Instant.now

    val mockedPerson = PersonIdentity(id = "1", "Carlitos", "Example", Instant.now().minus(Duration.ofDays(300000)))
    val mockedDrivingLicence = DrivingLicence(id = "123", LicenceType.B1, now, now.plus(Duration.ofDays(365)))

    when(peopleFacadeMock.getPerson(anyString())(any())).thenReturn(ZIO.succeed(mockedPerson))
    when(drivingLicencesFacadeMock.retrieveDrivingLicence(anyString())(any())).thenReturn(ZIO.succeed(Option(mockedDrivingLicence)))
  }

  "Getting people with other documents" should {
    "Return mocked person" in {
      fixture.peopleFacadeMock.getPerson("1").map(_ must be (Right(fixture.mockedPerson)))
    }

    "Return mocked driving licence" in {
      fixture.drivingLicencesFacadeMock.retrieveDrivingLicence("1").map(_ must be (Right(Option(fixture.mockedDrivingLicence))))
    }

    "Return person information" in {
      val mockedPerson = fixture.mockedPerson
      val informationService = new PeopleService(fixture.peopleFacadeMock, fixture.drivingLicencesFacadeMock)
      informationService.getCompletePersonInformation("1").map{ personInformation =>
          personInformation mustEqual PersonInformation(mockedPerson.id, mockedPerson.name, mockedPerson.lastname, mockedPerson.dateOfBirth, Option(fixture.mockedDrivingLicence))
      }
    }*/

  val peopleTest = suite("PeopleTest") {
    test("With mocked client response") {
      ???
    }
  }

  object AllSuites extends DefaultRunnableSpec {
    def spec = suite("All tests")(peopleTest)
  }

}
