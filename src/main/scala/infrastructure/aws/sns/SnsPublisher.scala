package infrastructure.aws.sns

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.sns.SnsClient
import software.amazon.awssdk.services.sns.model.{PublishRequest, PublishResponse}

import scala.util.Try

class SnsPublisher(
  region: Region = Region.US_EAST_1
) {

  private val snsClient = SnsClient
    .builder()
    .region(region)
    .credentialsProvider(SnsPublisher.credentialsProvider)
    .build()

  /** Publish a message to an SNS topic
    *
    * @param topicArn
    *   The ARN of the SNS topic
    * @param message
    *   The message to publish
    * @param subject
    *   Optional subject for the message
    * @return
    *   Try[PublishResponse] containing the message ID on success
    */
  def publish(topicArn: String, message: String, subject: Option[String] = None): Try[PublishResponse] = {
    Try {
      val requestBuilder = PublishRequest
        .builder()
        .topicArn(topicArn)
        .message(message)

      // Add subject if provided
      subject.foreach(requestBuilder.subject)

      snsClient.publish(requestBuilder.build())
    }
  }

  def close(): Unit = {
    snsClient.close()
  }
}
object SnsPublisher {

  private val credentialsProvider = ProfileCredentialsProvider.create("s3-file-uploading-sandbox-user")

}
