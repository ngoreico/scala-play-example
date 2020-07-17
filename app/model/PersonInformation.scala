package model

import java.time.Instant

import play.api.libs.json.{Json, OFormat, Reads, Writes}

case class PersonInformation(id: String,
                             name: String,
                             lastname: String,
                             dateOfBirth: Instant,
                             drivingLicence: Option[DrivingLicence])
object PersonInformation {
  implicit val format: OFormat[PersonInformation] = Json.format[PersonInformation]
}

case class DrivingLicence(id: String,
                          category: LicenceType,
                          obtainingDate: Instant,
                          expirationDate: Instant)

object DrivingLicence {
  implicit val format: OFormat[DrivingLicence] = Json.format[DrivingLicence]
}

import enumeratum._

sealed trait LicenceType extends EnumEntry
object LicenceType extends PlayEnum[LicenceType] {
  override def values: IndexedSeq[LicenceType] = findValues

  case object A1 extends LicenceType
  case object A2 extends LicenceType
  case object B1 extends LicenceType
  case object B2 extends LicenceType

}