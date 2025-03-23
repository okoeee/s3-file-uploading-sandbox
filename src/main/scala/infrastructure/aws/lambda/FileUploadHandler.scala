package infrastructure.aws.lambda

import com.amazonaws.services.lambda.runtime.{Context, RequestHandler}
import com.amazonaws.services.lambda.runtime.events.SNSEvent
import infrastructure.aws.s3.{S3ClientProvider, S3ClientUploaderImpl}
import util.zip.ZipFileCreator

import scala.util.{Failure, Success}

class FileUploadHandler extends RequestHandler[SNSEvent, Unit] {

  override def handleRequest(snsEvent: SNSEvent, context: Context): Unit = {
    val message = snsEvent.getRecords.get(0).getSNS.getMessage
    println("From SNS: " + message)

    val s3Client = S3ClientProvider.s3Client
    val s3ClientUploader = new S3ClientUploaderImpl(s3Client)

    val bucketName = "s3-file-uploading-sandbox"
    val objectKey = "1/operational-statistics/1"

    val zipFileCreator = new ZipFileCreator
    val zipFile = zipFileCreator.createZipBytes(
      Map(
        "file1.txt" -> "Hello, World!",
        "file2.txt" -> "Hello, Scala!"
      )
    )

    s3ClientUploader
      .uploadWithZipFile(
        bucketName = bucketName,
        objectKey = objectKey + ".zip",
        file = zipFile
      ) match {
      case Success(_)         =>
        println(s"Successfully uploaded: $objectKey")
      case Failure(exception) =>
        println(s"Failed to upload: $objectKey")
    }

  }

}
