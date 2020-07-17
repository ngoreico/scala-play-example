import zio.{Layer, ZIO}
import zio.logging.Logging

package object model {
  type F[R]         = ZIO[AppEnv, Throwable, R]
  type AppEnv       = zio.ZEnv with Logging
  type AppEnvLayer  = Layer[Throwable, AppEnv]
}
