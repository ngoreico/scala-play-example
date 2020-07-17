package controllers

import javax.inject.Inject
import model.ApiError
import play.api.libs.json.Json
import play.api.libs.ws.WSClient
import play.api.mvc._
import services.PeopleService
import zio.{Task, ZIO}

import scala.concurrent.ExecutionContext

class PersonController @Inject()(cc: ControllerComponents,
                                 wsClient: WSClient,
                                 peopleService: PeopleService)(implicit exec: ExecutionContext) extends AbstractController(cc) {

  val runtime: zio.Runtime[zio.ZEnv] = zio.Runtime.default

  private def ioToTask[T](io: ZIO[Any, Throwable, Result]): Task[Result] =
    io.fold[Result](throwable => InternalServerError(Json.toJson(ApiError(500, throwable.getMessage))) as JSON, identity)

  def zioAction[T](bodyParser: BodyParser[T])(actionBlock: Request[T] => ZIO[Any, Throwable, Result]): Action[T] = {
    Action.async(bodyParser) { request =>
      runtime.unsafeRunToFuture( ioToTask(actionBlock(request)) )
    } //TODO provideLayer...
  }

  def getPerson(id: String) = zioAction(parse.empty){ implicit request =>
    for (information <- peopleService.getCompletePersonInformation(id)) yield Ok(Json.toJson(information)) as JSON
  }

}
