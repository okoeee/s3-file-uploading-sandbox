package infrastructure.aws.s3

import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.{PutObjectRequest, PutObjectResponse}

import java.nio.file.Path
import scala.util.Try

trait S3ClientUploader {

  def uploadFileWithPath(
    bucketName: String,
    objectKey: String,
    filePath: Path
  ): Try[PutObjectResponse]

  def uploadWithZipFile(
    bucketName: String,
    objectKey: String,
    file: Array[Byte],
    contentType: String = "application/zip"
  ): Try[PutObjectResponse]
}

class S3ClientUploaderImpl(
  s3Client: S3Client
) extends S3ClientUploader {

  def uploadFileWithPath(
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

  def uploadWithZipFile(
    bucketName: String,
    objectKey: String,
    file: Array[Byte],
    contentType: String = "application/zip"
  ): Try[PutObjectResponse] = {
    Try {
      val objectForRequest = PutObjectRequest
        .builder()
        .bucket(bucketName)
        .key(objectKey)
        .contentType(contentType)
        .build()
      val requestBody = RequestBody.fromBytes(file)
      s3Client.putObject(objectForRequest, requestBody)
    }
  }

}
