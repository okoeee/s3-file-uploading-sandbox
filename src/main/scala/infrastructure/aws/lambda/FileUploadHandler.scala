package infrastructure.aws.lambda

import com.amazonaws.services.lambda.runtime.{Context, RequestHandler}
import com.amazonaws.services.lambda.runtime.events.SNSEvent

class FileUploadHandler extends RequestHandler[SNSEvent, String] {

  override def handleRequest(snsEvent: SNSEvent, context: Context): String = {
    println("Received SNS event: " + snsEvent)

    val message = snsEvent.getRecords.get(0).getSNS.getMessage
    println("From SNS: " + message)
    message
  }

}
