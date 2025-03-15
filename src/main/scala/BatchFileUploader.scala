import Main.getClass
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.presigner.S3Presigner

import java.nio.file.Paths

class BatchFileUploader(
  s3Client:  S3Client,
  presigner: S3Presigner
) {

  // S3 bucket and object key
  private val bucketName = "s3-file-uploading-sandbox"

  def upload(
    batchFile: BatchFile
  ): Unit = {
    val objectKey = batchFile.s3ObjectKey

    // filePath
    val resource = getClass.getResource("/sample.txt")
    val filePath = Paths.get(resource.toURI)

    S3ClientUploader
      .uploadFile(
        s3Client = s3Client,
        bucketName = bucketName,
        objectKey = objectKey,
        filePath = filePath
      ) match {
      case util.Success(_)         => println("File uploaded successfully")
      case util.Failure(exception) => println(s"File uploading failed: ${exception.getMessage}")
    }

    val presignUrl = S3ClientPresignGetter
      .getPresignedUrl(
        presigner = presigner,
        bucketName = bucketName,
        objectKey = objectKey
      )

    println(s"Presigned URL: $presignUrl")

    s3Client.close()
    presigner.close()
  }

}
