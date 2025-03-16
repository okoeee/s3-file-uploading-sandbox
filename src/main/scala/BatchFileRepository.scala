import scala.concurrent.Future

trait BatchFileRepository {

  def add(batchFile: BatchFile): Future[Unit]
  def update(batchFile: BatchFile): Future[Unit]
  def delete(id: Long): Future[Unit]

}

object BatchFileRepositoryImpl extends BatchFileRepository {

  override def add(batchFile: BatchFile): Future[Unit] = {
    Future.successful(println(s"Added"))
  }

  override def update(batchFile: BatchFile): Future[Unit] = {
    Future.successful(println(s"Updated"))
  }

  override def delete(id: Long): Future[Unit] = {
    Future.successful(println(s"Deleted"))
  }

}
