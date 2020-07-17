import external.drivinglicences.drivingLicencesFacade.DrivingLicencesFacade
import external.people.facade.peopleFacade.PeopleFacade
import zio.{Has, Layer, ZIO}
import zio.logging.Logging

package object model {
  type F[R]         = ZIO[AppEnv, Throwable, R]
  type AppEnv       = zio.ZEnv with Logging with Has[PeopleFacade.Service] with Has[DrivingLicencesFacade.Service]
  type AppEnvLayer  = Layer[Throwable, AppEnv]
}
