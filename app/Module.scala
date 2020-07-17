import com.google.inject.{AbstractModule, Provides}
import external.drivinglicences.{DrivingLicencesFacade, DrivingLicencesFacadeStub}
import external.people.facade.{PeopleFacade, PeopleFacadeStub}
import javax.inject.Singleton
import model.{AppEnv, AppEnvLayer}
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
    bind(classOf[PeopleFacade]).toInstance(new PeopleFacadeStub)
    bind(classOf[DrivingLicencesFacade]).toInstance(new DrivingLicencesFacadeStub)
  }

  @Provides
  @Singleton
  def appEnvProvider: AppEnvLayer = {
    ZEnv.live >+> Logging.console(format = (_, logEntry) => logEntry, rootLoggerName = Some("default-logger"))
  }

}
