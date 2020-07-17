import com.google.inject.{AbstractModule, Provides}
import external.drivinglicences.drivingLicencesFacade.DrivingLicencesFacade
import external.people.facade.peopleFacade.PeopleFacade
import javax.inject.Singleton
import model.AppEnvLayer
import net.codingwell.scalaguice.ScalaModule
import services.PeopleService
import zio._
import zio.logging.Logging

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
    //PeopleFacade.live ++ DrivingLicencesFacade.live
    ZEnv.live >+>
      Logging.console(format = (_, logEntry) => logEntry, rootLoggerName = Some("default-logger")) >+>
      PeopleFacade.live >+>
      DrivingLicencesFacade.live
  }

}
