package uk.gov.dwp.health.atw.msclaim.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static uk.gov.dwp.health.atw.msclaim.models.enums.ClaimStatus.AWAITING_AGENT_APPROVAL;
import static uk.gov.dwp.health.atw.msclaim.models.enums.ClaimStatus.DRS_ERROR;
import static uk.gov.dwp.health.atw.msclaim.models.enums.ClaimStatus.UPLOADED_TO_DOCUMENT_BATCH;
import static uk.gov.dwp.health.atw.msclaim.testData.EquipmentOrAdaptationTestData.submittedEquipmentOrAdaptationRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.EquipmentOrAdaptationTestData.submittedInvalidEAdRequestWithPreviousId;
import static uk.gov.dwp.health.atw.msclaim.testData.SupportWorkerTestData.existingSupportWorkerClaimRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.SupportWorkerTestData.invalidClaimTypeSupportWorkerClaimRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.SupportWorkerTestData.invalidRejectWorkplaceContactWithEmptyReasonDescriptionForSupportWorkerClaimRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.SupportWorkerTestData.resubmittedSavedValidSupportWorkerClaimRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.SupportWorkerTestData.submittedAcceptedSupportWorkerClaimRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.SupportWorkerTestData.submittedRejectedSupportWorkerClaimRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.SupportWorkerTestData.submittedSupportWorkerClaimForOneMonthRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.SupportWorkerTestData.validSupportWorkerClaimRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.UUID;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.claimRetrievalRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.countRejectedClaimsForNinoAndClaimTypeCount;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.countRejectedClaimsForNinoCount;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.documentUploadRequestWithRequestId;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.invalidClaimReferenceForTravelToWorkClaim;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.invalidClaimReferenceNinoForTravelToWorkClaim;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.invalidClaimReferenceNinoWithClaimNumberOnly;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.invalidClaimReferenceNinoWithClaimTypeOnly;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.nullRequestRejectedClaimsForNino;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.requestIdRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.validClaimNumber;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.validClaimReferenceForEquipmentOrAdaptationClaim;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.validClaimReferenceForTravelToWorkClaim;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.validClaimReferenceNinoForTravelToWorkClaim;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.validRequestRejectedClaimsForNino;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelToWorkTestData.awaitingAgentApprovalTravelToWorkClaimRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelToWorkTestData.drsErrorTravelToWorkClaimRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelToWorkTestData.existingTravelToWorkClaimRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelToWorkTestData.invalidAcceptWorkplaceContactWithReasonDescriptionForTravelToWorkRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelToWorkTestData.invalidClaimForTravelToWorkWithPreviousIdRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelToWorkTestData.invalidClaimTypeForTravelToWorkRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelToWorkTestData.invalidSelfEmployedAwaitingCounterSignTravelToWorkClaimRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelToWorkTestData.submittedAcceptedTravelToWorkClaimRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelToWorkTestData.submittedAcceptedTravelToWorkClaimRequestWithTwoClaims;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelToWorkTestData.submittedLiftForTwoMonthsTravelToWorkClaimRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelToWorkTestData.submittedLiftTravelToWorkClaimRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelToWorkTestData.submittedRejectedTravelToWorkClaimRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelToWorkTestData.submittedTaxiTravelToWorkClaimRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelToWorkTestData.uploadedToDocumentBatchTravelToWorkClaimRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.UpdateWorkplaceContactInformationTestData.updateSupportWorkerWorkplaceContactInformationRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.UpdateWorkplaceContactInformationTestData.updateWorkplaceContactForSupportWorkerClaimOneMonthRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.UpdateWorkplaceContactInformationTestData.updateWorkplaceContactForTravelToWorkClaimRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.WorkplaceContactTestData.inValidAcceptedSupportWorkerClaimMissingDeclarationVersion;
import static uk.gov.dwp.health.atw.msclaim.testData.WorkplaceContactTestData.inValidRejectedSupportWorkerClaimHasDeclarationVersion;
import static uk.gov.dwp.health.atw.msclaim.testData.WorkplaceContactTestData.validAcceptedEquipmentOrAdaptationClaim;
import static uk.gov.dwp.health.atw.msclaim.testData.WorkplaceContactTestData.validAcceptedSupportWorkerClaim;
import static uk.gov.dwp.health.atw.msclaim.testData.WorkplaceContactTestData.validAcceptedTravelToWorkClaim;
import static uk.gov.dwp.health.atw.msclaim.testData.WorkplaceContactTestData.validRejectedSupportWorkerClaim;
import static uk.gov.dwp.health.atw.msclaim.testData.WorkplaceContactTestData.validRejectedTravelToWorkClaim;
import static uk.gov.dwp.health.atw.msclaim.utils.TestUtils.asJsonString;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import uk.gov.dwp.health.atw.msclaim.messaging.ClaimPublisher;
import uk.gov.dwp.health.atw.msclaim.models.enums.ClaimStatus;
import uk.gov.dwp.health.atw.msclaim.models.enums.CounterSignType;
import uk.gov.dwp.health.atw.msclaim.models.exceptions.ClaimCannotBeCounterSignedException;
import uk.gov.dwp.health.atw.msclaim.models.exceptions.ClaimException;
import uk.gov.dwp.health.atw.msclaim.models.exceptions.ClaimHasWrongStatusException;
import uk.gov.dwp.health.atw.msclaim.models.exceptions.NoClaimRecordFoundException;
import uk.gov.dwp.health.atw.msclaim.models.exceptions.WrongClaimOrBadRequestException;
import uk.gov.dwp.health.atw.msclaim.models.messaging.SubmitToClaimBundlerEvent;
import uk.gov.dwp.health.atw.msclaim.models.requests.ClaimRequest;
import uk.gov.dwp.health.atw.msclaim.models.requests.EquipmentOrAdaptationClaimRequest;
import uk.gov.dwp.health.atw.msclaim.models.requests.SupportWorkerClaimRequest;
import uk.gov.dwp.health.atw.msclaim.models.requests.TravelToWorkClaimRequest;
import uk.gov.dwp.health.atw.msclaim.repositories.ClaimRepository;
import uk.gov.dwp.health.atw.msclaim.testData.SupportWorkerTestData;

