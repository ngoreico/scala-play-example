package controllers

import javax.inject.Inject
import model.ApiError
import org.apache.commons.logging.impl.SLF4JLog
import play.api.libs.json.Json
import play.api.libs.ws.WSClient
import play.api.mvc._
import services.PeopleService
import zio._
import zio.clock.Clock
import zio.console.Console
import zio.logging._

import scala.concurrent.ExecutionContext

class PersonController @Inject()(cc: ControllerComponents,
                                 wsClient: WSClient,
                                 peopleService: PeopleService)(implicit exec: ExecutionContext) extends AbstractController(cc) {

  val runtime: zio.Runtime[zio.ZEnv] = zio.Runtime.default
  type F[R] = ZIO[AppEnv, Throwable, R]
  type AppEnv = zio.ZEnv with Logging

  implicit val env: ZLayer[Any, Throwable, AppEnv] = {
    ZEnv.live >+> Logging.console(format = (_, logEntry) => logEntry, rootLoggerName = Some("default-logger"))
  }

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
      _ <- log.info(s"Getting person with id $id")
      information <- peopleService.getCompletePersonInformation(id)
    } yield Ok(Json.toJson(information)) as JSON
  }

}
