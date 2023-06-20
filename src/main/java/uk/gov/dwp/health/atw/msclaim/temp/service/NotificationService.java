package uk.gov.dwp.health.atw.msclaim.temp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import uk.gov.dwp.health.atw.msclaim.models.requests.EmailNotificationRequest;
import uk.gov.dwp.health.atw.msclaim.temp.messaging.CustomerNotificationPublisher;
import uk.gov.dwp.health.crypto.CryptoDataManager;
import uk.gov.dwp.health.crypto.CryptoMessage;
import uk.gov.dwp.health.crypto.exception.CryptoException;

@Service
public class NotificationService {
  final Logger log = LoggerFactory.getLogger(NotificationService.class);
  final CryptoDataManager cryptoDataManager;

  final CustomerNotificationPublisher customerNotificationPublisher;

  public NotificationService(
      CryptoDataManager cryptoDataManager,
      CustomerNotificationPublisher customerNotificationPublisher) {
    this.cryptoDataManager = cryptoDataManager;
    this.customerNotificationPublisher = customerNotificationPublisher;
  }

  public void sendEmail(EmailNotificationRequest req) {

    log.debug(req.toString());
    try {
      customerNotificationPublisher.publish(
          getNotificationCryptoBody(new ObjectMapper().writeValueAsString(req)));
    } catch (Exception e) {
      log.error(e.getMessage() + ", Unable to save email to queue for Notification Id: "
          + req.getNotificationId() + ", Notification Template: " + req.getNotificationTemplate());
    }
  }

  private String getNotificationCryptoBody(String payload) throws CryptoException {
    CryptoMessage message = cryptoDataManager.encrypt(payload);

    // create encrypted payload
    ObjectNode cryptoBody = new ObjectMapper().createObjectNode();
    cryptoBody.put("cipherkey", message.getKey());
    cryptoBody.put("ciphertext", message.getMessage());

    return cryptoBody.toString();
  }
}

