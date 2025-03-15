import software.amazon.awssdk.services.s3.model.GetObjectRequest
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest

import java.net.URL
import java.time.Duration

object S3ClientPresignGetter {

  def getPresignedUrl(
    presigner: S3Presigner,
    bucketName: String,
    objectKey: String
  ): URL = {
    val getObjectRequest = GetObjectRequest
      .builder()
      .bucket(bucketName)
      .key(objectKey)
      .build()

    val getPresignedUrlRequest = GetObjectPresignRequest
      .builder()
      .signatureDuration(Duration.ofDays(7))
      .getObjectRequest(getObjectRequest)
      .build()

    presigner
      .presignGetObject(getPresignedUrlRequest)
      .url()
  }

}
