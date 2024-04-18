package uk.gov.dwp.health.atw.msclaim.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static uk.gov.dwp.health.atw.msclaim.testData.ContactInformationTestData.contactInformationRequestWithUploadedToDocumentBatchStatus;
import static uk.gov.dwp.health.atw.msclaim.testData.ContactInformationTestData.submittedContactInformationRequestWithNullEmail;
import static uk.gov.dwp.health.atw.msclaim.testData.EquipmentOrAdaptationTestData.submittedEquipmentOrAdaptationRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.SupportWorkerTestData.submittedSupportWorkerClaimForTwoMonthsRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelInWorkTestData.submittedForTwoMonthsTravelInWorkEmployedClaimRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelToWorkTestData.submittedLiftForTwoMonthsTravelToWorkClaimRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelToWorkTestData.submittedLiftWithNoClaimantEmail;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;
import uk.gov.dwp.health.atw.msclaim.models.requests.EmailNotificationRequest;
import uk.gov.dwp.health.atw.msclaim.temp.service.NotificationService;

@SpringBootTest(classes = EmailNotificationService.class)
class EmailNotificationServiceTest {

  @Autowired
  EmailNotificationService emailNotificationService;

  @MockBean
  private NotificationService notificationService;

  @Test
  @DisplayName("send notification email to claimant with no counter sign required")
  void sendNotificationEmailToClaimantWithNoCounterSignRequired() {

    ArgumentCaptor<EmailNotificationRequest> argument =
        ArgumentCaptor.forClass(EmailNotificationRequest.class);

    emailNotificationService.notifyClaimantWithNoCountersignIsBeingProcessed(submittedEquipmentOrAdaptationRequest);
    verify(notificationService, times(1)).sendEmail(argument.capture());

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.findAndRegisterModules();

    EmailNotificationRequest actualEmailNotificationRequest =
        objectMapper.convertValue(argument.getValue(), EmailNotificationRequest.class);

    assertEquals("email", actualEmailNotificationRequest.getNotificationType());
    assertEquals("4", actualEmailNotificationRequest.getNotificationTemplate());
    assertEquals(submittedLiftForTwoMonthsTravelToWorkClaimRequest.getClaimant().getEmailAddress(), actualEmailNotificationRequest.getNotificationDestination());
    assertEquals(submittedLiftForTwoMonthsTravelToWorkClaimRequest.getClaimant().getForename(), actualEmailNotificationRequest.getNotificationData().get("CLAIMANTS_FIRST_NAME"));
    assertEquals(submittedLiftForTwoMonthsTravelToWorkClaimRequest.getClaimant().getSurname(), actualEmailNotificationRequest.getNotificationData().get("CLAIMANTS_LAST_NAME"));
    assertNull(actualEmailNotificationRequest.getNotificationData().get("CONFIRMERS_FULL_NAME"));
    assertNull(actualEmailNotificationRequest.getNotificationData().get("CLAIM_NUMBER"));
  }

  @Test
  @DisplayName("send notification email to claimant (claim email override enabled)")
  void sendNotificationEmailClaimEmailOverride() {

    ArgumentCaptor<EmailNotificationRequest> argument =
        ArgumentCaptor.forClass(EmailNotificationRequest.class);
    EmailNotificationService spyEmailNotificationService = spy(emailNotificationService);

    ReflectionTestUtils.setField(spyEmailNotificationService, "sendNotificationClaimantEmailOverride",
        "override@email.com");

    spyEmailNotificationService.notifyClaimantWithNoCountersignIsBeingProcessed(submittedEquipmentOrAdaptationRequest);
    verify(notificationService, times(1)).sendEmail(argument.capture());

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.findAndRegisterModules();

    EmailNotificationRequest actualEmailNotificationRequest =
        objectMapper.convertValue(argument.getValue(), EmailNotificationRequest.class);

    assertEquals("email", actualEmailNotificationRequest.getNotificationType());
    assertEquals("4", actualEmailNotificationRequest.getNotificationTemplate());
    assertEquals("override@email.com", actualEmailNotificationRequest.getNotificationDestination());
    assertEquals(submittedLiftForTwoMonthsTravelToWorkClaimRequest.getClaimant().getForename(), actualEmailNotificationRequest.getNotificationData().get("CLAIMANTS_FIRST_NAME"));
    assertEquals(submittedLiftForTwoMonthsTravelToWorkClaimRequest.getClaimant().getSurname(), actualEmailNotificationRequest.getNotificationData().get("CLAIMANTS_LAST_NAME"));
    assertNull(actualEmailNotificationRequest.getNotificationData().get("CONFIRMERS_FULL_NAME"));
    assertNull(actualEmailNotificationRequest.getNotificationData().get("CLAIM_NUMBER"));
  }

