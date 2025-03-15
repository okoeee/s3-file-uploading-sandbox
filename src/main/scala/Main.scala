import java.nio.file.Paths

object Main extends App {

  val s3Client = S3ClientProvider.s3Client

  // S3 bucket and object key
  private val bucketName = "s3-file-uploading-sandbox"
  private val objectKey = "1/batch-download/sample.txt"

  // filePath
  private val resource = getClass.getResource("/sample.txt")
  private val filePath = Paths.get(resource.toURI)

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

  s3Client.close()

  println("File uploaded successfully\n")
}
