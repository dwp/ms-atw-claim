package uk.gov.dwp.health.atw.msclaim.services;

import static uk.gov.dwp.health.atw.msclaim.models.enums.ClaimType.SUPPORT_WORKER;
import static uk.gov.dwp.health.atw.msclaim.models.enums.ClaimType.TRAVEL_TO_WORK;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uk.gov.dwp.health.atw.msclaim.models.exceptions.ClaimException;
import uk.gov.dwp.health.atw.msclaim.models.requests.ClaimRequest;
import uk.gov.dwp.health.atw.msclaim.models.requests.ContactInformationRequest;
import uk.gov.dwp.health.atw.msclaim.models.requests.EmailNotificationRequest;
import uk.gov.dwp.health.atw.msclaim.models.requests.SupportWorkerClaimRequest;
import uk.gov.dwp.health.atw.msclaim.models.requests.TravelToWorkClaimRequest;
import uk.gov.dwp.health.atw.msclaim.temp.service.NotificationService;
import uk.gov.dwp.health.crypto.exception.CryptoException;

@Service
public class EmailNotificationService {

  private static final String CLAIMANTS_FIRST_NAME = "CLAIMANTS_FIRST_NAME";
  private static final String CLAIMANTS_LAST_NAME = "CLAIMANTS_LAST_NAME";
  private static final String CONFIRMERS_FULL_NAME = "CONFIRMERS_FULL_NAME";
  private static final String CLAIM_NUMBER = "CLAIM_NUMBER";

  @Value("${service.send-notification.messaging.new-claim.queue}")
  String newClaimEmailNotificationQueue;

  @Value("${service.send-notification.messaging.topic}")
  private String emailNotificationTopic;

  @Value("${service.send-notification.client_api_key}")
  private String clientApiKey;

  @Value("${service.send-notification.send_workplace_contact_invite_template_id}")
  private String sendWorkplaceContactInviteTemplateId;

  @Value("${service.send-notification.send_workplace_contact_approval_template_id}")
  private String sendWorkplaceContactApprovalTemplateId;

  @Value("${service.send-notification.send_workplace_contact_rejection_template_id}")
  private String sendWorkplaceContactRejectionTemplateId;

  @Value("${service.send-notification.send_claim_submission_template_id}")
  private String sendClaimNonCountersignSubmissionTemplateId;

  @Value("${service.send-notification.send_claim_countersign_submission_template_id}")
  private String sendClaimCountersignSubmissionTemplateId;

  @Value("${service.send-notification.send_personal_information_update_template_id}")
  private String sendPersonalInformationUpdateTemplateId;

  @Value("${service.send-notification.claimant-email-override}")
  private String sendNotificationClaimantEmailOverride;

  static final String NOTIFICATION_TYPE = "email";

  final NotificationService notificationService;
  String workplaceContactEmail = "";

  public EmailNotificationService(
      NotificationService notificationService) {
    this.notificationService = notificationService;
  }

  @SneakyThrows
  public void notifyClaimantWithNoCountersignIsBeingProcessed(ClaimRequest response) {
    sendNotificationEmail(response, sendClaimNonCountersignSubmissionTemplateId, true, false);
  }

  @SneakyThrows
  public void notifyWorkplaceContactOfClaimToReview(ClaimRequest response) {
    sendNotificationEmail(response, sendWorkplaceContactInviteTemplateId, false, true);
  }

  @SneakyThrows
  public void notifyClaimantThatRequestHasBeenSentToWorkplaceContact(ClaimRequest response) {
    sendNotificationEmail(response, sendClaimCountersignSubmissionTemplateId, true, true);
  }

  @SneakyThrows
  public void notifyClaimantTheirClaimHasBeenApproved(ClaimRequest response) {
    sendNotificationEmail(response, sendWorkplaceContactApprovalTemplateId, true, true);
  }

  @SneakyThrows
  public void notifyClaimantTheirClaimHasBeenRejected(ClaimRequest response) {
    sendNotificationEmail(response, sendWorkplaceContactRejectionTemplateId, true, true);
  }

  @SneakyThrows
  public void notifyClaimantContactDetailsHaveChanged(ContactInformationRequest
                                                          contactInformationRequest) {
    sendNewContactNotificationEmail(contactInformationRequest,
        sendPersonalInformationUpdateTemplateId, true);
  }

  private void sendNotificationEmail(ClaimRequest claimRequest,
                                     String templateId,
                                     boolean claimantEmailAddress,
                                     boolean claimantAndWorkplaceContactDetails) {

    if (claimantEmailAddress && claimRequest.getClaimant().getEmailAddress() == null) {
      return;
    }

    EmailNotificationRequest emailNotificationRequest =
        createEmailNotificationRequest(templateId, claimRequest, claimantEmailAddress,
            claimantAndWorkplaceContactDetails);

    notificationService.sendEmail(emailNotificationRequest);
  }

