package uk.gov.dwp.health.atw.msclaim.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.dwp.health.atw.msclaim.models.RequestIdRequest;
import uk.gov.dwp.health.atw.msclaim.models.exceptions.ClaimException;
import uk.gov.dwp.health.atw.msclaim.models.requests.ContactInformationRequest;
import uk.gov.dwp.health.atw.msclaim.services.ContactInformationService;

@RestController
@Validated
public class ContactInformationController {
  final ContactInformationService contactInformationService;

  public ContactInformationController(ContactInformationService contactInformationService) {
    this.contactInformationService = contactInformationService;
  }

  @PostMapping(value = "/update-contact-information", consumes = "application/json")
  public ResponseEntity<ContactInformationRequest> updateContactInformation(
      @Valid @RequestBody ContactInformationRequest body) {

    ContactInformationRequest response = contactInformationService.updateContactInformation(body);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PostMapping(value = "/retrieve-contact-information", consumes = "application/json")
  public ResponseEntity<ContactInformationRequest> retrieveContactInformation(
      @Valid @RequestBody RequestIdRequest body) {

    ContactInformationRequest response = contactInformationService.retrieveContactInformation(body);

    if (response != null) {
      return ResponseEntity.ok(response);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @PutMapping(value = "/contact/change-status/processing-upload", consumes = "application/json")
  public ResponseEntity<?> changeClaimStatusToProcessingUpload(
      @Valid @RequestBody RequestIdRequest body) throws ClaimException {
    contactInformationService.changeStatusToProcessingUpload(body);

    return ResponseEntity.noContent().build();
  }

  @PutMapping(value = "/contact/change-status/uploaded-to-document-batch",
      consumes = "application/json")
  public ResponseEntity<?> changeClaimStatusToUploadToDocumentBatch(
      @Valid @RequestBody RequestIdRequest body) throws ClaimException {
    contactInformationService.changeStatusToUploadedToDocumentBatch(body);

    return ResponseEntity.noContent().build();
  }

  @PutMapping(value = "/contact/change-status/completed-upload", consumes = "application/json")
  public ResponseEntity<?> changeClaimStatusToUploadCompletedUpload(
      @Valid @RequestBody RequestIdRequest body) throws ClaimException {
    contactInformationService.changeStatusCompletedUploaded(body);

    return ResponseEntity.noContent().build();
  }

  @PutMapping(value = "/contact/change-status/failed-drs-upload", consumes = "application/json")
  public ResponseEntity<?> changeClaimStatusToFailedDrsUpload(
      @Valid @RequestBody RequestIdRequest body) throws ClaimException {
    contactInformationService.changeStatusFailedDrsUploads(body);

    return ResponseEntity.noContent().build();
  }
}