@SpringBootTest(classes = ClaimService.class)
class ClaimServiceTests {

  @Autowired
  ClaimService claimService;

  @MockBean
  private ClaimRepository<ClaimRequest> claimRepository;

  @MockBean
  private ClaimRepository<TravelToWorkClaimRequest> travelToWorkClaimRepository;
  @MockBean
  private ClaimRepository<SupportWorkerClaimRequest> supportWorkClaimRepository;
  @MockBean
  private ClaimRepository<EquipmentOrAdaptationClaimRequest> equipmentOrAdaptationRepository;
  @MockBean
  private ClaimPublisher claimPublisher;
  @MockBean
  private EmailNotificationService emailNotificationService;

  @Test
  @DisplayName("accept workplace contact successful")
  void acceptWorkplaceContactSupportWorkerSuccessful() throws Exception {
    SupportWorkerClaimRequest spySupportWorkerClaimRequest =
        spy(existingSupportWorkerClaimRequest);

    when(supportWorkClaimRepository.findClaimByIdAndClaimType(any(Long.class), any(String.class)))
        .thenReturn(spySupportWorkerClaimRequest);
    when(claimRepository.save(any(SupportWorkerClaimRequest.class)))
        .thenReturn(submittedAcceptedSupportWorkerClaimRequest);

    assertEquals(asJsonString(submittedAcceptedSupportWorkerClaimRequest),
        asJsonString(claimService.counterSignHandler(CounterSignType.ACCEPT,
            validAcceptedSupportWorkerClaim)));

    verify(spySupportWorkerClaimRequest, times(1))
        .setClaimStatus(ClaimStatus.AWAITING_DRS_UPLOAD);
    verify(claimPublisher, times(1)).publishToClaimBundler(any(SubmitToClaimBundlerEvent.class));
    verify(emailNotificationService, times(1)).notifyClaimantTheirClaimHasBeenApproved(
        any(ClaimRequest.class));
  }

  @Test
  @DisplayName("accept workplace contact travel to work successful")
  void acceptWorkplaceContactTravelToWorkSuccessful() throws Exception {
    TravelToWorkClaimRequest spyTravelToWorkClaimRequest =
        spy(existingTravelToWorkClaimRequest);

    when(travelToWorkClaimRepository.findClaimByIdAndClaimType(any(Long.class), any(String.class)))
        .thenReturn(spyTravelToWorkClaimRequest);
    when(claimRepository.save(any(TravelToWorkClaimRequest.class)))
        .thenReturn(submittedAcceptedTravelToWorkClaimRequest);

    assertEquals(asJsonString(submittedAcceptedTravelToWorkClaimRequest),
        asJsonString(claimService.counterSignHandler(CounterSignType.ACCEPT,
            validAcceptedTravelToWorkClaim)));

    verify(spyTravelToWorkClaimRequest, times(1))
        .setClaimStatus(ClaimStatus.AWAITING_DRS_UPLOAD);
    verify(claimPublisher, times(1)).publishToClaimBundler(any(SubmitToClaimBundlerEvent.class));
    verify(emailNotificationService, times(1)).notifyClaimantTheirClaimHasBeenApproved(
        any(ClaimRequest.class));
  }
  @Test
  @DisplayName("accept failed as workplace contact is Equipment Or Adaptation")
  void acceptWorkplaceContactEquipmentOrAdaptation() {
    when(equipmentOrAdaptationRepository.findClaimByIdAndClaimType(any(Long.class),
        any(String.class)))
        .thenReturn(submittedEquipmentOrAdaptationRequest);
    WrongClaimOrBadRequestException thrown =
        assertThrows(WrongClaimOrBadRequestException.class,
            () -> claimService.counterSignHandler(CounterSignType.ACCEPT,
                validAcceptedEquipmentOrAdaptationClaim));

    assertEquals("EQUIPMENT_OR_ADAPTATION cannot be a workplace contact",
        thrown.getErrorMessage());
    verify(claimPublisher, never()).publishToClaimBundler(any(SubmitToClaimBundlerEvent.class));
    verify(emailNotificationService, never()).notifyClaimantTheirClaimHasBeenApproved(
        any(ClaimRequest.class));
  }

  @Test
  @DisplayName("accept workplace contact no claim number found")
  void acceptWorkplaceContactNoClaimNumberFound() {
    when(travelToWorkClaimRepository.findClaimByIdAndClaimType(any(Long.class),
        any(String.class))).thenReturn(null);
    NoClaimRecordFoundException thrown = assertThrows(NoClaimRecordFoundException.class,
        () -> claimService.counterSignHandler(CounterSignType.ACCEPT,
            validAcceptedTravelToWorkClaim));

    assertEquals("No claim record found",
        thrown.getErrorMessage());
    verify(claimPublisher, never()).publishToClaimBundler(any(SubmitToClaimBundlerEvent.class));
    verify(emailNotificationService, never()).notifyClaimantTheirClaimHasBeenApproved(
        any(ClaimRequest.class));
  }

