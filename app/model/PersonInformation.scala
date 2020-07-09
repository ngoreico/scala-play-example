package model

import java.time.Instant

import play.api.libs.json.{Json, OFormat, Reads, Writes}

case class PersonInformation(id: String,
                             name: String,
                             lastname: String,
                             dateOfBirth: Instant,
                             drivingLicence: Option[DrivingLicence])
/*object Person {
  implicit val personReads: Reads[Person] = Json.reads[Person]
  implicit val personWrites: Writes[Person] = Json.writes[Person]
}*/

case class DrivingLicence(id: String,
                          category: LicenceType,
                          obtainingDate: Instant,
                          expirationDate: Instant)

import enumeratum._

sealed trait LicenceType extends EnumEntry
object LicenceType extends Enum[LicenceType] {
  override def values: IndexedSeq[LicenceType] = findValues

  case object A1 extends LicenceType
  case object A2 extends LicenceType
  case object B1 extends LicenceType
  case object B2 extends LicenceType
}