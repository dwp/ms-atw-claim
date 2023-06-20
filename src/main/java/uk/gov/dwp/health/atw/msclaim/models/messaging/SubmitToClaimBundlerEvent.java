package uk.gov.dwp.health.atw.msclaim.models.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import uk.gov.dwp.health.atw.msclaim.models.ClaimReferenceNinoRequest;
import uk.gov.dwp.health.integration.message.events.Event;

@Slf4j
public class SubmitToClaimBundlerEvent extends Event {

  public SubmitToClaimBundlerEvent(String topic, String routingKey,
                                   ClaimReferenceNinoRequest claimReferenceRequest) {
    log.info("Creating {} to send to DRS. Topic {} and Routing Key {}",
        claimReferenceRequest.getClaimReference(),
        topic,
        routingKey);

    ObjectMapper mapper = new ObjectMapper();
    mapper.findAndRegisterModules();
    Map<String, Object> data = mapper.convertValue(claimReferenceRequest, Map.class);

    setRoutingKey(routingKey);
    setTopic(topic);
    setPayload(data);
  }
}