  @Test
  @DisplayName("accept workplace contact has wrong claim status")
  void acceptWorkplaceContactWithClaimStatusNotAwaitingCounterSign() {
    when(travelToWorkClaimRepository.findClaimByIdAndClaimType(any(Long.class), any(String.class)))
        .thenReturn(submittedAcceptedTravelToWorkClaimRequest);
    ClaimHasWrongStatusException thrown = assertThrows(ClaimHasWrongStatusException.class,
        () -> claimService.counterSignHandler(CounterSignType.ACCEPT,
            validAcceptedTravelToWorkClaim));

    assertEquals("Claim with status AWAITING_DRS_UPLOAD cannot be a workplace contact",
        thrown.getErrorMessage());
    verify(claimPublisher, never()).publishToClaimBundler(any(SubmitToClaimBundlerEvent.class));
    verify(emailNotificationService, never()).notifyClaimantTheirClaimHasBeenApproved(
        any(ClaimRequest.class));
  }

  @Test
  @DisplayName("accept workplace contact has Equipment And Adaptations claim type")
  void acceptWorkplaceContactWithEquipAndAdaptationsClaimType() {
    when(travelToWorkClaimRepository.findClaimByIdAndClaimType(any(Long.class), any(String.class)))
        .thenReturn(invalidClaimTypeForTravelToWorkRequest);
    WrongClaimOrBadRequestException thrown = assertThrows(WrongClaimOrBadRequestException.class,
        () -> claimService.counterSignHandler(CounterSignType.ACCEPT,
            validAcceptedTravelToWorkClaim));

    assertEquals("EQUIPMENT_OR_ADAPTATION cannot be a workplace contact",
        thrown.getErrorMessage());
    verify(claimPublisher, never()).publishToClaimBundler(any(SubmitToClaimBundlerEvent.class));
    verify(emailNotificationService, never()).notifyClaimantTheirClaimHasBeenApproved(
        any(ClaimRequest.class));
  }

  @Test
  @DisplayName("accept workplace contact with a reason description")
  void acceptWorkplaceContactWithReasonDescription() {
    when(travelToWorkClaimRepository.findClaimByIdAndClaimType(any(Long.class), any(String.class)))
        .thenReturn(invalidAcceptWorkplaceContactWithReasonDescriptionForTravelToWorkRequest);
    WrongClaimOrBadRequestException thrown = assertThrows(WrongClaimOrBadRequestException.class,
        () -> claimService.counterSignHandler(CounterSignType.ACCEPT,
            validRejectedTravelToWorkClaim));

    assertEquals("Reason is should not be present when not rejecting claim",
        thrown.getErrorMessage());
    verify(claimPublisher, never()).publishToClaimBundler(any(SubmitToClaimBundlerEvent.class));
    verify(emailNotificationService, never()).notifyClaimantTheirClaimHasBeenApproved(
        any(ClaimRequest.class));
  }

  @Test
  @DisplayName("accept workplace contact with a reason description")
  void acceptWorkplaceContactWithoutDeclarationVersion() {
    when(travelToWorkClaimRepository.findClaimByIdAndClaimType(any(Long.class), any(String.class)))
        .thenReturn(invalidAcceptWorkplaceContactWithReasonDescriptionForTravelToWorkRequest);
    WrongClaimOrBadRequestException thrown = assertThrows(WrongClaimOrBadRequestException.class,
        () -> claimService.counterSignHandler(CounterSignType.ACCEPT,
            inValidAcceptedSupportWorkerClaimMissingDeclarationVersion));

    assertEquals("Declaration Version is required when not rejecting claim",
        thrown.getErrorMessage());
    verify(claimPublisher, never()).publishToClaimBundler(any(SubmitToClaimBundlerEvent.class));
    verify(emailNotificationService, never()).notifyClaimantTheirClaimHasBeenApproved(
        any(ClaimRequest.class));
  }

  @Test
  @DisplayName("reject workplace contact successful")
  void rejectWorkplaceContactTravelToWorkSuccessful() throws ClaimException {
    TravelToWorkClaimRequest spyTravelToWorkClaimRequest =
        spy(existingTravelToWorkClaimRequest);

    when(travelToWorkClaimRepository.findClaimByIdAndClaimType(any(Long.class), any(String.class)))
        .thenReturn(spyTravelToWorkClaimRequest);
    when(claimRepository.save(any(TravelToWorkClaimRequest.class)))
        .thenReturn(submittedRejectedTravelToWorkClaimRequest);

    assertEquals(asJsonString(submittedRejectedTravelToWorkClaimRequest),
        asJsonString(claimService.counterSignHandler(CounterSignType.REJECT,
            validRejectedTravelToWorkClaim)));

    verify(spyTravelToWorkClaimRequest, times(1))
        .setClaimStatus(ClaimStatus.COUNTER_SIGN_REJECTED);
    verify(claimPublisher, never()).publishToClaimBundler(any(SubmitToClaimBundlerEvent.class));
    verify(emailNotificationService, times(1)).notifyClaimantTheirClaimHasBeenRejected(
        any(ClaimRequest.class));
  }

  @Test
  @DisplayName("reject workplace contact successful")
  void rejectWorkplaceContactSupportWorkerSuccessful() throws ClaimException {
    SupportWorkerClaimRequest spySupportWorkerClaimRequest =
        spy(existingSupportWorkerClaimRequest);

    when(supportWorkClaimRepository.findClaimByIdAndClaimType(any(Long.class), any(String.class)))
        .thenReturn(spySupportWorkerClaimRequest);
    when(claimRepository.save(any(SupportWorkerClaimRequest.class)))
        .thenReturn(submittedRejectedSupportWorkerClaimRequest);

    assertEquals(asJsonString(submittedRejectedSupportWorkerClaimRequest),
        asJsonString(claimService.counterSignHandler(CounterSignType.REJECT,
            validRejectedSupportWorkerClaim)));

    verify(spySupportWorkerClaimRequest, times(1))
        .setClaimStatus(ClaimStatus.COUNTER_SIGN_REJECTED);
    verify(claimPublisher, never()).publishToClaimBundler(any(SubmitToClaimBundlerEvent.class));
    verify(emailNotificationService, times(1)).notifyClaimantTheirClaimHasBeenRejected(
        any(ClaimRequest.class));
  }

