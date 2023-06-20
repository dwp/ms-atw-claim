package uk.gov.dwp.health.atw.msclaim.services;

import static uk.gov.dwp.health.atw.msclaim.models.enums.ContactInformationStatus.AWAITING_UPLOAD;
import static uk.gov.dwp.health.atw.msclaim.models.enums.ContactInformationStatus.COMPLETED_DRS_UPLOAD;
import static uk.gov.dwp.health.atw.msclaim.models.enums.ContactInformationStatus.FAILED_DRS_UPLOAD;
import static uk.gov.dwp.health.atw.msclaim.models.enums.ContactInformationStatus.PROCESSING_UPLOAD;
import static uk.gov.dwp.health.atw.msclaim.models.enums.ContactInformationStatus.UPLOADED_TO_DOCUMENT_BATCH;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uk.gov.dwp.health.atw.msclaim.messaging.ClaimPublisher;
import uk.gov.dwp.health.atw.msclaim.models.RequestIdRequest;
import uk.gov.dwp.health.atw.msclaim.models.enums.ContactInformationStatus;
import uk.gov.dwp.health.atw.msclaim.models.exceptions.ClaimException;
import uk.gov.dwp.health.atw.msclaim.models.exceptions.ClaimHasWrongStatusException;
import uk.gov.dwp.health.atw.msclaim.models.exceptions.NoClaimRecordFoundException;
import uk.gov.dwp.health.atw.msclaim.models.messaging.SubmitToContactInformationBundlerEvent;
import uk.gov.dwp.health.atw.msclaim.models.requests.ContactInformationRequest;
import uk.gov.dwp.health.atw.msclaim.repositories.ContactInformationRepository;

@Service
public class ContactInformationService {

  @Value("${service.ms-claim-bundler.messaging.update-contact.routingKey}")
  String updateContactRoutingKey;

  @Value("${service.ms-claim-bundler.messaging.topic}")
  private String claimBundlerTopic;

  static final String CANNOT_CHANGE_STATUS = "Cannot change the contact information status to ";

  final ContactInformationRepository contactInformationRepository;
  final ClaimPublisher claimPublisher;
  final EmailNotificationService emailNotificationService;

  public ContactInformationService(ContactInformationRepository contactInformationRepository,
                                   ClaimPublisher claimPublisher,
                                   EmailNotificationService emailNotificationService) {
    this.contactInformationRepository = contactInformationRepository;
    this.claimPublisher = claimPublisher;
    this.emailNotificationService = emailNotificationService;
  }

  public ContactInformationRequest updateContactInformation(
      ContactInformationRequest contactInformation) {

    contactInformation.setContactInformationStatus(AWAITING_UPLOAD);
    ContactInformationRequest response = contactInformationRepository.save(contactInformation);

    uploadContactInformationToDocumentBatch(response.getId());

    emailNotificationService.notifyClaimantContactDetailsHaveChanged(response);

    return response;
  }

  private void uploadContactInformationToDocumentBatch(String id) {

    RequestIdRequest request = RequestIdRequest.builder().requestId(id).build();

    claimPublisher.publishToContactInformationBundler(
        new SubmitToContactInformationBundlerEvent(claimBundlerTopic, updateContactRoutingKey,
            request));
  }

  public ContactInformationRequest retrieveContactInformation(
      RequestIdRequest request) {

    return contactInformationRepository.findContactInformationById(request.getRequestId());
  }

  public void changeStatusToProcessingUpload(
      RequestIdRequest requestIdRequest)
      throws ClaimException {

    updateStatusOfContactInformation(requestIdRequest, AWAITING_UPLOAD, PROCESSING_UPLOAD);
  }

  public void changeStatusToUploadedToDocumentBatch(
      RequestIdRequest requestIdRequest) throws ClaimException {

    updateStatusOfContactInformation(requestIdRequest, PROCESSING_UPLOAD,
        UPLOADED_TO_DOCUMENT_BATCH);
  }

  public void changeStatusCompletedUploaded(RequestIdRequest requestIdRequest)
      throws ClaimException {
    updateStatusOfContactInformation(requestIdRequest,
        UPLOADED_TO_DOCUMENT_BATCH, COMPLETED_DRS_UPLOAD);
  }

  public void changeStatusFailedDrsUploads(RequestIdRequest requestIdRequest)
      throws ClaimException {
    updateStatusOfContactInformation(requestIdRequest,
        UPLOADED_TO_DOCUMENT_BATCH, FAILED_DRS_UPLOAD);
  }

  private void updateStatusOfContactInformation(
      RequestIdRequest requestIdRequest,
      ContactInformationStatus whatCurrentClaimStatusShouldBe,
      ContactInformationStatus updatedContactStatus) throws ClaimException {

    ContactInformationRequest contactRequest =
        contactInformationRepository.findContactInformationById(requestIdRequest.getRequestId());

    if (contactRequest == null) {
      throw new NoClaimRecordFoundException();
    }

    if (contactRequest.getContactInformationStatus() != null
        && contactRequest.getContactInformationStatus() == whatCurrentClaimStatusShouldBe) {

      contactRequest.setContactInformationStatus(updatedContactStatus);

      contactInformationRepository.save(contactRequest);
    } else {
      throw new ClaimHasWrongStatusException(CANNOT_CHANGE_STATUS + updatedContactStatus);
    }
  }

}