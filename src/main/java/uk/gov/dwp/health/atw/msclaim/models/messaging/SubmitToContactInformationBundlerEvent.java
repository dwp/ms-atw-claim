package uk.gov.dwp.health.atw.msclaim.models.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import uk.gov.dwp.health.atw.msclaim.models.RequestIdRequest;
import uk.gov.dwp.health.integration.message.events.Event;

@Slf4j
public class SubmitToContactInformationBundlerEvent extends Event {

  public SubmitToContactInformationBundlerEvent(String topic, String routingKey,
                                                RequestIdRequest requestIdRequest) {
    log.info("Creating {} to send to DRS. Topic {} and Routing Key {}",
        requestIdRequest.getRequestId(), topic, routingKey);

    ObjectMapper mapper = new ObjectMapper();
    mapper.findAndRegisterModules();
    Map<String, Object> data = mapper.convertValue(requestIdRequest, Map.class);

    setRoutingKey(routingKey);
    setTopic(topic);
    setPayload(data);
  }
}
