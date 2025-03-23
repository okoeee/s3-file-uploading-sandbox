package infrastructure.aws.sns

import org.scalatest.freespec.AnyFreeSpec
import util.resource.Resource

class SnsPublisherTest extends AnyFreeSpec {

  "Test Publish" in {
    val snsClient = SnsClientProvider.snsClient

    Resource.using(snsClient) { snsClient =>
      val publisher = new SnsPublisher(snsClient)

      val topicArn = "arn:aws:sns:us-east-1:149536463306:S3FileUploadingSandbox"

      val message =
        """
          |{
          |  "message": "Hello, SNS!",
          |}
          |""".stripMargin

      val response = publisher.publish(topicArn, message)

      response match {
        case scala.util.Success(value) => assert(value.messageId() != null)
      }
    }
  }

}
