package uk.gov.dwp.health.atw.msclaim.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import uk.gov.dwp.health.atw.msclaim.models.messaging.SubmitToClaimBundlerEvent;
import uk.gov.dwp.health.atw.msclaim.models.messaging.SubmitToContactInformationBundlerEvent;
import uk.gov.dwp.health.atw.msclaim.models.messaging.SubmitToEmailNotificationBundlerEvent;
import uk.gov.dwp.health.integration.message.events.EventManager;

@Slf4j
@Component
public class ClaimPublisher {

  final EventManager eventManager;

  public ClaimPublisher(EventManager eventManager) {
    this.eventManager = eventManager;
  }

  public void publishToClaimBundler(SubmitToClaimBundlerEvent payload) {
    eventManager.send(payload);
    log.info("Sent to claim bundler");
  }

  public void publishToContactInformationBundler(SubmitToContactInformationBundlerEvent payload) {
    eventManager.send(payload);
    log.info("Sent to claim bundler");
  }

  public void publishToEmailNotification(SubmitToEmailNotificationBundlerEvent payload) {
    eventManager.send(payload);
    log.info("Sent to email notifications");
  }
}
