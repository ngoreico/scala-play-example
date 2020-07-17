package controllers

import javax.inject.Inject
import model.AppEnvLayer
import play.api.libs.json.Json
import play.api.mvc._
import services.PeopleService
import zio.logging._

import scala.concurrent.ExecutionContext

class PersonController @Inject()(cc: ControllerComponents,
                                 peopleService: PeopleService)
                                (implicit exec: ExecutionContext, appEnv: AppEnvLayer) extends ZIOController(cc) {

  def getPerson(id: String) = zioAction(parse.empty){ implicit request =>
    for {
      _ <- log.info(s"Getting person information with id $id")
      information <- peopleService.getCompletePersonInformation(id)
    } yield Ok(Json.toJson(information)) as JSON
  }

}
