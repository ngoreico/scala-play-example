package external.drivinglicences

import java.time.{Duration, Instant}

import external.people.client.LiveWSClientProvider
import javax.inject.Inject
import model.{DrivingLicence, ErrorOr, LicenceType}
import zio._

import scala.concurrent.{ExecutionContext, Future}

trait DrivingLicencesFacade {
  def retrieveDrivingLicence(documentId: String)(implicit ec: ExecutionContext): Task[Option[DrivingLicence]]
}

class DrivingLicencesFacadeStub @Inject()() extends DrivingLicencesFacade {
  override def retrieveDrivingLicence(documentId: String)(implicit ec: ExecutionContext): Task[Option[DrivingLicence]] = {
    val instant = Instant.now()
    ZIO.succeed(Option(DrivingLicence(id = "1", LicenceType.B1, obtainingDate = instant, expirationDate = instant.plus(Duration.ofDays(365)))))
  }
}

//TODO impl
