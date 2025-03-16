import org.mockito.ArgumentMatchers.{any, anyString, eq => eqTo}
import org.mockito.Mockito.{verify, when}
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.mockito.MockitoSugar

import java.nio.file.Path
import java.time.{LocalDate, LocalDateTime}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

class BatchFileUploaderTest extends AnyFreeSpec with Matchers with MockitoSugar {

  // Mocks
  private val mockS3ClientUploader = mock[S3ClientUploader]
  private val mockBatchFileRepository = mock[BatchFileRepository]

  // Test subject
  private lazy val sut = new BatchFileUploader(
    s3ClientUploader = mockS3ClientUploader,
    batchFileRepository = mockBatchFileRepository
  )

  // Test data
  private val testBatchFile = BatchFile(
    id = 1,
    userId = 1,
    fileName = "test.txt",
    period = Period(
      startDate = LocalDate.of(2021, 1, 1),
      endDate = LocalDate.of(2021, 12, 31)
    ),
    status = BatchFile.Status.Pending,
    fileType = BatchFile.FileType.DataSummary,
    fileSize = Some(1024),
    generatedAt = LocalDateTime.now()
  )

  "BatchFileUploader" - {
    "upload" - {

      "File status updated successfully upon successful upload" in {
        // Set up the repository mock
        when(mockBatchFileRepository.add(any[BatchFile])).thenReturn(Future.successful(()))
        when(mockBatchFileRepository.update(any[BatchFile])).thenReturn(Future.successful(()))

        // Set up the S3 upload mock
        when(
          mockS3ClientUploader.uploadFile(
            bucketName = anyString(),
            objectKey = anyString(),
            filePath = any[Path]
          )
        ).thenReturn(Success(mock[software.amazon.awssdk.services.s3.model.PutObjectResponse]))

        // When
        sut.upload(testBatchFile)

        val updated = testBatchFile.copy(
          status = BatchFile.Status.UploadSuccess
        )

        // Then
        verify(mockBatchFileRepository).update(updated)
      }

      "Upload fails, File Status updated to Upload Failed" in {
        // Set up the repository mock
        when(mockBatchFileRepository.add(any[BatchFile])).thenReturn(Future.successful(()))
        when(mockBatchFileRepository.update(any[BatchFile])).thenReturn(Future.successful(()))

        // Set up the S3 upload mock
        when(
          mockS3ClientUploader.uploadFile(
            bucketName = anyString(),
            objectKey = anyString(),
            filePath = any[Path]
          )
        ).thenReturn(Failure(new RuntimeException("Upload failed")))

        // When
        sut.upload(testBatchFile)

        val updated = testBatchFile.copy(
          status = BatchFile.Status.UploadFailed
        )

        // Then
        verify(mockBatchFileRepository).update(updated)
      }

    }
  }
}