  @Test
  @DisplayName("reject failed as workplace contact is Equipment Or Adaptation")
  void rejectWorkplaceContactEquipmentOrAdaptation() {
    when(equipmentOrAdaptationRepository.findClaimByIdAndClaimType(any(Long.class),
        any(String.class)))
        .thenReturn(submittedEquipmentOrAdaptationRequest);
    WrongClaimOrBadRequestException thrown = assertThrows(WrongClaimOrBadRequestException.class,
        () -> claimService.counterSignHandler(CounterSignType.REJECT,
            validAcceptedEquipmentOrAdaptationClaim));

    assertEquals("EQUIPMENT_OR_ADAPTATION cannot be a workplace contact",
        thrown.getErrorMessage());
    verify(claimPublisher, never()).publishToClaimBundler(any(SubmitToClaimBundlerEvent.class));
    verify(emailNotificationService, never()).notifyClaimantTheirClaimHasBeenRejected(
        any(ClaimRequest.class));
  }

  @Test
  @DisplayName("reject workplace contact no claim number found")
  void rejectWorkplaceContactWithNoClaimNumberFound() {
    when(supportWorkClaimRepository.findClaimByIdAndClaimType(any(Long.class),
        any(String.class))).thenReturn(null);

    NoClaimRecordFoundException thrown = assertThrows(NoClaimRecordFoundException.class,
        () -> claimService.counterSignHandler(CounterSignType.REJECT,
            validRejectedSupportWorkerClaim));

    assertEquals("No claim record found",
        thrown.getErrorMessage());
    verify(claimPublisher, never()).publishToClaimBundler(any(SubmitToClaimBundlerEvent.class));
    verify(emailNotificationService, never()).notifyClaimantTheirClaimHasBeenRejected(
        any(ClaimRequest.class));
  }

  @Test
  @DisplayName("reject workplace contact has wrong claim status")
  void rejectWorkplaceContactWithClaimStatusNotAwaitingCounterSign() {
    when(supportWorkClaimRepository.findClaimByIdAndClaimType(any(Long.class), any(String.class)))
        .thenReturn(submittedRejectedSupportWorkerClaimRequest);
    ClaimHasWrongStatusException thrown = assertThrows(ClaimHasWrongStatusException.class,
        () -> claimService.counterSignHandler(CounterSignType.REJECT,
            validRejectedSupportWorkerClaim));
    assertEquals("Claim with status COUNTER_SIGN_REJECTED cannot be a workplace contact",
        thrown.getErrorMessage());
    verify(claimPublisher, never()).publishToClaimBundler(any(SubmitToClaimBundlerEvent.class));
    verify(emailNotificationService, never()).notifyClaimantTheirClaimHasBeenRejected(
        any(ClaimRequest.class));
  }

  @Test
  @DisplayName("reject workplace contact has Equipment And Adaptations claim type")
  void rejectWorkplaceContactWithEquipAndAdaptationsClaimType() {
    when(supportWorkClaimRepository.findClaimByIdAndClaimType(any(Long.class), any(String.class)))
        .thenReturn(invalidClaimTypeSupportWorkerClaimRequest);
    WrongClaimOrBadRequestException thrown = assertThrows(WrongClaimOrBadRequestException.class,
        () -> claimService.counterSignHandler(CounterSignType.REJECT,
            validRejectedSupportWorkerClaim));
    assertEquals("EQUIPMENT_OR_ADAPTATION cannot be a workplace contact",
        thrown.getErrorMessage());
    verify(claimPublisher, never()).publishToClaimBundler(any(SubmitToClaimBundlerEvent.class));
    verify(emailNotificationService, never()).notifyClaimantTheirClaimHasBeenRejected(
        any(ClaimRequest.class));
  }

  @Test
  @DisplayName("reject workplace contact with empty reason description")
  void rejectWorkplaceContactWithEmptyReasonDescription() {
    when(supportWorkClaimRepository.findClaimByIdAndClaimType(any(Long.class), any(String.class)))
        .thenReturn(
            invalidRejectWorkplaceContactWithEmptyReasonDescriptionForSupportWorkerClaimRequest);
    WrongClaimOrBadRequestException thrown = assertThrows(WrongClaimOrBadRequestException.class,
        () -> claimService.counterSignHandler(CounterSignType.REJECT,
            validAcceptedSupportWorkerClaim));
    assertEquals("Reason is required for workplace contact when rejecting",
        thrown.getErrorMessage());
    verify(claimPublisher, never()).publishToClaimBundler(any(SubmitToClaimBundlerEvent.class));
    verify(emailNotificationService, never()).notifyClaimantTheirClaimHasBeenRejected(
        any(ClaimRequest.class));
  }

  @Test
  @DisplayName("reject workplace contact with empty reason description")
  void rejectWorkplaceContactWithDeclarationVersion() {
    when(supportWorkClaimRepository.findClaimByIdAndClaimType(any(Long.class), any(String.class)))
        .thenReturn(
            invalidRejectWorkplaceContactWithEmptyReasonDescriptionForSupportWorkerClaimRequest);
    WrongClaimOrBadRequestException thrown = assertThrows(WrongClaimOrBadRequestException.class,
        () -> claimService.counterSignHandler(CounterSignType.REJECT,
            inValidRejectedSupportWorkerClaimHasDeclarationVersion));
    assertEquals("Declaration Version is not required for workplace contact when rejecting",
        thrown.getErrorMessage());
    verify(claimPublisher, never()).publishToClaimBundler(any(SubmitToClaimBundlerEvent.class));
    verify(emailNotificationService, never()).notifyClaimantTheirClaimHasBeenRejected(
        any(ClaimRequest.class));
  }