  @Test
  @DisplayName("send notification email to claimant (claim email override is null)")
  void sendNotificationEmailClaimEmailOverrideIsNull() {

    ArgumentCaptor<EmailNotificationRequest> argument =
        ArgumentCaptor.forClass(EmailNotificationRequest.class);
    EmailNotificationService spyEmailNotificationService = spy(emailNotificationService);

    ReflectionTestUtils.setField(spyEmailNotificationService, "sendNotificationClaimantEmailOverride",
        null);

    spyEmailNotificationService.notifyClaimantWithNoCountersignIsBeingProcessed(submittedEquipmentOrAdaptationRequest);
    verify(notificationService, times(1)).sendEmail(argument.capture());

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.findAndRegisterModules();

    EmailNotificationRequest actualEmailNotificationRequest =
        objectMapper.convertValue(argument.getValue(), EmailNotificationRequest.class);

    assertEquals("email", actualEmailNotificationRequest.getNotificationType());
    assertEquals("4", actualEmailNotificationRequest.getNotificationTemplate());
    assertEquals(submittedLiftForTwoMonthsTravelToWorkClaimRequest.getClaimant().getEmailAddress(), actualEmailNotificationRequest.getNotificationDestination());
    assertEquals(submittedLiftForTwoMonthsTravelToWorkClaimRequest.getClaimant().getForename(), actualEmailNotificationRequest.getNotificationData().get("CLAIMANTS_FIRST_NAME"));
    assertEquals(submittedLiftForTwoMonthsTravelToWorkClaimRequest.getClaimant().getSurname(), actualEmailNotificationRequest.getNotificationData().get("CLAIMANTS_LAST_NAME"));
    assertNull(actualEmailNotificationRequest.getNotificationData().get("CONFIRMERS_FULL_NAME"));
    assertNull(actualEmailNotificationRequest.getNotificationData().get("CLAIM_NUMBER"));
  }

  @Test
  @DisplayName("send notification email to workplace contact to review claim for Support Worker with id in double digits")
  void sendNotificationEmailToWorkplaceContactToReviewSupportWorkerClaim() {

    ArgumentCaptor<EmailNotificationRequest> argument =
            ArgumentCaptor.forClass(EmailNotificationRequest.class);

    emailNotificationService.notifyWorkplaceContactOfClaimToReview(submittedSupportWorkerClaimForTwoMonthsRequest);

    verify(notificationService, times(1)).sendEmail(argument.capture());

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.findAndRegisterModules();

    EmailNotificationRequest actualEmailNotificationRequest =
            objectMapper.convertValue(argument.getValue(), EmailNotificationRequest.class);

    assertEquals("email", actualEmailNotificationRequest.getNotificationType());
    assertEquals("1", actualEmailNotificationRequest.getNotificationTemplate());
    assertEquals(submittedSupportWorkerClaimForTwoMonthsRequest.getWorkplaceContact().getEmailAddress(), actualEmailNotificationRequest.getNotificationDestination());
    assertEquals(submittedSupportWorkerClaimForTwoMonthsRequest.getClaimant().getForename(), actualEmailNotificationRequest.getNotificationData().get("CLAIMANTS_FIRST_NAME"));
    assertEquals(submittedSupportWorkerClaimForTwoMonthsRequest.getClaimant().getSurname(), actualEmailNotificationRequest.getNotificationData().get("CLAIMANTS_LAST_NAME"));
    assertEquals(submittedSupportWorkerClaimForTwoMonthsRequest.getWorkplaceContact().getFullName(), actualEmailNotificationRequest.getNotificationData().get("CONFIRMERS_FULL_NAME"));
    assertEquals("SW000000" + submittedSupportWorkerClaimForTwoMonthsRequest.getId(), actualEmailNotificationRequest.getNotificationData().get("CLAIM_NUMBER"));
  }

