package uk.gov.dwp.health.atw.msclaim.temp.messaging;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomerNotificationPublisher {

  private AmazonSQS amazonSqs = null;

  @Value("${service.send-notification.messaging.new-claim.queue_url}")
  private String queueUrl;

  public void publish(String message) {
    if (amazonSqs == null) {
      amazonSqs = AmazonSQSClientBuilder.defaultClient();
    }
    log.info("Publishing message for notification");

    SendMessageRequest sendMsgRequest = new SendMessageRequest()
        .withQueueUrl(queueUrl)
        .withMessageBody(message);
    amazonSqs.sendMessage(sendMsgRequest);
  }
}
