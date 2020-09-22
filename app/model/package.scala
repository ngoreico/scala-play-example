import external.drivinglicences.DrivingLicencesFacade
import external.people.facade.PeopleFacade
import external.people.peopleClient.PeopleClient
import sttp.client.asynchttpclient.zio.SttpClient
import zio.{Has, Layer, ZIO}
import zio.logging.Logging

package object model {
  type F[R]         = ZIO[AppEnv, Throwable, R]
  type AppEnv       = zio.ZEnv with Logging with Has[PeopleFacade.Service] with Has[DrivingLicencesFacade.Service] with Has[PeopleClient.Service] with SttpClient
  type AppEnvLayer  = Layer[Throwable, AppEnv]
}
