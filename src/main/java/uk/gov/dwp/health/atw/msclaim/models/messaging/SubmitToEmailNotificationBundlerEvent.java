package uk.gov.dwp.health.atw.msclaim.models.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import uk.gov.dwp.health.atw.msclaim.models.requests.EmailNotificationRequest;
import uk.gov.dwp.health.integration.message.events.Event;

@Slf4j
public class SubmitToEmailNotificationBundlerEvent extends Event {

  public SubmitToEmailNotificationBundlerEvent(String topic, String routingKey,
                                               EmailNotificationRequest emailNotificationRequest) {
    log.info("Creating notification with Id {}, notification type {}, notification template {} to "
            + "send to Email Notification. Topic {} and Routing Key {}",
        emailNotificationRequest.getNotificationId(),
        emailNotificationRequest.getNotificationType(),
        emailNotificationRequest.getNotificationTemplate(),
        topic,
        routingKey);

    ObjectMapper mapper = new ObjectMapper();
    mapper.findAndRegisterModules();
    Map<String, Object> data = mapper.convertValue(emailNotificationRequest, Map.class);

    setRoutingKey(routingKey);
    setTopic(topic);
    setPayload(data);
  }
}