  @Test
  @DisplayName("send notification email to workplace contact to review claim for Travel In Work")
  void sendNotificationEmailToWorkplaceContactToReviewTravelInWorkClaim() {

    ArgumentCaptor<EmailNotificationRequest> argument =
            ArgumentCaptor.forClass(EmailNotificationRequest.class);

    emailNotificationService.notifyWorkplaceContactOfClaimToReview(submittedForTwoMonthsTravelInWorkEmployedClaimRequest);

    verify(notificationService, times(1)).sendEmail(argument.capture());

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.findAndRegisterModules();

    EmailNotificationRequest actualEmailNotificationRequest =
            objectMapper.convertValue(argument.getValue(), EmailNotificationRequest.class);

    assertEquals("email", actualEmailNotificationRequest.getNotificationType());
    assertEquals("1", actualEmailNotificationRequest.getNotificationTemplate());
    assertEquals(submittedForTwoMonthsTravelInWorkEmployedClaimRequest.getWorkplaceContact().getEmailAddress(), actualEmailNotificationRequest.getNotificationDestination());
    assertEquals(submittedForTwoMonthsTravelInWorkEmployedClaimRequest.getClaimant().getForename(), actualEmailNotificationRequest.getNotificationData().get("CLAIMANTS_FIRST_NAME"));
    assertEquals(submittedForTwoMonthsTravelInWorkEmployedClaimRequest.getClaimant().getSurname(), actualEmailNotificationRequest.getNotificationData().get("CLAIMANTS_LAST_NAME"));
    assertEquals(submittedForTwoMonthsTravelInWorkEmployedClaimRequest.getWorkplaceContact().getFullName(), actualEmailNotificationRequest.getNotificationData().get("CONFIRMERS_FULL_NAME"));
    assertEquals("TIW000000" + submittedForTwoMonthsTravelInWorkEmployedClaimRequest.getId(), actualEmailNotificationRequest.getNotificationData().get("CLAIM_NUMBER"));
  }

  @Test
  @DisplayName("send notification email to claimant that an email has been sent to the workplace contact")
  void sendNotificationEmailToClaimantThatRequestHasBeenSentToWorkplaceContact() {

    ArgumentCaptor<EmailNotificationRequest> argument =
        ArgumentCaptor.forClass(EmailNotificationRequest.class);

    emailNotificationService.notifyClaimantThatRequestHasBeenSentToWorkplaceContact(submittedLiftForTwoMonthsTravelToWorkClaimRequest);
    verify(notificationService, times(1)).sendEmail(argument.capture());

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.findAndRegisterModules();

    EmailNotificationRequest actualEmailNotificationRequest =
        objectMapper.convertValue(argument.getValue(), EmailNotificationRequest.class);

    assertEquals("email", actualEmailNotificationRequest.getNotificationType());
    assertEquals("5", actualEmailNotificationRequest.getNotificationTemplate());
    assertEquals(submittedLiftForTwoMonthsTravelToWorkClaimRequest.getClaimant().getEmailAddress(), actualEmailNotificationRequest.getNotificationDestination());
    assertEquals(submittedLiftForTwoMonthsTravelToWorkClaimRequest.getClaimant().getForename(), actualEmailNotificationRequest.getNotificationData().get("CLAIMANTS_FIRST_NAME"));
    assertEquals(submittedLiftForTwoMonthsTravelToWorkClaimRequest.getClaimant().getSurname(), actualEmailNotificationRequest.getNotificationData().get("CLAIMANTS_LAST_NAME"));
    assertEquals(submittedLiftForTwoMonthsTravelToWorkClaimRequest.getWorkplaceContact().getFullName(), actualEmailNotificationRequest.getNotificationData().get("CONFIRMERS_FULL_NAME"));
    assertEquals( "TW0000000" + submittedLiftForTwoMonthsTravelToWorkClaimRequest.getId(), actualEmailNotificationRequest.getNotificationData().get("CLAIM_NUMBER"));
  }

