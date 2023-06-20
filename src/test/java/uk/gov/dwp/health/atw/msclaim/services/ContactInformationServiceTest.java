package uk.gov.dwp.health.atw.msclaim.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static uk.gov.dwp.health.atw.msclaim.models.enums.ContactInformationStatus.AWAITING_UPLOAD;
import static uk.gov.dwp.health.atw.msclaim.models.enums.ContactInformationStatus.COMPLETED_DRS_UPLOAD;
import static uk.gov.dwp.health.atw.msclaim.models.enums.ContactInformationStatus.FAILED_DRS_UPLOAD;
import static uk.gov.dwp.health.atw.msclaim.models.enums.ContactInformationStatus.PROCESSING_UPLOAD;
import static uk.gov.dwp.health.atw.msclaim.models.enums.ContactInformationStatus.UPLOADED_TO_DOCUMENT_BATCH;
import static uk.gov.dwp.health.atw.msclaim.testData.ContactInformationTestData.contactInformationRequestWithCompletedUploadStatus2;
import static uk.gov.dwp.health.atw.msclaim.testData.ContactInformationTestData.contactInformationRequestWithDrsErrorStatus;
import static uk.gov.dwp.health.atw.msclaim.testData.ContactInformationTestData.contactInformationRequestWithDrsSuccessStatus;
import static uk.gov.dwp.health.atw.msclaim.testData.ContactInformationTestData.contactInformationRequestWithProcessingUploadStatus;
import static uk.gov.dwp.health.atw.msclaim.testData.ContactInformationTestData.contactInformationRequestWithProcessingUploadStatus2;
import static uk.gov.dwp.health.atw.msclaim.testData.ContactInformationTestData.contactInformationRequestWithUploadedToDocumentBatch;
import static uk.gov.dwp.health.atw.msclaim.testData.ContactInformationTestData.contactInformationRequestWithUploadedToDocumentBatchStatus;
import static uk.gov.dwp.health.atw.msclaim.testData.ContactInformationTestData.requestIdRequestContactInformation;
import static uk.gov.dwp.health.atw.msclaim.testData.ContactInformationTestData.submittedContactInformationRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.ContactInformationTestData.updateContactInformationRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import uk.gov.dwp.health.atw.msclaim.messaging.ClaimPublisher;
import uk.gov.dwp.health.atw.msclaim.models.exceptions.ClaimException;
import uk.gov.dwp.health.atw.msclaim.models.exceptions.ClaimHasWrongStatusException;
import uk.gov.dwp.health.atw.msclaim.models.exceptions.NoClaimRecordFoundException;
import uk.gov.dwp.health.atw.msclaim.models.requests.ContactInformationRequest;
import uk.gov.dwp.health.atw.msclaim.repositories.ContactInformationRepository;

@SpringBootTest(classes = ContactInformationService.class)
class ContactInformationServiceTest {

  @Autowired
  private ContactInformationService contactInformationService;

  @MockBean
  private ContactInformationRepository contactInformationRepository;

  @MockBean
  private ClaimPublisher claimPublisher;

  @MockBean
  private EmailNotificationService emailNotificationService;

  @Test
  @DisplayName("update contact information successful")
  void updateContactInformation() {

    when(contactInformationRepository.save(any(ContactInformationRequest.class)))
        .thenReturn(submittedContactInformationRequest);

    assertEquals(submittedContactInformationRequest, contactInformationService.updateContactInformation(updateContactInformationRequest));
    verify(emailNotificationService, times(1)).notifyClaimantContactDetailsHaveChanged(
        any(ContactInformationRequest.class));

    assertEquals(submittedContactInformationRequest, contactInformationService.updateContactInformation(updateContactInformationRequest));

    assertEquals(submittedContactInformationRequest.getNewContactInformation().getEmailAddress(),
        updateContactInformationRequest.getNewContactInformation().getEmailAddress());
    assertEquals(submittedContactInformationRequest.getNewContactInformation().getForename(),
        updateContactInformationRequest.getNewContactInformation().getForename());
    assertEquals(submittedContactInformationRequest.getNewContactInformation().getSurname(),
        updateContactInformationRequest.getNewContactInformation().getSurname());
  }

  @Test
  @DisplayName("retrieve contact information with accessToWorkNumber and id successful")
  void retrieveContactInformationAccessToWorkNumberAndId() {

    when(contactInformationRepository.findContactInformationById(anyString()))
        .thenReturn(submittedContactInformationRequest);

    assertEquals(submittedContactInformationRequest, contactInformationService.retrieveContactInformation(
        requestIdRequestContactInformation));
  }

  @Test
  @DisplayName("change status to processing upload successful")
  void changeStatusToProcessingUpload() throws ClaimException {
    submittedContactInformationRequest.setContactInformationStatus(AWAITING_UPLOAD);

    ContactInformationRequest spyContactInformation = spy(submittedContactInformationRequest);

    when(contactInformationRepository.findContactInformationById(UUID))
        .thenReturn(spyContactInformation);

    when(contactInformationRepository.save(any(ContactInformationRequest.class)))
        .thenReturn(contactInformationRequestWithProcessingUploadStatus);

    contactInformationService.changeStatusToProcessingUpload(
        requestIdRequestContactInformation);

    verify(spyContactInformation, times(1)).setContactInformationStatus(PROCESSING_UPLOAD);
    verify(contactInformationRepository, times(1))
        .findContactInformationById(UUID);
    verify(contactInformationRepository, times(1)).save(contactInformationRequestWithProcessingUploadStatus);
  }

