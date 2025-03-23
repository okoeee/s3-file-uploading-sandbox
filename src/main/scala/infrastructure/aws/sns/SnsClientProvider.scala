package infrastructure.aws.sns

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.sns.SnsClient

object SnsDefaultSettings {
  val region: Region = Region.US_EAST_1
  val credentialsProvider: ProfileCredentialsProvider = ProfileCredentialsProvider.create("s3-file-uploading-sandbox-user")
}

object SnsClientProvider {

  lazy val snsClient: SnsClient = SnsClient
    .builder()
    .region(SnsDefaultSettings.region)
    .credentialsProvider(SnsDefaultSettings.credentialsProvider)
    .build()

}
