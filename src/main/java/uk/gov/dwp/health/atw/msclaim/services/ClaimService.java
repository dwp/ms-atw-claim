package uk.gov.dwp.health.atw.msclaim.services;

import static uk.gov.dwp.health.atw.msclaim.models.enums.ClaimStatus.AWAITING_AGENT_APPROVAL;
import static uk.gov.dwp.health.atw.msclaim.models.enums.ClaimStatus.AWAITING_COUNTER_SIGN;
import static uk.gov.dwp.health.atw.msclaim.models.enums.ClaimStatus.AWAITING_DRS_UPLOAD;
import static uk.gov.dwp.health.atw.msclaim.models.enums.ClaimStatus.COUNTER_SIGN_REJECTED;
import static uk.gov.dwp.health.atw.msclaim.models.enums.ClaimStatus.DRS_ERROR;
import static uk.gov.dwp.health.atw.msclaim.models.enums.ClaimStatus.REPLACED_BY_NEW_CLAIM;
import static uk.gov.dwp.health.atw.msclaim.models.enums.ClaimType.ADAPTATION_TO_VEHICLE;
import static uk.gov.dwp.health.atw.msclaim.models.enums.ClaimType.EQUIPMENT_OR_ADAPTATION;
import static uk.gov.dwp.health.atw.msclaim.models.enums.ClaimType.SUPPORT_WORKER;
import static uk.gov.dwp.health.atw.msclaim.models.enums.ClaimType.TRAVEL_TO_WORK;
import static uk.gov.dwp.health.atw.msclaim.models.enums.CounterSignType.ACCEPT;
import static uk.gov.dwp.health.atw.msclaim.models.enums.CounterSignType.REJECT;
import static uk.gov.dwp.health.atw.msclaim.models.enums.EmploymentStatus.SELFEMPLOYED;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uk.gov.dwp.health.atw.msclaim.messaging.ClaimPublisher;
import uk.gov.dwp.health.atw.msclaim.models.ClaimReferenceNinoRequest;
import uk.gov.dwp.health.atw.msclaim.models.ClaimReferenceRequest;
import uk.gov.dwp.health.atw.msclaim.models.ClaimRetrievalRequest;
import uk.gov.dwp.health.atw.msclaim.models.DocumentUploadRequest;
import uk.gov.dwp.health.atw.msclaim.models.NinoRequest;
import uk.gov.dwp.health.atw.msclaim.models.RequestIdRequest;
import uk.gov.dwp.health.atw.msclaim.models.enums.ClaimStatus;
import uk.gov.dwp.health.atw.msclaim.models.enums.ClaimType;
import uk.gov.dwp.health.atw.msclaim.models.enums.CounterSignType;
import uk.gov.dwp.health.atw.msclaim.models.exceptions.ClaimCannotBeCounterSignedException;
import uk.gov.dwp.health.atw.msclaim.models.exceptions.ClaimException;
import uk.gov.dwp.health.atw.msclaim.models.exceptions.ClaimHasWrongStatusException;
import uk.gov.dwp.health.atw.msclaim.models.exceptions.NoClaimRecordFoundException;
import uk.gov.dwp.health.atw.msclaim.models.exceptions.WrongClaimOrBadRequestException;
import uk.gov.dwp.health.atw.msclaim.models.messaging.SubmitToClaimBundlerEvent;
import uk.gov.dwp.health.atw.msclaim.models.requests.ClaimRequest;
import uk.gov.dwp.health.atw.msclaim.models.requests.ClaimRequestWithWorkplaceContact;
import uk.gov.dwp.health.atw.msclaim.models.requests.EquipmentOrAdaptationClaimRequest;
import uk.gov.dwp.health.atw.msclaim.models.requests.SupportWorkerClaimRequest;
import uk.gov.dwp.health.atw.msclaim.models.requests.TravelToWorkClaimRequest;
import uk.gov.dwp.health.atw.msclaim.models.requests.UpdateWorkplaceContactRequest;
import uk.gov.dwp.health.atw.msclaim.models.requests.WorkplaceContactRequest;
import uk.gov.dwp.health.atw.msclaim.models.responses.ClaimCountResponse;
import uk.gov.dwp.health.atw.msclaim.models.responses.ClaimResponse;
import uk.gov.dwp.health.atw.msclaim.repositories.ClaimRepository;


@Service
public class ClaimService {

  final Logger logger = LoggerFactory.getLogger(ClaimService.class);

