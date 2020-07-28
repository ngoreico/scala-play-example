package external.drivinglicences

import java.time.{Duration, Instant}

import model.{DrivingLicence, LicenceType}
import zio._
import zio.logging._

import scala.concurrent.ExecutionContext

object DrivingLicencesFacade {
  type DrivingLicencesFacade = Has[DrivingLicencesFacade.Service]

  trait Service {
    def retrieveDrivingLicence(documentId: String)(implicit ec: ExecutionContext): RIO[Logging, Option[DrivingLicence]]
  }

  def retrieveDrivingLicence(documentId: String)(implicit ec: ExecutionContext): RIO[DrivingLicencesFacade with Logging, Option[DrivingLicence]] =
    ZIO.accessM(_.get.retrieveDrivingLicence(documentId))

  //This must be the real implementation.
  val live: ZLayer[Logging, Throwable, Has[DrivingLicencesFacade.Service]] = ZLayer.succeed {
    new Service {
      override def retrieveDrivingLicence(documentId: String)(implicit ec: ExecutionContext): RIO[Logging, Option[DrivingLicence]] = {
        for {
          _ <- log.info("Driving Licences Facade STUB !")
          instant <- ZIO(Instant.now())
          drivingLicence <- ZIO(Option(DrivingLicence(id = "1", LicenceType.B1, obtainingDate = instant, expirationDate = instant.plus(Duration.ofDays(365)))))
        } yield drivingLicence
      }
    }
  }
}

//TODO impl
