package uk.gov.dwp.health.atw.msclaim.messaging;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.UUID;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import uk.gov.dwp.health.atw.msclaim.models.ClaimReferenceNinoRequest;
import uk.gov.dwp.health.atw.msclaim.models.RequestIdRequest;
import uk.gov.dwp.health.atw.msclaim.models.messaging.SubmitToClaimBundlerEvent;
import uk.gov.dwp.health.atw.msclaim.models.messaging.SubmitToContactInformationBundlerEvent;
import uk.gov.dwp.health.atw.msclaim.models.messaging.SubmitToEmailNotificationBundlerEvent;
import uk.gov.dwp.health.atw.msclaim.models.requests.EmailNotificationRequest;
import uk.gov.dwp.health.integration.message.events.EventManager;

@SpringBootTest(classes = ClaimPublisher.class)
class ClaimPublisherTest {

  @Autowired
  ClaimPublisher claimPublisher;

  @MockBean
  private EventManager eventManager;

  @Test
  @DisplayName("submit Equipment And Adaptation claim successfully")
  void publishToClaimBundler() {

    doNothing().when(eventManager).send(any(SubmitToClaimBundlerEvent.class));

    ClaimReferenceNinoRequest claimReferenceRequest = ClaimReferenceNinoRequest.builder()
        .claimReference("EA123456789")
        .nino("AB123456C")
        .build();

    SubmitToClaimBundlerEvent event =
        new SubmitToClaimBundlerEvent("topic", "queue", claimReferenceRequest);

    claimPublisher.publishToClaimBundler(event
    );

    verify(eventManager, times(1)).send(event);
  }

  @Test
  @DisplayName("submit update contact information successfully")
  void publishToContactInformationBundler() {

    doNothing().when(eventManager).send(any(SubmitToClaimBundlerEvent.class));

    RequestIdRequest requestIdRequest = RequestIdRequest.builder()
        .requestId(UUID)
        .build();

    SubmitToContactInformationBundlerEvent event =
        new SubmitToContactInformationBundlerEvent("topic", "queue", requestIdRequest);

    claimPublisher.publishToContactInformationBundler(event);

    verify(eventManager, times(1)).send(event);
  }

  @Test
  @DisplayName("submit to email notification")
  void publishToEmailNotification() {

    doNothing().when(eventManager).send(any(SubmitToEmailNotificationBundlerEvent.class));

    EmailNotificationRequest emailNotificationForSendWorkplaceContactInvite = EmailNotificationRequest.builder()
        .notificationId("1234")
        .notificationType("email")
        .notificationTemplate("1")
        .clientApiKey("abcssasdwe")
        .notificationDestination("email@email.com")
        .notificationData(Map.of("CLAIMANTS_FIRST_NAME", "John",
            "CLAIMANTS_LAST_NAME", "Smith",
            "CONFIRMERS_FULL_NAME", "David Jones",
            "CLAIM_NUMBER", "EA123456789"))
        .build();

    SubmitToEmailNotificationBundlerEvent event =
        new SubmitToEmailNotificationBundlerEvent("topic", "queue", emailNotificationForSendWorkplaceContactInvite);

    claimPublisher.publishToEmailNotification(event);

    verify(eventManager, times(1)).send(event);
  }
}
