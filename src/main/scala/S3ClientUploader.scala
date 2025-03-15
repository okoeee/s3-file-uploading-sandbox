import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.{PutObjectRequest, PutObjectResponse}

import java.nio.file.Path
import scala.util.Try

object S3ClientUploader {

  def uploadFile(
    s3Client: S3Client,
    bucketName: String,
    objectKey: String,
    filePath: Path
  ): Try[PutObjectResponse] = {
    Try {
      val objectForRequest = PutObjectRequest
        .builder()
        .bucket(bucketName)
        .key(objectKey)
        .build()
      s3Client.putObject(objectForRequest, filePath)
    }
  }

}
