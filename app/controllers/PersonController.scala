package controllers

import javax.inject.Inject
import model.{ApiError, AppEnv, AppEnvLayer, F}
import play.api.libs.json.Json
import play.api.mvc._
import services.PeopleService
import zio._
import zio.logging._

import scala.concurrent.ExecutionContext

class PersonController @Inject()(cc: ControllerComponents,
                                 peopleService: PeopleService)
                                (implicit exec: ExecutionContext,
                                 appEnv: AppEnvLayer) extends AbstractController(cc) {

  val runtime: zio.Runtime[zio.ZEnv] = zio.Runtime.default

  private def ioToTask[T](io: F[Result]): F[Result] =
    io.foldM(
      failure = throwable => ZIO.succeed(InternalServerError(Json.toJson(ApiError(500, throwable.getMessage))) as JSON),
      success = content => ZIO.succeed[Result](identity(content))
    )

  def zioAction[T](bodyParser: BodyParser[T])(actionBlock: Request[T] => F[Result])(implicit env: Layer[Throwable, AppEnv]): Action[T] = {
    Action.async(bodyParser) { request => runtime.unsafeRunToFuture( ioToTask(actionBlock(request)).provideLayer(env))}
  }

  def getPerson(id: String) = zioAction(parse.empty){ implicit request =>
    for {
      _ <- log.info(s"Getting person information with id $id")
      information <- peopleService.getCompletePersonInformation(id)
    } yield Ok(Json.toJson(information)) as JSON
  }

}