  @Test
  @DisplayName("find claim with claim reference")
  void findClaimWithClaimReference() throws ClaimException {
    when(claimRepository.findClaimByIdAndClaimTypeAndNino(any(Long.class), any(String.class),
        any(String.class)))
        .thenReturn(existingTravelToWorkClaimRequest);

    assertEquals(asJsonString(existingTravelToWorkClaimRequest),
        asJsonString(claimService.findClaimRequestForClaimReferenceAndNino(
            validClaimReferenceNinoForTravelToWorkClaim)));
  }

  @Test
  @DisplayName("find claim with claim reference which is not valid (TW0001SW)")
  void findClaimWithClaimReferenceWhichIsNotValid() {
    WrongClaimOrBadRequestException thrown = assertThrows(WrongClaimOrBadRequestException.class,
        () -> claimService.findClaimRequestForClaimReferenceAndNino(
            invalidClaimReferenceNinoForTravelToWorkClaim));
    assertEquals("Claim number is not a number TW0001SW",
        thrown.getErrorMessage());
  }

  @Test
  @DisplayName("find claim with claim reference with claim number only (0001)")
  void findClaimWithClaimReferenceWithClaimNumberOnly() {
    WrongClaimOrBadRequestException thrown = assertThrows(WrongClaimOrBadRequestException.class,
        () -> claimService.findClaimRequestForClaimReferenceAndNino(
            invalidClaimReferenceNinoWithClaimNumberOnly));

    assertEquals("Invalid claim reference format. Expected format is for example SW001",
        thrown.getErrorMessage());
  }

  @Test
  @DisplayName("find claim with claim reference with claim type only (TW)")
  void findClaimWithClaimReferenceWithClaimTypeOnly() {
    WrongClaimOrBadRequestException thrown = assertThrows(WrongClaimOrBadRequestException.class,
        () -> claimService.findClaimRequestForClaimReferenceAndNino(
            invalidClaimReferenceNinoWithClaimTypeOnly));
    assertEquals("Invalid claim reference format. Expected format is for example SW001",
        thrown.getErrorMessage());
  }

  @Test
  @DisplayName("find claim with claim reference which does not exist")
  void findClaimWithClaimReferenceWithDoesNotExist() {
    when(claimRepository.findClaimByIdAndClaimTypeAndNino(any(Long.class), any(String.class),
        any(String.class)))
        .thenReturn(null);

    NoClaimRecordFoundException thrown = assertThrows(NoClaimRecordFoundException.class,
        () -> claimService.findClaimRequestForClaimReferenceAndNino(
            validClaimReferenceNinoForTravelToWorkClaim));
    assertEquals("No claim record found",
        thrown.getErrorMessage());
  }

  @Test
  @DisplayName("find claim request to workplace contact successful")
  void findClaimRequestToCounterSignSuccessful() throws ClaimException {
    when(claimRepository.findClaimByIdAndClaimType(any(Long.class), any(String.class)))
        .thenReturn(existingTravelToWorkClaimRequest);

    assertEquals(asJsonString(existingTravelToWorkClaimRequest),
        asJsonString(
            claimService.findClaimRequestToWorkplaceContact(
                validClaimReferenceForTravelToWorkClaim)));
  }

  @Test
  @DisplayName("find claim reference to workplace contact with claim reference which is not valid")
  void findClaimRequestToCounterSignWithClaimReferenceWhichIsNotValid() {
    WrongClaimOrBadRequestException thrown = assertThrows(WrongClaimOrBadRequestException.class,
        () -> claimService.findClaimRequestToWorkplaceContact(
            invalidClaimReferenceForTravelToWorkClaim));

    assertEquals("Claim number is not a number TW0001SW",
        thrown.getErrorMessage());
  }

  @Test
  @DisplayName("find claim request to workplace contact failed with counter sign already approved")
  void findClaimRequestToCounterSignWithInvalidCounterSignAlreadyApproved() {
    when(claimRepository.findClaimByIdAndClaimType(any(Long.class), any(String.class)))
        .thenReturn(submittedAcceptedTravelToWorkClaimRequest);

    ClaimCannotBeCounterSignedException thrown =
        assertThrows(ClaimCannotBeCounterSignedException.class,
            () -> claimService.findClaimRequestToWorkplaceContact(
                validClaimReferenceForTravelToWorkClaim));

    assertEquals("Claim cannot be counter signed with status AWAITING_DRS_UPLOAD",
        thrown.getErrorMessage());
  }

  @Test
  @DisplayName("find claim request to workplace contact failed with counter sign already rejected")
  void findClaimRequestToCounterSignWithInvalidCounterSignAlreadyRejected() {
    when(claimRepository.findClaimByIdAndClaimType(any(Long.class), any(String.class)))
        .thenReturn(submittedRejectedTravelToWorkClaimRequest);

    ClaimCannotBeCounterSignedException thrown =
        assertThrows(ClaimCannotBeCounterSignedException.class,
            () -> claimService.findClaimRequestToWorkplaceContact(
                validClaimReferenceForTravelToWorkClaim));
    assertEquals("Claim cannot be counter signed with status COUNTER_SIGN_REJECTED",
        thrown.getErrorMessage());
  }

  @Test
  @DisplayName("find claim request to workplace contact failed with Equipment Or Adaptation claim type")
  void findClaimRequestToCounterSignWithEquipmentOrAdaptation() {
    when(claimRepository.findClaimByIdAndClaimType(any(Long.class), any(String.class)))
        .thenReturn(submittedEquipmentOrAdaptationRequest);

    WrongClaimOrBadRequestException thrown = assertThrows(WrongClaimOrBadRequestException.class,
        () -> claimService.findClaimRequestToWorkplaceContact(
            validClaimReferenceForEquipmentOrAdaptationClaim));

    assertEquals("EQUIPMENT_OR_ADAPTATION cannot be a workplace contact",
        thrown.getErrorMessage());
  }

