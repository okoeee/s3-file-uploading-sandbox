package infrastructure.aws.s3

import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.presigner.S3Presigner

object S3DefaultSettings {
  val defaultRegion: Region = Region.US_EAST_1
}

object S3ClientProvider {

  lazy val s3Client: S3Client = S3Client
    .builder()
    .region(S3DefaultSettings.defaultRegion)
    .credentialsProvider(DefaultCredentialsProvider.create())
    .build()
}

object S3ClientPersigner {
  lazy val presigner: S3Presigner = S3Presigner
    .builder()
    .region(S3DefaultSettings.defaultRegion)
    .credentialsProvider(DefaultCredentialsProvider.create())
    .build()
}
