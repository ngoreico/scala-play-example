package object model {

  type ErrorOr[T] = Either[Throwable, T]
}
