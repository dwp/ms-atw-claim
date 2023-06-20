package uk.gov.dwp.health.atw.msclaim.temp.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.messaging.MessageDeliveryException;
import uk.gov.dwp.health.atw.msclaim.models.requests.EmailNotificationRequest;
import uk.gov.dwp.health.atw.msclaim.temp.messaging.CustomerNotificationPublisher;
import uk.gov.dwp.health.atw.msclaim.temp.service.NotificationService;
import uk.gov.dwp.health.crypto.CryptoDataManager;
import uk.gov.dwp.health.crypto.CryptoMessage;

@ExtendWith(OutputCaptureExtension.class)
@SpringBootTest(classes = NotificationService.class)
class NotificationServiceTest {

  @Autowired
  private NotificationService notificationService;

  @MockBean
  CryptoDataManager cryptoDataManager;

  @MockBean
  CustomerNotificationPublisher customerNotificationPublisher;

  @Test
  void testSendNotification() throws Exception {

    EmailNotificationRequest request =
        EmailNotificationRequest.builder()
            .notificationId("123")
            .notificationData(Map.of("data", "data"))
            .notificationTemplate("template")
            .notificationType("email")
            .clientApiKey("clientApiKey")
            .notificationDestination("destination")
            .build();

    CryptoMessage cryptoMessage = new CryptoMessage();
    cryptoMessage.setMessage("message");
    cryptoMessage.setKey("key");

    when(cryptoDataManager.encrypt(new ObjectMapper().writeValueAsString(request))).thenReturn(cryptoMessage);

    notificationService.sendEmail(request);

    verify(customerNotificationPublisher, times(1)).publish("{\"cipherkey\":\"key\",\"ciphertext\":\"message\"}");

  }

  @Test
  void testSendNotificationException(CapturedOutput output) throws Exception {

    EmailNotificationRequest request =
        EmailNotificationRequest.builder()
            .notificationId("123")
            .notificationData(Map.of("data", "data"))
            .notificationTemplate("template")
            .notificationType("email")
            .clientApiKey("clientApiKey")
            .notificationDestination("destination")
            .build();

    CryptoMessage cryptoMessage = new CryptoMessage();
    cryptoMessage.setMessage("message");
    cryptoMessage.setKey("key");

    when(cryptoDataManager.encrypt(new ObjectMapper().writeValueAsString(request)))
        .thenReturn(cryptoMessage);

    doThrow(new MessageDeliveryException("error message")).when(customerNotificationPublisher)
        .publish(any(String.class));

    notificationService.sendEmail(request);

    Assertions.assertTrue(output.getOut()
        .contains("error message, Unable to save email to queue for Notification Id: 123, Notification Template: template"));
  }
}
