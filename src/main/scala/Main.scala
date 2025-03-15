import java.time.{LocalDate, LocalDateTime}

object Main extends App {

  val batchFileUploader = new BatchFileUploader(
    s3Client = S3ClientProvider.s3Client,
    presigner = S3ClientPersigner.presigner,
  )

  val targetBatchFile = BatchFile(
    id = 1,
    userId = 1,
    fileName = "sample.txt",
    period = Period(
      startDate = LocalDate.of(2021, 1, 1),
      endDate = LocalDate.of(2021, 12, 31)
    ),
    fileType = BatchFile.FileType.DataSummary,
    fileSize = Some(1024),
    generatedAt = LocalDateTime.now()
  )

  batchFileUploader.upload(targetBatchFile)

}