  @Test
  @DisplayName("Throw exception when trying to change claim status to PROCESSING_UPLOAD")
  void changeStatusToProcessingUploadThrowsException() {
    when(contactInformationRepository.findContactInformationById(UUID))
        .thenReturn(contactInformationRequestWithProcessingUploadStatus);

    ClaimHasWrongStatusException thrown =
        assertThrows(ClaimHasWrongStatusException.class,
            () -> contactInformationService.changeStatusToProcessingUpload(
                requestIdRequestContactInformation));

    assertEquals("Cannot change the contact information status to PROCESSING_UPLOAD",
        thrown.getErrorMessage());

    verify(contactInformationRepository, times(1))
        .findContactInformationById(UUID);
    verify(contactInformationRepository, never()).save(any(ContactInformationRequest.class));
  }

  @Test
  @DisplayName("change status to processing upload has no claim number found")
  void changeStatusToProcessingUploadNoClaimNumberFound() {
    when(contactInformationRepository.findContactInformationById(UUID))
        .thenReturn(null);
    NoClaimRecordFoundException thrown = assertThrows(NoClaimRecordFoundException.class,
        () -> contactInformationService.changeStatusToProcessingUpload(
            requestIdRequestContactInformation));

    assertEquals("No claim record found", thrown.getErrorMessage());

    verify(contactInformationRepository, times(1))
        .findContactInformationById(UUID);
    verify(contactInformationRepository, never()).save(any(ContactInformationRequest.class));
  }

  @Test
  @DisplayName("change status to uploaded to document batch upload successful")
  void changeStatusToUploadedToDocumentBatch() throws ClaimException {

    ContactInformationRequest spyContactInformation = spy(contactInformationRequestWithProcessingUploadStatus2);

    when(contactInformationRepository.findContactInformationById(anyString()))
        .thenReturn(spyContactInformation);

    when(contactInformationRepository.save(any(ContactInformationRequest.class)))
        .thenReturn(contactInformationRequestWithUploadedToDocumentBatchStatus);

    contactInformationService.changeStatusToUploadedToDocumentBatch(
        requestIdRequestContactInformation);

    verify(spyContactInformation, times(1)).setContactInformationStatus(UPLOADED_TO_DOCUMENT_BATCH);
    verify(contactInformationRepository, times(1))
        .findContactInformationById(anyString());
    verify(contactInformationRepository, times(1)).save(
        contactInformationRequestWithUploadedToDocumentBatchStatus);
  }

  @Test
  @DisplayName("Throw exception when trying to change claim status to UPLOADED_TO_DOCUMENT_BATCH")
  void changeStatusToUploadedToDocumentBatchThrowsException() {
    when(contactInformationRepository.findContactInformationById(UUID))
        .thenReturn(contactInformationRequestWithCompletedUploadStatus2);

    ClaimHasWrongStatusException thrown =
        assertThrows(ClaimHasWrongStatusException.class,
            () -> contactInformationService.changeStatusToUploadedToDocumentBatch(
                requestIdRequestContactInformation));

    assertEquals("Cannot change the contact information status to UPLOADED_TO_DOCUMENT_BATCH",
        thrown.getErrorMessage());

    verify(contactInformationRepository, times(1))
        .findContactInformationById(UUID);
    verify(contactInformationRepository, never()).save(any(ContactInformationRequest.class));
  }

  @Test
  @DisplayName("change status to processing upload has no claim number found")
  void changeStatusToUploadedToDocumentBatcNoClaimNumberFound() {
    when(contactInformationRepository.findContactInformationById(UUID))
        .thenReturn(null);
    NoClaimRecordFoundException thrown = assertThrows(NoClaimRecordFoundException.class,
        () -> contactInformationService.changeStatusToUploadedToDocumentBatch(
            requestIdRequestContactInformation));

    assertEquals("No claim record found", thrown.getErrorMessage());

    verify(contactInformationRepository, times(1))
        .findContactInformationById(anyString());
    verify(contactInformationRepository, never()).save(any());
  }

  @Test
  @DisplayName("change status to completed upload successful")
  void changeStatusToCompletedUpload() throws ClaimException {

    ContactInformationRequest spyContactInformation = spy(contactInformationRequestWithUploadedToDocumentBatch);

    when(contactInformationRepository.findContactInformationById(anyString()))
        .thenReturn(spyContactInformation);

    when(contactInformationRepository.save(any(ContactInformationRequest.class)))
        .thenReturn(contactInformationRequestWithDrsSuccessStatus);

    contactInformationService.changeStatusCompletedUploaded(
        requestIdRequestContactInformation);

    verify(spyContactInformation, times(1)).setContactInformationStatus(COMPLETED_DRS_UPLOAD);
    verify(contactInformationRepository, times(1))
        .findContactInformationById(anyString());
    verify(contactInformationRepository, times(1)).save(
        contactInformationRequestWithDrsSuccessStatus);
  }

  @Test
  @DisplayName("change status to DRS upload failed")
  void changeStatusToDrsUploadFailure() throws ClaimException {

    ContactInformationRequest spyContactInformation = spy(contactInformationRequestWithUploadedToDocumentBatch);

    when(contactInformationRepository.findContactInformationById(anyString()))
        .thenReturn(spyContactInformation);

    when(contactInformationRepository.save(any(ContactInformationRequest.class)))
        .thenReturn(contactInformationRequestWithDrsErrorStatus);

    contactInformationService.changeStatusFailedDrsUploads(
        requestIdRequestContactInformation);

    verify(spyContactInformation, times(1)).setContactInformationStatus(FAILED_DRS_UPLOAD);
    verify(contactInformationRepository, times(1))
        .findContactInformationById(anyString());
    verify(contactInformationRepository, times(1)).save(
        contactInformationRequestWithDrsErrorStatus);
  }
}