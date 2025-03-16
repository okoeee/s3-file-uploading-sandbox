import java.time.{LocalDate, LocalDateTime}

object Main extends App {

  import scala.concurrent.ExecutionContext.Implicits.global

  val s3Client = S3ClientProvider.s3Client
  val s3ClientUploader = new S3ClientUploaderImpl(s3Client)

  val batchFileUploader = new BatchFileUploader(
    s3ClientUploader = s3ClientUploader,
    batchFileRepository = BatchFileRepositoryImpl
  )

  val targetBatchFile = BatchFile(
    id = 1,
    userId = 1,
    fileName = "sample.txt",
    period = Period(
      startDate = LocalDate.of(2021, 1, 1),
      endDate = LocalDate.of(2021, 12, 31)
    ),
    status = BatchFile.Status.Pending,
    fileType = BatchFile.FileType.DataSummary,
    fileSize = Some(1024),
    generatedAt = LocalDateTime.now()
  )

  batchFileUploader.upload(targetBatchFile)

}
