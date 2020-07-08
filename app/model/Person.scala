package model

import play.api.libs.json.{Json, Reads, Writes}

case class Person(id: String, name: String, lastname: String)
object Person {
  implicit val personReads: Reads[Person] = Json.reads[Person]
  implicit val personWrites: Writes[Person] = Json.writes[Person]
}
