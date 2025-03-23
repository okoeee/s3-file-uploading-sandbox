import infrastructure.aws.s3.S3ClientUploader

import java.nio.file.Paths
import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

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
             .uploadFileWithPath(
               bucketName = bucketName,
               objectKey = objectKey,
               filePath = filePath
             ) match {
             case Success(_)         =>
               val updatedStatus = batchFile.copy(
                 status = BatchFile.Status.UploadSuccess
               )
               println(s"Successfully uploaded: $objectKey")
               batchFileRepository.update(updatedStatus)
             case Failure(exception) =>
               val updatedStatus = batchFile.copy(
                 status = BatchFile.Status.UploadFailed
               )
               println(s"Failed to upload: $objectKey")
               batchFileRepository.update(updatedStatus)
           }
    } yield ()

  }

}