  @Value("${service.ms-claim-bundler.messaging.new-claim.routingKey}")
  String newClaimRoutingKey;

  @Value("${service.ms-claim-bundler.messaging.topic}")
  private String claimBundlerTopic;

  static final String CANNOT_BE_WORKPLACE_CONTACT = " cannot be a workplace contact";
  static final String TW_CANNOT_RE_CREATE_PREVIOUS_CLAIM = " with employment status Self Employed "
      + "cannot recreate previous claim";
  static final String CANNOT_RE_CREATE_PREVIOUS_CLAIM = " cannot recreate previous claim";
  static final String CANNOT_CHANGE_STATUS = "Cannot change the claim status to ";

  final ClaimRepository<ClaimRequest> claimRepository;
  final ClaimRepository<TravelToWorkClaimRequest> travelToWorkClaimRepository;
  final ClaimRepository<SupportWorkerClaimRequest> supportWorkClaimRepository;
  final ClaimRepository<EquipmentOrAdaptationClaimRequest> equipmentOrAdaptationRepository;

  final ClaimPublisher claimPublisher;
  final EmailNotificationService emailNotificationService;

  public ClaimService(ClaimRepository<ClaimRequest> claimRepository,
                      ClaimRepository<TravelToWorkClaimRequest> travelToWorkClaimRepository,
                      ClaimRepository<SupportWorkerClaimRequest> supportWorkerClaimRequest,
                      ClaimRepository<EquipmentOrAdaptationClaimRequest>
                          equipmentOrAdaptationRepository,
                      ClaimPublisher claimPublisher,
                      EmailNotificationService emailNotificationService) {
    this.claimRepository = claimRepository;
    this.travelToWorkClaimRepository = travelToWorkClaimRepository;
    this.supportWorkClaimRepository = supportWorkerClaimRequest;
    this.equipmentOrAdaptationRepository = equipmentOrAdaptationRepository;
    this.claimPublisher = claimPublisher;
    this.emailNotificationService = emailNotificationService;
  }

  public void uploadClaimToDocumentBatch(String claimReference, String nino) {

    ClaimReferenceNinoRequest request =
        ClaimReferenceNinoRequest.builder().claimReference(claimReference).nino(nino).build();

    claimPublisher.publishToClaimBundler(
        new SubmitToClaimBundlerEvent(claimBundlerTopic, newClaimRoutingKey, request));
  }

  public ClaimRequest counterSignHandler(CounterSignType counterSignType,
                                         WorkplaceContactRequest workplaceContactRequest)
      throws ClaimException {

    if (counterSignType == ACCEPT) {
      ClaimRequest claimRequest = counterSign(workplaceContactRequest, AWAITING_DRS_UPLOAD);

      ClaimResponse claimResponse =
          ClaimResponse.of(claimRequest.getId(), claimRequest.getClaimType());
      uploadClaimToDocumentBatch(claimResponse.getClaimReference(), claimRequest.getNino());

      emailNotificationService.notifyClaimantTheirClaimHasBeenApproved(claimRequest);

      return claimRequest;
    } else if (counterSignType == REJECT) {

      ClaimRequest claimRequest = counterSign(workplaceContactRequest, COUNTER_SIGN_REJECTED);

      emailNotificationService.notifyClaimantTheirClaimHasBeenRejected(claimRequest);

      return claimRequest;
    }
    throw new WrongClaimOrBadRequestException(workplaceContactRequest.getClaimType()
        + " cannot be counter sign " + counterSignType);
  }

  private ClaimRequest counterSign(WorkplaceContactRequest workplaceContactRequest,
                                   ClaimStatus claimStatusToBeSet) throws ClaimException {

    ClaimRequestWithWorkplaceContact claimRecord;
    ClaimType claimType = workplaceContactRequest.getClaimType();

    if (claimType == TRAVEL_TO_WORK) {
      claimRecord = travelToWorkClaimRepository.findClaimByIdAndClaimType(
          workplaceContactRequest.getClaimNumber(), claimType.toString());
    } else if (claimType == SUPPORT_WORKER) {
      claimRecord = supportWorkClaimRepository.findClaimByIdAndClaimType(
          workplaceContactRequest.getClaimNumber(), claimType.toString());
    } else {
      throw new WrongClaimOrBadRequestException(claimType + CANNOT_BE_WORKPLACE_CONTACT);
    }

    validateClaimRequest(claimRecord);
    validateWorkplaceContact(workplaceContactRequest, claimStatusToBeSet);

    claimRecord.getWorkplaceContact().setOrganisation(workplaceContactRequest.getOrganisation());
    claimRecord.getWorkplaceContact().setJobTitle(workplaceContactRequest.getJobTitle());
    claimRecord.getWorkplaceContact().setAddress(workplaceContactRequest.getAddress());
    claimRecord.getWorkplaceContact().setReason(workplaceContactRequest.getReason());
    claimRecord.getWorkplaceContact().setDeclarationVersion(
        workplaceContactRequest.getDeclarationVersion());
    claimRecord.setClaimStatus(claimStatusToBeSet);

    ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("Europe/London"));
    claimRecord.getWorkplaceContact().setUpdatedOn(zonedDateTime.toLocalDateTime());

