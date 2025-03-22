package infrastructure.aws.sns

import infrastructure.aws.sns.SnsPublisher
import org.scalatest.freespec.AnyFreeSpec

class SnsPublisherTest extends AnyFreeSpec {

  "Test Publish" in {
    val publisher = new SnsPublisher()

    val topicArn = "arn:aws:sns:us-east-1:149536463306:S3FileUploadingSandbox"

    val message =
      """
      |{
      |  "message": "Hello, SNS!",
      |}
      |""".stripMargin

    val response = publisher.publish(topicArn, message)

    response match {
      case scala.util.Success(value)     => assert(value.messageId() != null)
      case scala.util.Failure(exception) => fail(exception)
    }

    publisher.close()
  }

}
