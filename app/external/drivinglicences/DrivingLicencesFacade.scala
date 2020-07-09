package external.drivinglicences

import model.{DrivingLicence, ErrorOr}

import scala.concurrent.{ExecutionContext, Future}

trait DrivingLicencesFacade {
  def retrieveDrivingLicence(documentId: String)(implicit ec: ExecutionContext): Future[ErrorOr[Option[DrivingLicence]]]
}

//TODO impl
