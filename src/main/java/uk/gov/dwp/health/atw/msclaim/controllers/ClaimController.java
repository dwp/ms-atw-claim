package uk.gov.dwp.health.atw.msclaim.controllers;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.dwp.health.atw.msclaim.models.ClaimReferenceNinoRequest;
import uk.gov.dwp.health.atw.msclaim.models.ClaimReferenceRequest;
import uk.gov.dwp.health.atw.msclaim.models.ClaimRetrievalRequest;
import uk.gov.dwp.health.atw.msclaim.models.DocumentUploadRequest;
import uk.gov.dwp.health.atw.msclaim.models.NinoRequest;
import uk.gov.dwp.health.atw.msclaim.models.RequestIdRequest;
import uk.gov.dwp.health.atw.msclaim.models.enums.CounterSignType;
import uk.gov.dwp.health.atw.msclaim.models.exceptions.ClaimException;
import uk.gov.dwp.health.atw.msclaim.models.requests.ClaimRequest;
import uk.gov.dwp.health.atw.msclaim.models.requests.UpdateWorkplaceContactRequest;
import uk.gov.dwp.health.atw.msclaim.models.requests.WorkplaceContactRequest;
import uk.gov.dwp.health.atw.msclaim.models.responses.ClaimCountResponse;
import uk.gov.dwp.health.atw.msclaim.models.responses.ClaimResponse;
import uk.gov.dwp.health.atw.msclaim.models.responses.CountResponse;
import uk.gov.dwp.health.atw.msclaim.repositories.DatabaseSequenceRepository;
import uk.gov.dwp.health.atw.msclaim.services.ClaimService;
import uk.gov.dwp.health.atw.msclaim.services.ClaimSubmissionService;

@RestController
@Validated
public class ClaimController {
  final ClaimService claimService;
  final ClaimSubmissionService claimSubmissionService;
  final DatabaseSequenceRepository sequenceGenerator;

  public ClaimController(ClaimService claimService,
                         DatabaseSequenceRepository sequenceGenerator,
                         ClaimSubmissionService claimSubmissionService) {
    this.claimService = claimService;
    this.sequenceGenerator = sequenceGenerator;
    this.claimSubmissionService = claimSubmissionService;
  }

  @PostMapping(value = "/submit", consumes = "application/json")
  public ResponseEntity<ClaimResponse> submitClaim(@Valid @RequestBody ClaimRequest claimRequest)
      throws DuplicateKeyException {

    long claimNumberCounter = sequenceGenerator.getNextValueInSequence(ClaimRequest.SEQUENCE_NAME);
    ClaimResponse response = claimSubmissionService.submitClaim(claimRequest, claimNumberCounter);


    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PutMapping(value = "/accept", consumes = "application/json")
  public ResponseEntity<?> workplaceContactAccepted(
      @Valid @RequestBody WorkplaceContactRequest body)
      throws ClaimException {
    claimService.counterSignHandler(CounterSignType.ACCEPT, body);

    return ResponseEntity.noContent().build();
  }

  @PutMapping(value = "/reject", consumes = "application/json")
  public ResponseEntity<?> workplaceContactRejected(
      @Valid @RequestBody WorkplaceContactRequest body)
      throws ClaimException {
    claimService.counterSignHandler(CounterSignType.REJECT, body);

    return ResponseEntity.noContent().build();
  }

  @PostMapping(value = "/claim-for-reference-and-nino", consumes = "application/json")
  public ResponseEntity<ClaimRequest> findClaimRequestFromClaimReference(
      @Valid @RequestBody ClaimReferenceNinoRequest body) throws ClaimException {
    ClaimRequest response = claimService.findClaimRequestForClaimReferenceAndNino(body);

    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @PostMapping(value = "/claim-to-workplace-contact", consumes = "application/json")
  public ResponseEntity<ClaimRequest> findClaimRequestToWorkplaceContact(
      @Valid @RequestBody ClaimReferenceRequest body) throws ClaimException {
    ClaimRequest response = claimService.findClaimRequestToWorkplaceContact(body);

    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @PostMapping(value = "/claims-for-nino-and-type", consumes = "application/json")
  public ResponseEntity<List<ClaimRequest>> findClaimsByNinoAndType(
          @Valid @RequestBody ClaimRetrievalRequest body) {
    List<ClaimRequest> response = claimService.findClaimsForNinoAndType(body);

    if (!response.isEmpty()) {
      return ResponseEntity.ok(response);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping(value = "/count-rejected-claims", consumes = "application/json")
  public ResponseEntity<CountResponse> countRejectedClaimsForNino(
          @Valid @RequestBody NinoRequest body) {
    long response = claimService.countRejectedClaimsForNino(body);
    return ResponseEntity.ok(new CountResponse(response));
  }

  @PostMapping(value = "/count-rejected-claims-by-claim-type", consumes = "application/json")
  public ResponseEntity<List<ClaimCountResponse>> countRejectedClaimsForNinoAndClaimType(
      @Valid @RequestBody NinoRequest body) {
    List<ClaimCountResponse> response = claimService.countRejectedClaimsForNinoAndClaimType(body);
    if (!response.isEmpty()) {
      return ResponseEntity.ok(response);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @PutMapping(value = "/change-status/uploaded-to-document-batch", consumes = "application/json")
  public ResponseEntity<?> changeClaimStatusToUploadedToDocumentBatch(
          @Valid @RequestBody DocumentUploadRequest body) throws ClaimException {
    claimService.changeStatusToUploadedToDocumentBatch(body);

    return ResponseEntity.noContent().build();
  }

  @PutMapping(value = "/change-status/drs-error", consumes = "application/json")
  public ResponseEntity<?> changeClaimStatusToDrsError(
      @Valid @RequestBody RequestIdRequest body) throws ClaimException {
    claimService.changeStatusToDrsError(body);

    return ResponseEntity.noContent().build();
  }

  @PutMapping(value = "/change-status/awaiting-agent-approval", consumes = "application/json")
  public ResponseEntity<?> changeClaimStatusToAwaitingAgentApproval(
      @Valid @RequestBody RequestIdRequest body) throws ClaimException {
    claimService.changeStatusToAwaitingAgentApproval(body);

    return ResponseEntity.noContent().build();
  }

  @PutMapping(value = "/update-workplace-contact-details", consumes = "application/json")
  public ResponseEntity<?> updateWorkplaceContactDetails(
          @Valid @RequestBody UpdateWorkplaceContactRequest body)
      throws ClaimException {
    claimService.updateWorkplaceDetails(body);

    return ResponseEntity.noContent().build();
  }
}
