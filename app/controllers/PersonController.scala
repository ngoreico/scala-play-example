package controllers

import external.facade.PersonsFacade
import javax.inject.Inject
import model.ApiError
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.ExecutionContext

class PersonController @Inject()(cc: ControllerComponents,
                                 personsFacade: PersonsFacade)(implicit exec: ExecutionContext) extends AbstractController(cc) {

  def getPerson(id: String) = Action.async {
    personsFacade.getPerson(id).map {
      case Right(person) => Ok(Json.toJson(person)) as JSON
      case Left(error) => InternalServerError(Json.toJson(ApiError(500, error.getMessage))) as JSON
    }
  }
}