  @Test
  @DisplayName("send notification email to claimant that an email has been sent to the workplace contact - TIW")
  void sendNotificationEmailToClaimantThatRequestHasBeenSentToWorkplaceContactForTiW() {

    ArgumentCaptor<EmailNotificationRequest> argument =
        ArgumentCaptor.forClass(EmailNotificationRequest.class);

    emailNotificationService.notifyClaimantThatRequestHasBeenSentToWorkplaceContact(submittedForTwoMonthsTravelInWorkEmployedClaimRequest);
    verify(notificationService, times(1)).sendEmail(argument.capture());

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.findAndRegisterModules();

    EmailNotificationRequest actualEmailNotificationRequest =
        objectMapper.convertValue(argument.getValue(), EmailNotificationRequest.class);

    assertEquals("email", actualEmailNotificationRequest.getNotificationType());
    assertEquals("5", actualEmailNotificationRequest.getNotificationTemplate());
    assertEquals(submittedForTwoMonthsTravelInWorkEmployedClaimRequest.getClaimant().getEmailAddress(), actualEmailNotificationRequest.getNotificationDestination());
    assertEquals(submittedForTwoMonthsTravelInWorkEmployedClaimRequest.getClaimant().getForename(), actualEmailNotificationRequest.getNotificationData().get("CLAIMANTS_FIRST_NAME"));
    assertEquals(submittedForTwoMonthsTravelInWorkEmployedClaimRequest.getClaimant().getSurname(), actualEmailNotificationRequest.getNotificationData().get("CLAIMANTS_LAST_NAME"));
    assertEquals(submittedForTwoMonthsTravelInWorkEmployedClaimRequest.getWorkplaceContact().getFullName(), actualEmailNotificationRequest.getNotificationData().get("CONFIRMERS_FULL_NAME"));
    assertEquals( "TIW000000" + submittedForTwoMonthsTravelInWorkEmployedClaimRequest.getId(), actualEmailNotificationRequest.getNotificationData().get("CLAIM_NUMBER"));
  }

  @Test
  @DisplayName("send notification email to claimant that their TW Claim has been approved")
  void sendNotificationEmailToClaimantThatTheirEmployedTravelToWorkClaimHasBeenApproved() {

    ArgumentCaptor<EmailNotificationRequest> argument =
        ArgumentCaptor.forClass(EmailNotificationRequest.class);

    emailNotificationService.notifyClaimantTheirClaimHasBeenApproved(submittedLiftForTwoMonthsTravelToWorkClaimRequest);
    verify(notificationService, times(1)).sendEmail(argument.capture());

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.findAndRegisterModules();

    EmailNotificationRequest actualEmailNotificationRequest =
        objectMapper.convertValue(argument.getValue(), EmailNotificationRequest.class);

    assertEquals("email", actualEmailNotificationRequest.getNotificationType());
    assertEquals("2", actualEmailNotificationRequest.getNotificationTemplate());
    assertEquals(submittedLiftForTwoMonthsTravelToWorkClaimRequest.getClaimant().getEmailAddress(), actualEmailNotificationRequest.getNotificationDestination());
    assertEquals(submittedLiftForTwoMonthsTravelToWorkClaimRequest.getClaimant().getForename(), actualEmailNotificationRequest.getNotificationData().get("CLAIMANTS_FIRST_NAME"));
    assertEquals(submittedLiftForTwoMonthsTravelToWorkClaimRequest.getClaimant().getSurname(), actualEmailNotificationRequest.getNotificationData().get("CLAIMANTS_LAST_NAME"));
    assertEquals(submittedLiftForTwoMonthsTravelToWorkClaimRequest.getWorkplaceContact().getFullName(), actualEmailNotificationRequest.getNotificationData().get("CONFIRMERS_FULL_NAME"));
    assertEquals( "TW0000000" + submittedLiftForTwoMonthsTravelToWorkClaimRequest.getId(), actualEmailNotificationRequest.getNotificationData().get("CLAIM_NUMBER"));
  }