    return claimRepository.save(claimRecord);
  }

  public void validateClaimRequestPreviousClaimId(ClaimRequest claimRequest)
      throws ClaimException {

    if (null != claimRequest.getPreviousClaimId()) {
      if (claimRequest.getClaimType() == EQUIPMENT_OR_ADAPTATION) {
        throw new WrongClaimOrBadRequestException(EQUIPMENT_OR_ADAPTATION
            + CANNOT_RE_CREATE_PREVIOUS_CLAIM);
      }
      if (claimRequest.getClaimType() == ADAPTATION_TO_VEHICLE) {
        throw new WrongClaimOrBadRequestException(ADAPTATION_TO_VEHICLE
            + CANNOT_RE_CREATE_PREVIOUS_CLAIM);
      }
      if (claimRequest instanceof TravelToWorkClaimRequest) {

        TravelToWorkClaimRequest travelToWorkClaimRequest = (TravelToWorkClaimRequest) claimRequest;
        String employmentStatus = travelToWorkClaimRequest
            .getWorkplaceContact().getEmploymentStatus();

        if (employmentStatus.equalsIgnoreCase(SELFEMPLOYED.toString())) {
          throw new WrongClaimOrBadRequestException(TRAVEL_TO_WORK
              + TW_CANNOT_RE_CREATE_PREVIOUS_CLAIM);
        }
      }
    }
  }

  private void validateClaimRequest(ClaimRequest claimRequest) throws ClaimException {
    if (claimRequest == null) {
      throw new NoClaimRecordFoundException();
    }

    if (claimRequest.getClaimType() == EQUIPMENT_OR_ADAPTATION) {
      throw new WrongClaimOrBadRequestException(EQUIPMENT_OR_ADAPTATION
          + CANNOT_BE_WORKPLACE_CONTACT);
    }

    if (claimRequest.getClaimType() == ADAPTATION_TO_VEHICLE) {
      throw new WrongClaimOrBadRequestException(ADAPTATION_TO_VEHICLE
          + CANNOT_BE_WORKPLACE_CONTACT);
    }

    if (claimRequest.getClaimStatus() != AWAITING_COUNTER_SIGN) {
      throw new ClaimHasWrongStatusException("Claim with status "
          + claimRequest.getClaimStatus()
          + CANNOT_BE_WORKPLACE_CONTACT);
    }
  }

  private void validateWorkplaceContact(WorkplaceContactRequest workplaceContactRequest,
                                        ClaimStatus claimStatusToBeSet) throws ClaimException {

    if (claimStatusToBeSet.equals(COUNTER_SIGN_REJECTED)) {
      if (workplaceContactRequest.getReason() == null
          || workplaceContactRequest.getReason().trim().isEmpty()) {
        throw new WrongClaimOrBadRequestException(
            "Reason is required for workplace contact when rejecting");
      }
      if (workplaceContactRequest.getDeclarationVersion() != null) {
        throw new WrongClaimOrBadRequestException(
            "Declaration Version is not required for workplace contact when rejecting");
      }
    } else if (workplaceContactRequest.getReason() != null) {
      throw new WrongClaimOrBadRequestException(
          "Reason is should not be present when not rejecting claim");
    } else if (workplaceContactRequest.getDeclarationVersion() == null) {
      throw new WrongClaimOrBadRequestException(
          "Declaration Version is required when not rejecting claim");
    }
  }

  public ClaimRequest findClaimRequestForClaimReferenceAndNino(
      ClaimReferenceNinoRequest claimReference)
      throws ClaimException {
    return getClaimRequestUsingClaimReference(claimReference.getClaimReference(),
        claimReference.getClaimNumber(),
        claimReference.getClaimType(), claimReference.getNino());
  }

  public ClaimRequest findClaimRequestToWorkplaceContact(ClaimReferenceRequest claimReference)
      throws ClaimException {

    System.out.println("claimReference.getClaimType(): " + claimReference.getClaimType());
    if (claimReference.getClaimType()
        .equals(EQUIPMENT_OR_ADAPTATION.toString())) {
      throw new WrongClaimOrBadRequestException(
          EQUIPMENT_OR_ADAPTATION + CANNOT_BE_WORKPLACE_CONTACT);
    }
    if (claimReference.getClaimType()
        .equals(ADAPTATION_TO_VEHICLE.toString())) {
      throw new WrongClaimOrBadRequestException(
          ADAPTATION_TO_VEHICLE + CANNOT_BE_WORKPLACE_CONTACT);
    }

    ClaimRequest claimRequest =
        getClaimRequestUsingClaimReference(claimReference.getClaimReference(),
            claimReference.getClaimNumber(),
            claimReference.getClaimType(), null);

    if (claimRequest.getClaimStatus() != AWAITING_COUNTER_SIGN) {
      throw new ClaimCannotBeCounterSignedException("Claim cannot be counter signed with status "
          + claimRequest.getClaimStatus());
    }

    return claimRequest;
  }

  private ClaimRequest getClaimRequestUsingClaimReference(String claimReference,
                                                          String claimNumberString,
                                                          String claimType, String nino)
      throws ClaimException {

    try {
      int claimNumberInt = Integer.parseInt(claimNumberString);

      ClaimRequest claimRequest =
          nino == null ? claimRepository.findClaimByIdAndClaimType(claimNumberInt,
              claimType) : claimRepository.findClaimByIdAndClaimTypeAndNino(claimNumberInt,
              claimType, nino);

      if (claimRequest == null) {
        throw new NoClaimRecordFoundException();
      }

      return claimRequest;
    } catch (NumberFormatException e) {
      logger.error("Claim number is not a number {}", claimReference);
      throw new WrongClaimOrBadRequestException("Claim number is not a number "
          + claimReference);
    }
  }

  public List<ClaimRequest> findClaimsForNinoAndType(ClaimRetrievalRequest request) {
    return claimRepository.findClaimsByNinoAndClaimType(request.getNino(),
        request.getClaimType().name());
  }

  public ClaimRequest changeStatusToUploadedToDocumentBatch(
      DocumentUploadRequest documentUploadRequest) throws ClaimException {

    return updateRequestIdAndStatusOnClaim(documentUploadRequest);
  }

  public ClaimRequest changeStatusToDrsError(RequestIdRequest requestIdRequest)
      throws ClaimException {

    return updateStatusWithDocBatchResponse(requestIdRequest.getRequestId(),
        DRS_ERROR);
  }

  public ClaimRequest changeStatusToAwaitingAgentApproval(
      RequestIdRequest requestIdRequest)
      throws ClaimException {

    return updateStatusWithDocBatchResponse(requestIdRequest.getRequestId(),
        AWAITING_AGENT_APPROVAL);
  }

  public long countRejectedClaimsForNino(NinoRequest ninoRequest) {

    return claimRepository.countClaimByNinoAndClaimStatus(ninoRequest.getNino(),
        COUNTER_SIGN_REJECTED.name());
  }

  public List<ClaimCountResponse> countRejectedClaimsForNinoAndClaimType(NinoRequest ninoRequest) {
    return claimRepository.countClaimByNinoAndClaimTypeAndClaimStatus(
        ninoRequest.getNino(), COUNTER_SIGN_REJECTED.name());
  }

  private ClaimRequest updateRequestIdAndStatusOnClaim(
      DocumentUploadRequest documentUploadRequest) throws ClaimException {
    ClaimRequest claimRequest =
        getClaimRequestUsingClaimReference(documentUploadRequest.getClaimReference(),
            documentUploadRequest.getClaimNumber(), documentUploadRequest.getClaimType(), null);

    if (claimRequest.getClaimStatus() != null
        && claimRequest.getClaimStatus() == ClaimStatus.AWAITING_DRS_UPLOAD) {
      claimRequest.setClaimStatus(ClaimStatus.UPLOADED_TO_DOCUMENT_BATCH);

      // Set requestId only when this is related to creating request in ms-document-batch
      claimRequest.setDocumentBatchRequestId(documentUploadRequest.getRequestId());

      return claimRepository.save(claimRequest);
    }
    throw new ClaimHasWrongStatusException(
        CANNOT_CHANGE_STATUS + ClaimStatus.UPLOADED_TO_DOCUMENT_BATCH);
  }

  private ClaimRequest updateStatusWithDocBatchResponse(String requestId,
                                                        ClaimStatus updatedClaimStatus)
      throws ClaimException {
    ClaimRequest claimRequest = claimRepository.findClaimByDocumentBatchRequestId(requestId);

    if (claimRequest != null) {
      if (claimRequest.getClaimStatus() != null
          && claimRequest.getClaimStatus() == ClaimStatus.UPLOADED_TO_DOCUMENT_BATCH) {
        claimRequest.setClaimStatus(updatedClaimStatus);

        return claimRepository.save(claimRequest);
      }
      throw new ClaimHasWrongStatusException(CANNOT_CHANGE_STATUS + updatedClaimStatus);
    } else {
      throw new NoClaimRecordFoundException();
    }
  }

  public ClaimRequest updateWorkplaceDetails(
      UpdateWorkplaceContactRequest updateClaimRequest)
      throws ClaimException {

    ClaimRequest response = null;
    ClaimRequest claimRequest =
        getClaimRequestUsingClaimReference(updateClaimRequest.getClaimReference(),
            updateClaimRequest.getClaimNumber(), updateClaimRequest.getClaimType(),
            updateClaimRequest.getNino());

    if (claimRequest.getClaimType() != EQUIPMENT_OR_ADAPTATION
        && claimRequest.getClaimType() != ADAPTATION_TO_VEHICLE) {

      if (!claimRequest.getClaimStatus().equals(AWAITING_COUNTER_SIGN)) {
        throw new ClaimCannotBeCounterSignedException(
            "Cannot be updated as it does not have the status AWAITING_COUNTER_SIGN");
      }

      if (claimRequest instanceof TravelToWorkClaimRequest) {
        TravelToWorkClaimRequest travelToWorkClaimRequest = (TravelToWorkClaimRequest) claimRequest;
        String employmentStatus =
            travelToWorkClaimRequest.getWorkplaceContact().getEmploymentStatus();

        if (!employmentStatus.equalsIgnoreCase("employed")) {
          throw new WrongClaimOrBadRequestException(
              "Cannot update work place details for someone self-employed");
        }

        travelToWorkClaimRequest.getWorkplaceContact()
            .setFullName(updateClaimRequest.getWorkplaceContact().getFullName());
        travelToWorkClaimRequest.getWorkplaceContact()
            .setEmailAddress(updateClaimRequest.getWorkplaceContact().getEmailAddress());

        response = claimRepository.save(travelToWorkClaimRequest);
      } else if (claimRequest instanceof SupportWorkerClaimRequest) {
        SupportWorkerClaimRequest supportWorkerClaimRequest =
            (SupportWorkerClaimRequest) claimRequest;

        supportWorkerClaimRequest.getWorkplaceContact()
            .setFullName(updateClaimRequest.getWorkplaceContact().getFullName());
        supportWorkerClaimRequest.getWorkplaceContact()
            .setEmailAddress(updateClaimRequest.getWorkplaceContact().getEmailAddress());

        response = claimRepository.save(supportWorkerClaimRequest);
      }
    }

    if (response != null) {
      emailNotificationService.notifyWorkplaceContactOfClaimToReview(response);

      emailNotificationService.notifyClaimantThatRequestHasBeenSentToWorkplaceContact(response);

      return response;
    }

    throw new WrongClaimOrBadRequestException(
        claimRequest.getClaimType() + " cannot update workplace details");
  }

  public void handlePreviousClaim(ClaimRequest claim) {
    if (claim.getPreviousClaimId() != null && claim.getClaimStatus() == AWAITING_COUNTER_SIGN) {
      ClaimRequest previousClaimRequest = claimRepository.findClaimByIdAndClaimType(
          claim.getPreviousClaimId(), claim.getClaimType().toString());

      if (previousClaimRequest != null) {
        previousClaimRequest.setClaimStatus(REPLACED_BY_NEW_CLAIM);
        claimRepository.save(previousClaimRequest);
      }
    }
  }

  @SneakyThrows
  public ClaimRequest validateAndSaveActiveClaim(ClaimRequest claim, long claimNumberCounter) {
    validateClaimRequestPreviousClaimId(claim);

    claim.setId(claimNumberCounter);
    return claimRepository.save(claim);
  }
}

