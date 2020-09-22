import com.google.inject.{AbstractModule, Provides}
import external.drivinglicences.DrivingLicencesFacade
import external.people.facade.PeopleFacade
import external.people.peopleClient.PeopleClient
import javax.inject.Singleton
import model.AppEnvLayer
import net.codingwell.scalaguice.ScalaModule
import services.PeopleService
import sttp.client.asynchttpclient.zio.AsyncHttpClientZioBackend
import zio._
import zio.logging.{LogLevel, Logging}

/**
 * This class is a Guice module that tells Guice how to bind several
 * different types. This Guice module is created when the Play
 * application starts.

 * Play will automatically use any class called `Module` that is in
 * the root package. You can create modules in other locations by
 * adding `play.modules.enabled` settings to the `application.conf`
 * configuration file.
 */
class Module extends AbstractModule with ScalaModule {

  override def configure() = {
    bind(classOf[PeopleService])
  }

  @Provides
  @Singleton
  def appEnvProvider: AppEnvLayer = {
    ZEnv.live >+>
      Logging.console(format = (_, logEntry) => logEntry, logLevel = LogLevel.Info) >+>
      PeopleFacade.live >+>
      DrivingLicencesFacade.live >+>
      PeopleClient.live  >+>
      AsyncHttpClientZioBackend.layer()
      //TODO
  }

}
