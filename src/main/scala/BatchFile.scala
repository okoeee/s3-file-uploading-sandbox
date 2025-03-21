import java.time.{LocalDate, LocalDateTime}

case class Period(
  startDate: LocalDate,
  endDate:   LocalDate
)

case class BatchFile(
  id:          Long,
  userId:      Long,
  fileName:    String,
  period:      Period,
  status:      BatchFile.Status,
  fileType:    BatchFile.FileType,
  fileSize:    Option[Long],
  generatedAt: LocalDateTime
) {

  lazy val s3ObjectKey =
    s"$userId/${fileType.value}/$fileName"

}
object BatchFile {

  sealed trait FileType { def value: String }
  object FileType {
    case object DataSummary extends FileType { def value = "data-summary" }
    case object OperationalStatistics extends FileType { def value = "operational-statistics" }
  }

  sealed trait Status { def value: String }
  object Status {
    case object Pending extends Status { def value = "pending" }
    case object UploadSuccess extends Status { def value = "upload-success" }
    case object UploadFailed extends Status { def value = "upload-failed" }
  }

}