  @Test
  @DisplayName("Find claims by Nino and Type")
  void findClaimsByNinoAndType() {
    when(claimRepository.findClaimsByNinoAndClaimType(any(String.class), any(String.class)))
        .thenReturn(List.of(submittedEquipmentOrAdaptationRequest));
    assertEquals(claimService.findClaimsForNinoAndType(claimRetrievalRequest),
        List.of(submittedEquipmentOrAdaptationRequest));
  }

  @Test
  @DisplayName("Change claim status to UPLOADED_TO_DOCUMENT_BATCH")
  void changeStatusToUploadedToDocumentBatch() throws ClaimException {
    ClaimRequest spyTravelToWorkClaimRequest =
        spy(submittedAcceptedTravelToWorkClaimRequestWithTwoClaims);

    when(claimRepository.findClaimByIdAndClaimType(anyLong(), anyString()))
        .thenReturn(spyTravelToWorkClaimRequest);
    when(claimRepository.save(any(TravelToWorkClaimRequest.class)))
        .thenReturn(uploadedToDocumentBatchTravelToWorkClaimRequest);

    assertEquals(uploadedToDocumentBatchTravelToWorkClaimRequest,
        claimService.changeStatusToUploadedToDocumentBatch(
            documentUploadRequestWithRequestId));

    verify(spyTravelToWorkClaimRequest, times(1)).setClaimStatus(UPLOADED_TO_DOCUMENT_BATCH);
    verify(spyTravelToWorkClaimRequest, times(1)).setDocumentBatchRequestId(UUID);
  }

  @Test
  @DisplayName("Throw exception when trying to change claim status to UPLOADED_TO_DOCUMENT_BATCH")
  void changeStatusToUploadedToDocumentBatchThrowsException() {
    when(claimRepository.findClaimByIdAndClaimType(anyLong(), anyString()))
        .thenReturn(submittedLiftTravelToWorkClaimRequest);

    ClaimHasWrongStatusException thrown =
        assertThrows(ClaimHasWrongStatusException.class,
            () -> claimService.changeStatusToUploadedToDocumentBatch(
                documentUploadRequestWithRequestId));

    assertEquals("Cannot change the claim status to UPLOADED_TO_DOCUMENT_BATCH",
        thrown.getErrorMessage());
  }

  @Test
  @DisplayName("change status to uploaded to document batch has no claim number found")
  void changeStatusToUploadedToDocumentBatchNoClaimNumberFound() {
    when(travelToWorkClaimRepository.findClaimByIdAndClaimType(any(Long.class),
        any(String.class))).thenReturn(null);
    NoClaimRecordFoundException thrown = assertThrows(NoClaimRecordFoundException.class,
        () -> claimService.changeStatusToUploadedToDocumentBatch(
            documentUploadRequestWithRequestId));

    assertEquals("No claim record found", thrown.getErrorMessage());
  }

  @Test
  @DisplayName("change status to DRS_ERROR successful")
  void changeStatusToDrsErrorSuccessful() throws ClaimException {
    ClaimRequest spyUploadedToDocumentBatchTWClaimRequest =
        spy(uploadedToDocumentBatchTravelToWorkClaimRequest);

    when(claimRepository.findClaimByDocumentBatchRequestId(
        any(String.class)))
        .thenReturn(spyUploadedToDocumentBatchTWClaimRequest);

    when(claimRepository.save(any(TravelToWorkClaimRequest.class)))
        .thenReturn(drsErrorTravelToWorkClaimRequest);

    assertEquals(drsErrorTravelToWorkClaimRequest,
        claimService.changeStatusToDrsError(requestIdRequest));

    verify(spyUploadedToDocumentBatchTWClaimRequest, times(1)).setClaimStatus(DRS_ERROR);
    verify(spyUploadedToDocumentBatchTWClaimRequest, never()).setDocumentBatchRequestId(UUID);
  }

  @Test
  @DisplayName("Throw exception when trying to change claim status to DRS_ERROR")
  void changeStatusToDrsErrorThrowsException() {

    when(claimRepository.findClaimByDocumentBatchRequestId(
        any(String.class)))
        .thenReturn(submittedLiftTravelToWorkClaimRequest);

    ClaimHasWrongStatusException thrown =
        assertThrows(ClaimHasWrongStatusException.class,
            () -> claimService.changeStatusToDrsError(requestIdRequest));

    assertEquals("Cannot change the claim status to DRS_ERROR", thrown.getErrorMessage());
  }

  @Test
  @DisplayName("change status to drs error has no claim number found")
  void changeStatusToDrsErrorNoClaimNumberFound() {
    when(travelToWorkClaimRepository.findClaimByDocumentBatchRequestId(
        any(String.class))).thenReturn(null);
    NoClaimRecordFoundException thrown = assertThrows(NoClaimRecordFoundException.class,
        () -> claimService.changeStatusToDrsError(requestIdRequest));

    assertEquals("No claim record found", thrown.getErrorMessage());
  }

  @Test
  @DisplayName("change status to AWAITING_AGENT_APPROVAL successful")
  void changeStatusToAwaitingAgentApprovalSuccessful() throws ClaimException {
    ClaimRequest spyUploadedToDocumentBatchTWClaimRequest =
        spy(uploadedToDocumentBatchTravelToWorkClaimRequest);

    when(claimRepository.findClaimByDocumentBatchRequestId(
        any(String.class)))
        .thenReturn(spyUploadedToDocumentBatchTWClaimRequest);
    when(claimRepository.save(any(TravelToWorkClaimRequest.class)))
        .thenReturn(awaitingAgentApprovalTravelToWorkClaimRequest);

    assertEquals(awaitingAgentApprovalTravelToWorkClaimRequest,
        claimService.changeStatusToAwaitingAgentApproval(requestIdRequest));

    verify(spyUploadedToDocumentBatchTWClaimRequest, times(1)).setClaimStatus(
        AWAITING_AGENT_APPROVAL);
    verify(spyUploadedToDocumentBatchTWClaimRequest, never()).setDocumentBatchRequestId(UUID);
  }

