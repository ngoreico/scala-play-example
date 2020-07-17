package controllers

import javax.inject.Inject
import model.{ApiError, AppEnv, F}
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, Action, BodyParser, ControllerComponents, Request, Result}
import zio.{Layer, ZIO}

class ZIOController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  val runtime: zio.Runtime[zio.ZEnv] = zio.Runtime.default

  private def ioToTask[T](io: F[Result]): F[Result] =
    io.foldM(
      failure = throwable => ZIO.succeed(InternalServerError(Json.toJson(ApiError(500, throwable.getMessage))) as JSON),
      success = content => ZIO.succeed[Result](identity(content))
    )

  def zioAction[T](bodyParser: BodyParser[T])(actionBlock: Request[T] => F[Result])(implicit env: Layer[Throwable, AppEnv]): Action[T] = {
    Action.async(bodyParser) { request => runtime.unsafeRunToFuture( ioToTask(actionBlock(request)).provideLayer(env))}
  }

}
