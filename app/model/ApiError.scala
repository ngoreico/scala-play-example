package model

import play.api.libs.json.{Json, Writes}

case class ApiError(status: Int, message: String)
object ApiError {
  implicit val writes: Writes[ApiError] = Json.writes[ApiError]
}