  @Test
  @DisplayName("Throw exception when trying to change claim status to AWAITING_AGENT_APPROVAL")
  void changeStatusToAwaitingAgentApprovalThrowsException() {
    when(claimRepository.findClaimByDocumentBatchRequestId(
        any(String.class)))
        .thenReturn(submittedLiftTravelToWorkClaimRequest);

    ClaimHasWrongStatusException thrown =
        assertThrows(ClaimHasWrongStatusException.class,
            () -> claimService.changeStatusToAwaitingAgentApproval(
                requestIdRequest));

    assertEquals("Cannot change the claim status to AWAITING_AGENT_APPROVAL",
        thrown.getErrorMessage());
  }

  @Test
  @DisplayName("change status to awaiting agent approval has no claim number found")
  void changeStatusToAwaitingAgentApprovalNoClaimNumberFound() {
    when(travelToWorkClaimRepository.findClaimByDocumentBatchRequestId(
        any(String.class))).thenReturn(null);
    NoClaimRecordFoundException thrown = assertThrows(NoClaimRecordFoundException.class,
        () -> claimService.changeStatusToAwaitingAgentApproval(
            requestIdRequest));

    assertEquals("No claim record found", thrown.getErrorMessage());
  }

  @Test
  @DisplayName("update workplace details for support worker successful")
  void updateWorkplaceDetailsForSupportWorkerSuccessful() throws ClaimException {
    when(claimRepository.findClaimByIdAndClaimTypeAndNino(anyLong(), anyString(), anyString()))
        .thenReturn(submittedSupportWorkerClaimForOneMonthRequest);

    when(claimRepository.save(any(SupportWorkerClaimRequest.class)))
        .thenReturn(updateWorkplaceContactForSupportWorkerClaimOneMonthRequest);

    assertEquals(updateWorkplaceContactForSupportWorkerClaimOneMonthRequest,
        claimService.updateWorkplaceDetails(updateSupportWorkerWorkplaceContactInformationRequest));
  }

  @Test
  @DisplayName("update workplace details for travel to work successful")
  void updateWorkplaceDetailsForEmployedTravelWorkerSuccessful() throws ClaimException {
    when(claimRepository.findClaimByIdAndClaimTypeAndNino(anyLong(), anyString(), anyString()))
        .thenReturn(submittedLiftForTwoMonthsTravelToWorkClaimRequest);

    when(claimRepository.save(any(TravelToWorkClaimRequest.class)))
        .thenReturn(updateWorkplaceContactForTravelToWorkClaimRequest);

    assertEquals(updateWorkplaceContactForTravelToWorkClaimRequest,
        claimService.updateWorkplaceDetails(updateSupportWorkerWorkplaceContactInformationRequest));
  }

  @Test
  @DisplayName("Throw exception when support worker is not awaiting counter sign")
  void updateWorkplaceDetailsWhereSupportWorkerIsNotAwaitingCounterSignThrowsException() {
    when(claimRepository.findClaimByIdAndClaimTypeAndNino(anyLong(), anyString(), anyString()))
        .thenReturn(submittedAcceptedSupportWorkerClaimRequest);

    ClaimCannotBeCounterSignedException thrown =
        assertThrows(ClaimCannotBeCounterSignedException.class,
            () -> claimService.updateWorkplaceDetails(
                updateSupportWorkerWorkplaceContactInformationRequest));

    assertEquals("Cannot be updated as it does not have the status AWAITING_COUNTER_SIGN",
        thrown.getErrorMessage());
  }

  @Test
  @DisplayName("Throw exception when travel to worker is not awaiting counter sign")
  void updateWorkplaceDetailsWhereTravelToWorkIsNotAwaitingCounterSignThrowsException() {
    when(claimRepository.findClaimByIdAndClaimTypeAndNino(anyLong(), anyString(), anyString()))
        .thenReturn(submittedTaxiTravelToWorkClaimRequest);

    ClaimCannotBeCounterSignedException thrown =
        assertThrows(ClaimCannotBeCounterSignedException.class,
            () -> claimService.updateWorkplaceDetails(
                updateSupportWorkerWorkplaceContactInformationRequest));

    assertEquals("Cannot be updated as it does not have the status AWAITING_COUNTER_SIGN",
        thrown.getErrorMessage());
  }

  @Test
  @DisplayName("Throw exception when claim does not exist")
  void updateWorkplaceDetailsWhereClaimDoesNotExistThrowsException() {
    when(claimRepository.findClaimByIdAndClaimTypeAndNino(anyLong(), anyString(), anyString()))
        .thenReturn(null);

    NoClaimRecordFoundException thrown = assertThrows(NoClaimRecordFoundException.class,
        () -> claimService.updateWorkplaceDetails(
            updateSupportWorkerWorkplaceContactInformationRequest));

    assertEquals("No claim record found", thrown.getErrorMessage());
  }

  @Test
  @DisplayName("Throw exception when claim is Equipment or Adaptation")
  void updateWorkplaceDetailsWhereClaimIsEquipmentOrAdaptationThrowsException() {
    when(claimRepository.findClaimByIdAndClaimTypeAndNino(anyLong(), anyString(), anyString()))
        .thenReturn(submittedEquipmentOrAdaptationRequest);
    WrongClaimOrBadRequestException thrown = assertThrows(WrongClaimOrBadRequestException.class,
        () -> claimService.updateWorkplaceDetails(
            updateSupportWorkerWorkplaceContactInformationRequest));

    assertEquals("EQUIPMENT_OR_ADAPTATION cannot update workplace details",
        thrown.getErrorMessage());
  }

