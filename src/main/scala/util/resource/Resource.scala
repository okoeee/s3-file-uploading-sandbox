package util.resource

import software.amazon.awssdk.services.sns.SnsClient

import scala.io.Source

object Resource {

  def using[A, B](resource: A)(process: A => B)(implicit closer: Closer[A]): B = {
    try {
      process(resource)
    } finally {
      closer.close(resource)
    }
  }

  trait Closer[A] {
    def close(resource: A): Unit
  }
  object Closer {
    implicit val sourceCloser: Closer[Source] = (resource: Source) => resource.close()
    implicit val autoCloseableCloser: Closer[AutoCloseable] = (resource: AutoCloseable) => resource.close()
    implicit val SnsClientCloser: Closer[SnsClient] = (resource: SnsClient) => resource.close()
  }

}