  @Test
  @DisplayName("send notification email to claimant that their claim has been rejected")
  void sendNotificationEmailToClaimantThatTheirClaimHasBeenRejected() {

    ArgumentCaptor<EmailNotificationRequest> argument =
        ArgumentCaptor.forClass(EmailNotificationRequest.class);

    emailNotificationService.notifyClaimantTheirClaimHasBeenRejected(submittedLiftForTwoMonthsTravelToWorkClaimRequest);
    verify(notificationService, times(1)).sendEmail(argument.capture());

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.findAndRegisterModules();

    EmailNotificationRequest actualEmailNotificationRequest =
        objectMapper.convertValue(argument.getValue(), EmailNotificationRequest.class);

    assertEquals("email", actualEmailNotificationRequest.getNotificationType());
    assertEquals("3", actualEmailNotificationRequest.getNotificationTemplate());
    assertEquals(submittedLiftForTwoMonthsTravelToWorkClaimRequest.getClaimant().getEmailAddress(), actualEmailNotificationRequest.getNotificationDestination());
    assertEquals(submittedLiftForTwoMonthsTravelToWorkClaimRequest.getClaimant().getForename(), actualEmailNotificationRequest.getNotificationData().get("CLAIMANTS_FIRST_NAME"));
    assertEquals(submittedLiftForTwoMonthsTravelToWorkClaimRequest.getClaimant().getSurname(), actualEmailNotificationRequest.getNotificationData().get("CLAIMANTS_LAST_NAME"));
    assertEquals(submittedLiftForTwoMonthsTravelToWorkClaimRequest.getWorkplaceContact().getFullName(), actualEmailNotificationRequest.getNotificationData().get("CONFIRMERS_FULL_NAME"));
    assertEquals( "TW0000000" + submittedLiftForTwoMonthsTravelToWorkClaimRequest.getId(), actualEmailNotificationRequest.getNotificationData().get("CLAIM_NUMBER"));
  }

  @Test
  @DisplayName("send notification email to claimant without claimant email set")
  void sendNotificationEmailToClaimantWithClaimantEmailSet() {

    ArgumentCaptor<EmailNotificationRequest> argument =
        ArgumentCaptor.forClass(EmailNotificationRequest.class);

    emailNotificationService.notifyClaimantTheirClaimHasBeenApproved(submittedLiftWithNoClaimantEmail);

    verify(notificationService, never()).sendEmail(argument.capture());
  }

  @Test
  @DisplayName("send notification email to contact after successful contact information changed")
  void sendNotificationEmailToNotifyContactDetailsHaveChanged() {

    ArgumentCaptor<EmailNotificationRequest> argument =
        ArgumentCaptor.forClass(EmailNotificationRequest.class);

    emailNotificationService.notifyClaimantContactDetailsHaveChanged(contactInformationRequestWithUploadedToDocumentBatchStatus);
    verify(notificationService, times(1)).sendEmail(argument.capture());

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.findAndRegisterModules();

    EmailNotificationRequest actualEmailNotificationRequest =
        objectMapper.convertValue(argument.getValue(), EmailNotificationRequest.class);

    assertEquals("email", actualEmailNotificationRequest.getNotificationType());
    assertEquals("6", actualEmailNotificationRequest.getNotificationTemplate());
    assertEquals(contactInformationRequestWithUploadedToDocumentBatchStatus.getNewContactInformation().getEmailAddress(), actualEmailNotificationRequest.getNotificationDestination() );
    assertEquals(contactInformationRequestWithUploadedToDocumentBatchStatus.getNewContactInformation().getSurname(), actualEmailNotificationRequest.getNotificationData().get("CLAIMANTS_LAST_NAME"));
    assertEquals(contactInformationRequestWithUploadedToDocumentBatchStatus.getNewContactInformation().getForename(), actualEmailNotificationRequest.getNotificationData().get("CLAIMANTS_FIRST_NAME"));
  }

  @Test
  @DisplayName("Failed to send notification email to contact when email is null")
  void sendNotificationEmailToNotifyContactDetailsHaveChangedWithNullEmail() {

    ArgumentCaptor<EmailNotificationRequest> argument =
        ArgumentCaptor.forClass(EmailNotificationRequest.class);

    emailNotificationService.notifyClaimantContactDetailsHaveChanged(submittedContactInformationRequestWithNullEmail);
    verify(notificationService, times(0)).sendEmail(argument.capture());
  }

  @Test
  @DisplayName("send notification email to claimant with claimrequest object")
  void testSendNotificationEmailWithClaimRequest() {

    ArgumentCaptor<EmailNotificationRequest> argument =
        ArgumentCaptor.forClass(EmailNotificationRequest.class);

    emailNotificationService.notifyClaimantTheirClaimHasBeenApproved(submittedLiftForTwoMonthsTravelToWorkClaimRequest);

    verify(notificationService, times(1)).sendEmail(argument.capture());
  }
}