  @Test
  @DisplayName("Throw exception when travel to work is self employed")
  void updateWorkplaceDetailsForSelfEmployedTravelWorkerThrowsException() {
    when(claimRepository.findClaimByIdAndClaimTypeAndNino(anyLong(), anyString(), anyString()))
        .thenReturn(invalidSelfEmployedAwaitingCounterSignTravelToWorkClaimRequest);
    WrongClaimOrBadRequestException thrown = assertThrows(WrongClaimOrBadRequestException.class,
        () -> claimService.updateWorkplaceDetails(
            updateSupportWorkerWorkplaceContactInformationRequest));

    assertEquals("Cannot update work place details for someone self-employed",
        thrown.getErrorMessage());
  }

  @Test
  @DisplayName("count Rejected Claims For Valid Nino")
  void countRejectedClaimForValidNino() {
    when(claimService.countRejectedClaimsForNino(validRequestRejectedClaimsForNino))
        .thenReturn(countRejectedClaimsForNinoCount);

    assertEquals(countRejectedClaimsForNinoCount,
        claimService.countRejectedClaimsForNino(validRequestRejectedClaimsForNino));
  }

  @Test
  @DisplayName("count Rejected Claims For InValid Nino")
  void countRejectedClaimForInvalidNino() {
    when(claimService.countRejectedClaimsForNino(nullRequestRejectedClaimsForNino))
        .thenReturn(0L);

    assertNotEquals(countRejectedClaimsForNinoCount,
        claimService.countRejectedClaimsForNino(nullRequestRejectedClaimsForNino));
  }

  @Test
  @DisplayName("count Rejected Claims For Valid Nino and ClaimType")
  void countRejectedClaimForValidNinoAndClaimType() {
    when(claimService.countRejectedClaimsForNinoAndClaimType(validRequestRejectedClaimsForNino))
        .thenReturn(List.of(countRejectedClaimsForNinoAndClaimTypeCount));

    assertEquals(List.of(countRejectedClaimsForNinoAndClaimTypeCount),
        claimService.countRejectedClaimsForNinoAndClaimType(validRequestRejectedClaimsForNino));
  }

  @Test
  @DisplayName("count Rejected Claims For InValid Nino")
  void countRejectedClaimForInvalidNinoAndClaimType() {
    when(claimService.countRejectedClaimsForNinoAndClaimType(validRequestRejectedClaimsForNino))
        .thenReturn(List.of(countRejectedClaimsForNinoAndClaimTypeCount));

    assertNotEquals(countRejectedClaimsForNinoAndClaimTypeCount,
        claimService.countRejectedClaimsForNinoAndClaimType(nullRequestRejectedClaimsForNino));
  }

  @Test
  @DisplayName("reject new EA claim when previousClaimId is set")
  void rejectEAClaimWithPreviousClaimID() {

    WrongClaimOrBadRequestException thrown = assertThrows(WrongClaimOrBadRequestException.class,
        () -> claimService.validateAndSaveActiveClaim(submittedInvalidEAdRequestWithPreviousId,
            submittedInvalidEAdRequestWithPreviousId.getId()));

    assertEquals("EQUIPMENT_OR_ADAPTATION cannot recreate previous claim",
        thrown.getErrorMessage());
    verify(claimPublisher, never()).publishToClaimBundler(any(SubmitToClaimBundlerEvent.class));
    verify(emailNotificationService, never()).notifyClaimantTheirClaimHasBeenRejected(
        any(ClaimRequest.class));
  }

  @Test
  @DisplayName("reject new TW - Self Employed claim when previousClaimId is set")
  void rejectTWEmployedClaimWithPreviousClaimID() {

    WrongClaimOrBadRequestException thrown = assertThrows(WrongClaimOrBadRequestException.class,
        () -> claimService.validateAndSaveActiveClaim(
            invalidClaimForTravelToWorkWithPreviousIdRequest,
            invalidClaimForTravelToWorkWithPreviousIdRequest.getId()));

    assertEquals(
        "TRAVEL_TO_WORK with employment status Self Employed cannot recreate previous claim",
        thrown.getErrorMessage());
    verify(claimPublisher, never()).publishToClaimBundler(any(SubmitToClaimBundlerEvent.class));
    verify(emailNotificationService, never()).notifyClaimantTheirClaimHasBeenRejected(
        any(ClaimRequest.class));
  }

  @Test
  @DisplayName("update claim status for previous claim linked to the active claim")
  void handlePreviousClaim() {

    SupportWorkerClaimRequest spyPreviousClaimRequest =
        spy(SupportWorkerTestData.submittedSupportWorkerClaimForOneMonthRequest);

    when(claimRepository.findClaimByIdAndClaimType(anyLong(), anyString())).thenReturn(
        spyPreviousClaimRequest);

    claimService.handlePreviousClaim(resubmittedSavedValidSupportWorkerClaimRequest);

    verify(spyPreviousClaimRequest, times(1)).setClaimStatus(any());

    verify(claimRepository, times(1)).save(any());
  }

  @Test
  @DisplayName("validate and save active claim")
  void validateAndSaveActiveClaimSuccessful() {

    when(claimRepository.save(any()))
        .thenReturn(submittedSupportWorkerClaimForOneMonthRequest);

    assertThat(claimService.validateAndSaveActiveClaim(validSupportWorkerClaimRequest,
        validClaimNumber), samePropertyValuesAs(submittedSupportWorkerClaimForOneMonthRequest));
  }
}

