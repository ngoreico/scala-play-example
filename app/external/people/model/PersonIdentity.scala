package external.people.model

import java.time.Instant

import play.api.libs.json.{Json, Reads}

case class PersonIdentity(id: String, name: String, lastname: String, dateOfBirth: Instant)
object PersonIdentity {
  implicit val personResponseReads: Reads[PersonIdentity] = Json.reads[PersonIdentity]
}