  private void sendNewContactNotificationEmail(ContactInformationRequest contactInformationRequest,
                                               String templateId,
                                               boolean isClaimantEmailAddress) {

    if (isClaimantEmailAddress && contactInformationRequest.getNewContactInformation()
        .getEmailAddress() == null) {
      return;
    }

    EmailNotificationRequest emailNotificationRequest =
        createEmailNotificationRequest(templateId, contactInformationRequest,
            isClaimantEmailAddress);

    notificationService.sendEmail(emailNotificationRequest);
  }

  private EmailNotificationRequest createEmailNotificationRequest(
      String notificationTemplate,
      ClaimRequest claimRequest,
      boolean isClaimantEmailAddress,
      boolean claimantAndWorkplaceContactDetails
  ) {

    String notificationId = UUID.randomUUID().toString();

    Map<String, String> notificationData;

    if (claimantAndWorkplaceContactDetails) {
      notificationData = getAllEmailNotificationData(claimRequest);
    } else {
      notificationData = getClaimantDetails(claimRequest.getClaimant().getForename(),
          claimRequest.getClaimant().getSurname());
    }

    return EmailNotificationRequest.builder()
        .notificationId(notificationId)
        .notificationType(NOTIFICATION_TYPE)
        .notificationTemplate(notificationTemplate)
        .clientApiKey(clientApiKey)
        .notificationDestination(
            getEmailAddress(isClaimantEmailAddress, claimRequest.getClaimant().getEmailAddress(),
                workplaceContactEmail))
        .notificationData(notificationData)
        .build();
  }

  private EmailNotificationRequest createEmailNotificationRequest(
      String notificationTemplate,
      ContactInformationRequest contactInformationRequest,
      boolean isClaimantEmailAddress
  ) {

    String notificationId = UUID.randomUUID().toString();

    Map<String, String> notificationData;

    notificationData = getClaimantDetails(contactInformationRequest.getNewContactInformation()
        .getForename(), contactInformationRequest.getNewContactInformation().getSurname());

    return EmailNotificationRequest.builder()
        .notificationId(notificationId)
        .notificationType(NOTIFICATION_TYPE)
        .notificationTemplate(notificationTemplate)
        .clientApiKey(clientApiKey)
        .notificationDestination(
            getEmailAddress(isClaimantEmailAddress, contactInformationRequest
                .getNewContactInformation().getEmailAddress(), workplaceContactEmail))
        .notificationData(notificationData)
        .build();
  }

  private String getEmailAddress(boolean isClaimantEmailAddress, String claimantEmail,
                                 String workplaceContactEmail) {
    if (!isClaimantEmailAddress) {
      return workplaceContactEmail;
    }

    return sendNotificationClaimantEmailOverride != null
        && !sendNotificationClaimantEmailOverride.isEmpty()
        ? sendNotificationClaimantEmailOverride
        : claimantEmail;
  }

  private Map<String, String> getClaimantDetails(String forename, String surname) {
    Map<String, String> notificationData = new HashMap<>();
    notificationData.put(CLAIMANTS_FIRST_NAME, forename);
    notificationData.put(CLAIMANTS_LAST_NAME, surname);
    return notificationData;
  }

  private Map<String, String> getAllEmailNotificationData(ClaimRequest claimRequest) {
    Map<String, String> notificationData = getClaimantDetails(claimRequest.getClaimant()
        .getForename(), claimRequest.getClaimant().getSurname());

    switch (claimRequest.getClaimType()) {
      case TRAVEL_TO_WORK:
        TravelToWorkClaimRequest ttwClaimRequest = (TravelToWorkClaimRequest) claimRequest;
        notificationData.put(CONFIRMERS_FULL_NAME,
            ttwClaimRequest.getWorkplaceContact().getFullName());
        notificationData.put(CLAIM_NUMBER,
            getClaimNumber(TRAVEL_TO_WORK.label, ttwClaimRequest.getId()));
        workplaceContactEmail = ttwClaimRequest.getWorkplaceContact().getEmailAddress();
        break;
      case SUPPORT_WORKER:
        SupportWorkerClaimRequest swClaimRequest = (SupportWorkerClaimRequest) claimRequest;
        notificationData.put(CONFIRMERS_FULL_NAME,
            swClaimRequest.getWorkplaceContact().getFullName());
        notificationData.put(CLAIM_NUMBER,
            getClaimNumber(SUPPORT_WORKER.label, swClaimRequest.getId()));
        workplaceContactEmail = swClaimRequest.getWorkplaceContact().getEmailAddress();
        break;
      default:
        throw new IllegalArgumentException(
            "Claim type not supported " + claimRequest.getClaimType());
    }

    return notificationData;
  }

  private String getClaimNumber(String claimTypeLabel, long id) {

    return claimTypeLabel + "0".repeat(Math.max(0, 8 - Long.toString(id).length())) + id;
  }
}
