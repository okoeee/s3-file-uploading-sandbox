import java.nio.file.Paths
import scala.concurrent.ExecutionContext

class BatchFileUploader(
  s3ClientUploader:    S3ClientUploader,
  batchFileRepository: BatchFileRepository
)(implicit ec: ExecutionContext) {

  // S3 bucket and object key
  private val bucketName = "s3-file-uploading-sandbox"

  def upload(
    batchFile: BatchFile
  ): Unit = {
    val objectKey = batchFile.s3ObjectKey

    // filePath
    val resource = getClass.getResource("/sample.txt")
    val filePath = Paths.get(resource.toURI)

    for {
      _ <- batchFileRepository.add(batchFile)
      _ <- s3ClientUploader
             .uploadFile(
               bucketName = bucketName,
               objectKey = objectKey,
               filePath = filePath
             ) match {
             case util.Success(_)         =>
               val updatedStatus = batchFile.copy(
                 status = BatchFile.Status.UploadSuccess
               )
               println(s"Successfully uploaded: $objectKey")
               batchFileRepository.update(updatedStatus)
             case util.Failure(exception) =>
               val updatedStatus = batchFile.copy(
                 status = BatchFile.Status.UploadFailed
               )
               println(s"Failed to upload: $objectKey")
               batchFileRepository.update(updatedStatus)
           }
    } yield ()

  }

}
