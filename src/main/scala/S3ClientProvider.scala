import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client

object S3ClientProvider {
  private val defaultRegion = Region.US_EAST_1
  private val credentialsProvider = ProfileCredentialsProvider.create("s3-file-uploading-sandbox-user")

  lazy val s3Client: S3Client = S3Client
    .builder()
    .region(defaultRegion)
    .credentialsProvider(credentialsProvider)
    .build()
}
