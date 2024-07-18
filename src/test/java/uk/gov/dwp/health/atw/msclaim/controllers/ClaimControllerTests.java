package uk.gov.dwp.health.atw.msclaim.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static uk.gov.dwp.health.atw.msclaim.models.enums.ClaimType.ADAPTATION_TO_VEHICLE;
import static uk.gov.dwp.health.atw.msclaim.models.enums.ClaimType.EQUIPMENT_OR_ADAPTATION;
import static uk.gov.dwp.health.atw.msclaim.testData.AdaptationToVehicleTestData.claimResponseAv;
import static uk.gov.dwp.health.atw.msclaim.testData.AdaptationToVehicleTestData.invalidAVClaim;
import static uk.gov.dwp.health.atw.msclaim.testData.AdaptationToVehicleTestData.submittedAdaptationToVehicleRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.AdaptationToVehicleTestData.validAdaptationToVehicleSubmitRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.EquipmentOrAdaptationTestData.claimResponseEa;
import static uk.gov.dwp.health.atw.msclaim.testData.EquipmentOrAdaptationTestData.invalidEAClaim;
import static uk.gov.dwp.health.atw.msclaim.testData.EquipmentOrAdaptationTestData.invalidNinoClaim;
import static uk.gov.dwp.health.atw.msclaim.testData.EquipmentOrAdaptationTestData.submittedEquipmentOrAdaptationRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.EquipmentOrAdaptationTestData.validEquipmentOrAdaptationSubmitRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.SupportWorkerTestData.claimResponseSw;
import static uk.gov.dwp.health.atw.msclaim.testData.SupportWorkerTestData.submittedRejectedSupportWorkerClaimRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.SupportWorkerTestData.submittedSupportWorkerClaimForOneMonthRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.SupportWorkerTestData.validSupportWorkerClaimRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.adaptationToVehicleClaimResponse;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.claimRetrievalRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.documentUploadRequestWithRequestId;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.equipmentOrAdaptationsClaimResponse;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.invalidClaimReferenceForTravelToWorkClaim;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.invalidClaimReferenceNinoForTravelToWorkClaim;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.rejectedClaimsForNinoAndClaimTypeCountResponse;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.rejectedClaimsForNinoCountResponse;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.supportWorkerClaimResponse;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.travelInWorkClaimResponse;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.travelToWorkClaimResponse;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.validClaimReferenceForAdaptationToVehicleClaim;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.validClaimReferenceForEquipmentOrAdaptationClaim;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.validClaimReferenceForTravelToWorkClaim;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.validClaimReferenceNinoForTravelToWorkClaim;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.validRequestRejectedClaimsForNino;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.validRequestRejectedClaimsForNinoAndClaimType;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelInWorkTestData.claimResponseTiw;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelInWorkTestData.submittedAcceptedTravelInWorkClaimRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelInWorkTestData.submittedRejectedTravelInWorkClaimRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelInWorkTestData.validTravelInWorkEmployedSubmitRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelToWorkTestData.awaitingAgentApprovalTravelToWorkClaimRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelToWorkTestData.claimResponseTtw;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelToWorkTestData.drsErrorTravelToWorkClaimRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelToWorkTestData.existingTravelToWorkClaimRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelToWorkTestData.submittedAcceptedTravelToWorkClaimRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelToWorkTestData.travelToWorkWithMissingEvidenceClaimRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelToWorkTestData.uploadedToDocumentBatchTravelToWorkClaimRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelToWorkTestData.validLiftTravelToWorkSubmitRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.UpdateWorkplaceContactInformationTestData.updateSupportWorkerWorkplaceContactInformationRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.UpdateWorkplaceContactInformationTestData.updateWorkplaceContactForSupportWorkerClaimOneMonthRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.WorkplaceContactTestData.invalidAcceptedAdaptationToVehicleClaim;
import static uk.gov.dwp.health.atw.msclaim.testData.WorkplaceContactTestData.invalidRejectAdaptationToVehicleClaim;
import static uk.gov.dwp.health.atw.msclaim.testData.WorkplaceContactTestData.invalidRejectedSupportWorkerWorkplaceContactReasonOver300Char;
import static uk.gov.dwp.health.atw.msclaim.testData.WorkplaceContactTestData.invalidAcceptedEquipmentOrAdaptationClaim;
import static uk.gov.dwp.health.atw.msclaim.testData.WorkplaceContactTestData.validAcceptedTravelInWorkClaim;
import static uk.gov.dwp.health.atw.msclaim.testData.WorkplaceContactTestData.validAcceptedTravelToWorkClaim;
import static uk.gov.dwp.health.atw.msclaim.testData.WorkplaceContactTestData.invalidRejectEquipmentOrAdaptationClaim;
import static uk.gov.dwp.health.atw.msclaim.testData.WorkplaceContactTestData.validRejectedSupportWorkerClaim;
import static uk.gov.dwp.health.atw.msclaim.testData.WorkplaceContactTestData.validRejectedTravelInWorkClaim;
import static uk.gov.dwp.health.atw.msclaim.utils.TestUtils.asJsonString;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import uk.gov.dwp.health.atw.msclaim.controllers.utils.CustomExceptionHandler;
import uk.gov.dwp.health.atw.msclaim.controllers.utils.ServiceExceptionHandler;
import uk.gov.dwp.health.atw.msclaim.models.ClaimReferenceNinoRequest;
import uk.gov.dwp.health.atw.msclaim.models.ClaimReferenceRequest;
import uk.gov.dwp.health.atw.msclaim.models.ClaimRetrievalRequest;
import uk.gov.dwp.health.atw.msclaim.models.DocumentUploadRequest;
import uk.gov.dwp.health.atw.msclaim.models.NinoRequest;
import uk.gov.dwp.health.atw.msclaim.models.RequestIdRequest;
import uk.gov.dwp.health.atw.msclaim.models.enums.CounterSignType;
import uk.gov.dwp.health.atw.msclaim.models.exceptions.ClaimCannotBeCounterSignedException;
import uk.gov.dwp.health.atw.msclaim.models.exceptions.ClaimHasWrongStatusException;
import uk.gov.dwp.health.atw.msclaim.models.exceptions.NoClaimRecordFoundException;
import uk.gov.dwp.health.atw.msclaim.models.exceptions.WrongClaimOrBadRequestException;
import uk.gov.dwp.health.atw.msclaim.models.requests.AdaptationToVehicleClaimRequest;
import uk.gov.dwp.health.atw.msclaim.models.requests.EquipmentOrAdaptationClaimRequest;
import uk.gov.dwp.health.atw.msclaim.models.requests.SupportWorkerClaimRequest;
import uk.gov.dwp.health.atw.msclaim.models.requests.TravelInWorkClaimRequest;
import uk.gov.dwp.health.atw.msclaim.models.requests.TravelToWorkClaimRequest;
import uk.gov.dwp.health.atw.msclaim.models.requests.WorkplaceContactRequest;
import uk.gov.dwp.health.atw.msclaim.repositories.DatabaseSequenceRepository;
import uk.gov.dwp.health.atw.msclaim.services.ClaimService;
import uk.gov.dwp.health.atw.msclaim.services.ClaimSubmissionService;

@SpringBootTest(classes = ClaimController.class)
@EnableWebMvc
@AutoConfigureMockMvc
@ImportAutoConfiguration({ServiceExceptionHandler.class, CustomExceptionHandler.class})
class ClaimControllerTests {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ClaimService claimService;

  @MockBean
  private ClaimSubmissionService claimSubmissionService;

  @MockBean
  private DatabaseSequenceRepository databaseSequenceRepository;

  @Test
  @DisplayName("/submit creates record successfully - Equipment or Adaptation")
  void submitRecordOkSae() throws Exception {

    when(databaseSequenceRepository.getNextValueInSequence(any(String.class))).thenReturn(5L);

    when(claimSubmissionService.submitClaim(any(EquipmentOrAdaptationClaimRequest.class), eq(5L)))
        .thenReturn(claimResponseEa);

    mockMvc.perform(post("/submit")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(validEquipmentOrAdaptationSubmitRequest)))
        .andExpect(status().isCreated())
        .andExpect(content().json(asJsonString(equipmentOrAdaptationsClaimResponse)));
  }

  @Test
  @DisplayName("/submit creates record successfully - Adaptation To Vehicle")
  void submitRecordOkAv() throws Exception {

    when(databaseSequenceRepository.getNextValueInSequence(any(String.class))).thenReturn(5L);

    when(claimSubmissionService.submitClaim(any(AdaptationToVehicleClaimRequest.class), eq(5L)))
        .thenReturn(claimResponseAv);

    mockMvc.perform(post("/submit")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(validAdaptationToVehicleSubmitRequest)))
        .andExpect(status().isCreated())
        .andExpect(content().json(asJsonString(adaptationToVehicleClaimResponse)));
  }

  @Test
  @DisplayName("/submit creates record successfully -TW")
  void submitRecordOkTW() throws Exception {

    when(databaseSequenceRepository.getNextValueInSequence(any(String.class))).thenReturn(5L);

    when(claimSubmissionService.submitClaim(any(TravelToWorkClaimRequest.class), eq(5L)))
        .thenReturn(claimResponseTtw);

    mockMvc.perform(post("/submit")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(validLiftTravelToWorkSubmitRequest)))
        .andExpect(status().isCreated())
        .andExpect(content().json(asJsonString(travelToWorkClaimResponse)));
  }

  @Test
  @DisplayName("/submit creates record successfully -TIW")
  void submitRecordOkTiW() throws Exception {

    when(databaseSequenceRepository.getNextValueInSequence(any(String.class))).thenReturn(5L);

    when(claimSubmissionService.submitClaim(any(TravelInWorkClaimRequest.class), eq(5L)))
        .thenReturn(claimResponseTiw);

    mockMvc.perform(post("/submit")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(validTravelInWorkEmployedSubmitRequest)))
        .andExpect(status().isCreated())
        .andExpect(content().json(asJsonString(travelInWorkClaimResponse)));
  }

  @Test
  @DisplayName("/submit creates record for successfully - SW")
  void submitRecordForSupportWorkerOk() throws Exception {
    when(databaseSequenceRepository.getNextValueInSequence(any(String.class))).thenReturn(5L);

    when(claimSubmissionService.submitClaim(any(SupportWorkerClaimRequest.class), eq(5L)))
        .thenReturn(claimResponseSw);

    mockMvc.perform(post("/submit")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(validSupportWorkerClaimRequest)))
        .andExpect(status().isCreated())
        .andExpect(content().json(asJsonString(supportWorkerClaimResponse)));
  }

  @Test
  @DisplayName("/submit for EA with previousclaimid and return 400 error -  EA")
  void submitRecordWithPreviousClaimId() throws Exception {
    mockMvc.perform(post("/submit")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(invalidEAClaim)))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("/submit for AV with previousClaimId and return 400 error -  AV")
  void submitAdaptionToVehicleRecordWithPreviousClaimId() throws Exception {
    mockMvc.perform(post("/submit")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(invalidAVClaim)))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("/submit failed due to missing ClaimStatus")
  void failToSubmitDueToMissingClaimStatus() throws Exception {

    mockMvc.perform(post("/submit")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"nino\": \"AA370773A\"}"))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("/submit failed due to invalid nino length")
  void failDueToInvalidNinoLength() throws Exception {

    mockMvc.perform(post("/submit")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(invalidNinoClaim)))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("/submit failed to duplicate claim number")
  void failToSubmitDueToDuplicateClaimNumber() throws Exception {
    when(databaseSequenceRepository.getNextValueInSequence(any(String.class))).thenReturn(5L);

    when(claimSubmissionService.submitClaim(any(TravelToWorkClaimRequest.class), eq(5L)))
        .thenThrow(new DuplicateKeyException("Duplicate Claim number"));

    mockMvc.perform(post("/submit")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(validLiftTravelToWorkSubmitRequest)))
        .andExpect(status().isConflict());
  }

  @Test
  @DisplayName("/submit failed to duplicate claim number for support worker")
  void failToSubmitDueToDuplicateClaimNumberForSupportWorker() throws Exception {
    when(databaseSequenceRepository.getNextValueInSequence(any(String.class))).thenReturn(5L);

    when(claimSubmissionService.submitClaim(any(SupportWorkerClaimRequest.class), eq(5L)))
        .thenThrow(new DuplicateKeyException("Duplicate Claim number"));

    mockMvc.perform(post("/submit")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(validSupportWorkerClaimRequest)))
        .andExpect(status().isConflict());
  }

  @Test
  @DisplayName("/submit failed due to missing required field")
  void failToSubmitDueToMissingRequiredField() throws Exception {
    mockMvc.perform(post("/submit")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(travelToWorkWithMissingEvidenceClaimRequest)))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("/accept updated claim record successfully")
  void acceptWorkplaceContactSuccessful() throws Exception {
    when(claimService.counterSignHandler(eq(CounterSignType.ACCEPT),
        any(WorkplaceContactRequest.class)))
        .thenReturn(submittedAcceptedTravelToWorkClaimRequest);

    mockMvc.perform(put("/accept")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(validAcceptedTravelToWorkClaim)))
        .andExpect(status().isNoContent());
  }

  @Test
  @DisplayName("/accept updated claim record successfully - TiW")
  void acceptTiWWorkplaceContactSuccessful() throws Exception {
    when(claimService.counterSignHandler(eq(CounterSignType.ACCEPT),
        any(WorkplaceContactRequest.class)))
        .thenReturn(submittedAcceptedTravelInWorkClaimRequest);

    mockMvc.perform(put("/accept")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(validAcceptedTravelInWorkClaim)))
        .andExpect(status().isNoContent());
  }

  @Test
  @DisplayName("/accept failed due claim number not found")
  void acceptWorkplaceContactWithClaimReferenceNotFound() throws Exception {
    when(claimService.counterSignHandler(eq(CounterSignType.ACCEPT),
        any(WorkplaceContactRequest.class)))
        .thenThrow(new NoClaimRecordFoundException());

    mockMvc.perform(put("/accept")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(validAcceptedTravelToWorkClaim)))
        .andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("/accept failed due to claim status not being awaiting countersign")
  void acceptWorkplaceContactWithClaimStatusNotBeingAwaitingCountersign() throws Exception {
    when(claimService.counterSignHandler(eq(CounterSignType.ACCEPT),
        any(WorkplaceContactRequest.class)))
        .thenThrow(new ClaimHasWrongStatusException("Claim with status "
            + "AWAITING_COUNTER_SIGNATURE cannot be counter signed"));

    mockMvc.perform(put("/accept")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(validAcceptedTravelToWorkClaim)))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Claim with status "
            + "AWAITING_COUNTER_SIGNATURE cannot be counter signed"));
  }

  @Test
  @DisplayName("/accept failed due to claim type being equipment and adaptations")
  void acceptWorkplaceContactWithClaimTypeIsEquipmentAndAdaptations() throws Exception {
    when(claimService.counterSignHandler(eq(CounterSignType.ACCEPT),
        any(WorkplaceContactRequest.class)))
        .thenThrow(new WrongClaimOrBadRequestException(
            submittedEquipmentOrAdaptationRequest.getClaimType() +
                " cannot be counter sign accepted"));

    mockMvc.perform(put("/accept")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(invalidAcceptedEquipmentOrAdaptationClaim)))
        .andExpect(status().isBadRequest())
        .andExpect(content().string("EQUIPMENT_OR_ADAPTATION cannot be counter sign accepted"));
  }

  @Test
  @DisplayName("/accept failed due to claim type being adaptation to vehicle")
  void acceptWorkplaceContactWithClaimTypeIsAdaptationToVehicle() throws Exception {
    when(claimService.counterSignHandler(eq(CounterSignType.ACCEPT),
        any(WorkplaceContactRequest.class)))
        .thenThrow(new WrongClaimOrBadRequestException(
            submittedAdaptationToVehicleRequest.getClaimType() +
                " cannot be counter sign accepted"));

    mockMvc.perform(put("/accept")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(invalidAcceptedAdaptationToVehicleClaim)))
        .andExpect(status().isBadRequest())
        .andExpect(content().string("ADAPTATION_TO_VEHICLE cannot be counter sign accepted"));
  }

  @Test
  @DisplayName("/reject updated claim record successfully -SW")
  void rejectWorkplaceContactSuccessful() throws Exception {
    when(claimService.counterSignHandler(eq(CounterSignType.REJECT),
        any(WorkplaceContactRequest.class)))
        .thenReturn(submittedRejectedSupportWorkerClaimRequest);

    mockMvc.perform(put("/reject")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(validRejectedSupportWorkerClaim)))
        .andExpect(status().isNoContent());
  }

  @Test
  @DisplayName("/reject updated claim record successfully -TiW")
  void rejectTiWWorkplaceContactSuccessful() throws Exception {
    when(claimService.counterSignHandler(eq(CounterSignType.REJECT),
        any(WorkplaceContactRequest.class)))
        .thenReturn(submittedRejectedTravelInWorkClaimRequest);

    mockMvc.perform(put("/reject")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(validRejectedTravelInWorkClaim)))
        .andExpect(status().isNoContent());
  }

  @Test
  @DisplayName("/reject failed due claim number not found")
  void rejectWorkplaceContactWithClaimReferenceNotFound() throws Exception {
    when(claimService.counterSignHandler(eq(CounterSignType.REJECT),
        any(WorkplaceContactRequest.class)))
        .thenThrow(new NoClaimRecordFoundException());

    mockMvc.perform(put("/reject")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(validRejectedSupportWorkerClaim)))
        .andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("/reject failed due to claim status not being awaiting countersign")
  void rejectWorkplaceContactWithClaimStatusNotBeingAwaitingCountersign() throws Exception {
    when(claimService.counterSignHandler(eq(CounterSignType.REJECT),
        any(WorkplaceContactRequest.class)))
        .thenThrow(new ClaimHasWrongStatusException("Claim with status "
            + "AWAITING_COUNTER_SIGNATURE cannot be counter signed"));

    mockMvc.perform(put("/reject")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(validRejectedSupportWorkerClaim)))
        .andExpect(status().isNotFound()).andExpect(content().string("Claim with status "
            + "AWAITING_COUNTER_SIGNATURE cannot be counter signed"));
  }

  @Test
  @DisplayName("/reject failed due to claim type being equipment and adaptations")
  void rejectWorkplaceContactWithClaimTypeIsEquipmentAndAdaptations() throws Exception {
    when(claimService.counterSignHandler(eq(CounterSignType.REJECT),
        any(WorkplaceContactRequest.class)))
        .thenThrow(new WrongClaimOrBadRequestException(
            submittedEquipmentOrAdaptationRequest.getClaimType() +
                " cannot be counter sign rejected"));

    mockMvc.perform(put("/reject")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(invalidRejectEquipmentOrAdaptationClaim)))
        .andExpect(status().isBadRequest())
        .andExpect(content().string("EQUIPMENT_OR_ADAPTATION cannot be counter sign rejected"));
  }

  @Test
  @DisplayName("/reject failed due to claim type being adaptation to vehicle")
  void rejectWorkplaceContactWithClaimTypeIsAdaptationToVehicle() throws Exception {
    when(claimService.counterSignHandler(eq(CounterSignType.REJECT),
        any(WorkplaceContactRequest.class)))
        .thenThrow(new WrongClaimOrBadRequestException(
            submittedAdaptationToVehicleRequest.getClaimType() +
                " cannot be counter sign rejected"));

    mockMvc.perform(put("/reject")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(invalidRejectAdaptationToVehicleClaim)))
        .andExpect(status().isBadRequest())
        .andExpect(content().string("ADAPTATION_TO_VEHICLE cannot be counter sign rejected"));
  }

  @Test
  @DisplayName("/reject failed due workplace contact reason being over 300 characters")
  void rejectWorkplaceContactWithReasonOver300Characters() throws Exception {
    when(claimService.counterSignHandler(eq(CounterSignType.REJECT),
        any(WorkplaceContactRequest.class)))
        .thenThrow(new WrongClaimOrBadRequestException(
            submittedSupportWorkerClaimForOneMonthRequest.getClaimType() +
                " cannot be counter sign rejected"));

    mockMvc.perform(put("/reject")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(invalidRejectedSupportWorkerWorkplaceContactReasonOver300Char)))
        .andExpect(status().isBadRequest())
        .andDo(MockMvcResultHandlers.print())
        .andExpect(content().string(""));
  }

  @Test
  @DisplayName("/claims-for-nino return list of claims for Nino")
  void retrieveClaimsForNino() throws Exception {
    when(claimService.findClaimsForNinoAndType(any(ClaimRetrievalRequest.class)))
        .thenReturn(List.of(submittedEquipmentOrAdaptationRequest));

    mockMvc.perform(post("/claims-for-nino-and-type")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(claimRetrievalRequest)))
        .andExpect(status().isOk())
        .andExpect(content().json(asJsonString(List.of(submittedEquipmentOrAdaptationRequest))));
  }

  @Test
  @DisplayName("/claims-for-nino failed due to conflict in Mongo")
  void returnNotFoundForNino() throws Exception {
    when(claimService.findClaimsForNinoAndType(any(ClaimRetrievalRequest.class)))
        .thenReturn(List.of());

    mockMvc.perform(post("/claims-for-nino-and-type")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(claimRetrievalRequest)))
        .andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("/claim-for-reference-and-nino returns claim request for claim reference")
  void retrieveClaimRequestFromClaimReference() throws Exception {
    when(claimService.findClaimRequestForClaimReferenceAndNino(any(ClaimReferenceNinoRequest.class)))
        .thenReturn(existingTravelToWorkClaimRequest);

    mockMvc.perform(post("/claim-for-reference-and-nino")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(validClaimReferenceNinoForTravelToWorkClaim)))
        .andExpect(status().isOk())
        .andExpect(content().json(asJsonString(existingTravelToWorkClaimRequest)));
  }

  @Test
  @DisplayName("/claim-for-reference-and-nino failed due to invalid claim reference")
  void returnNotFoundForClaimRequestFromClaimReference() throws Exception {
    when(claimService.findClaimRequestForClaimReferenceAndNino(any(ClaimReferenceNinoRequest.class)))
        .thenThrow(new WrongClaimOrBadRequestException("Claim number is not a number " +
            invalidClaimReferenceForTravelToWorkClaim.getClaimReference()));

    mockMvc.perform(post("/claim-for-reference-and-nino")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(invalidClaimReferenceNinoForTravelToWorkClaim)))
        .andExpect(status().isBadRequest())
        .andExpect(content().string("Claim number is not a number TW0001SW"));
  }

  @Test
  @DisplayName("/claim-to-workplace-contact returns claim request for claim reference")
  void retrieveClaimRequestToWorkplaceContactFromClaimReference() throws Exception {
    when(claimService.findClaimRequestToWorkplaceContact(any(ClaimReferenceRequest.class)))
        .thenReturn(existingTravelToWorkClaimRequest);

    mockMvc.perform(post("/claim-to-workplace-contact")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(validClaimReferenceForTravelToWorkClaim)))
        .andExpect(status().isOk())
        .andExpect(content().json(asJsonString(existingTravelToWorkClaimRequest)));
  }

  @Test
  @DisplayName("/claim-to-workplace-contact failed due to invalid claim reference -EA")
  void returnNotFoundForClaimRequestToWorkplaceContactFromClaimReferenceForEA() throws Exception {
    when(claimService.findClaimRequestToWorkplaceContact(any(ClaimReferenceRequest.class)))
        .thenThrow(new WrongClaimOrBadRequestException(
            EQUIPMENT_OR_ADAPTATION + " cannot be counter signed"));

    mockMvc.perform(post("/claim-to-workplace-contact")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(invalidClaimReferenceForTravelToWorkClaim)))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("/claim-to-workplace-contact failed due to Equipment Or Adaptation claim type")
  void returnNotFoundForClaimRequestToWorkplaceContactFromEquipmentOrAdaptationClaimType()
      throws Exception {
    when(claimService.findClaimRequestToWorkplaceContact(any(ClaimReferenceRequest.class)))
        .thenThrow(new WrongClaimOrBadRequestException(
            EQUIPMENT_OR_ADAPTATION + " cannot be counter signed"));

    mockMvc.perform(post("/claim-to-workplace-contact")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(validClaimReferenceForEquipmentOrAdaptationClaim)))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("/claim-to-workplace-contact failed due to Adaptation To Vehicle claim type")
  void returnNotFoundForClaimRequestToWorkplaceContactFromAdaptationToVehicleClaimType()
      throws Exception {
    when(claimService.findClaimRequestToWorkplaceContact(any(ClaimReferenceRequest.class)))
        .thenThrow(new WrongClaimOrBadRequestException(
            ADAPTATION_TO_VEHICLE + " cannot be counter signed"));

    mockMvc.perform(post("/claim-to-workplace-contact")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(validClaimReferenceForAdaptationToVehicleClaim)))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("/claim-to-workplace-contact failed due to claim status incorrect")
  void returnNotFoundForClaimRequestToWorkplaceContactFromInvalidClaimStatus() throws Exception {
    when(claimService.findClaimRequestToWorkplaceContact(any(ClaimReferenceRequest.class)))
        .thenThrow(new ClaimCannotBeCounterSignedException(
            "Claim cannot be counter signed with status " +
                validAcceptedTravelToWorkClaim.getClaimType()));

    mockMvc.perform(post("/claim-to-workplace-contact")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(validClaimReferenceForTravelToWorkClaim)))
        .andExpect(status().isLocked())
        .andExpect(content().string("Claim cannot be counter signed with status " +
            validAcceptedTravelToWorkClaim.getClaimType()));
  }

  @Test
  @DisplayName("/change-status/uploaded-to-document-batch successful")
  void changeClaimStatusToUploadedToDocumentBatch() throws Exception {
    when(claimService.changeStatusToUploadedToDocumentBatch(any(DocumentUploadRequest.class)))
            .thenReturn(uploadedToDocumentBatchTravelToWorkClaimRequest);

    mockMvc.perform(put("/change-status/uploaded-to-document-batch")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(documentUploadRequestWithRequestId)))
        .andExpect(status().isNoContent());
  }

  @Test
  @DisplayName("/change-status/uploaded-to-document-batch failed due claim number not found")
  void changeClaimStatusToUploadedToDocumentBatchWithClaimReferenceNotFound() throws Exception {
    when(claimService.changeStatusToUploadedToDocumentBatch(any(DocumentUploadRequest.class)))
        .thenThrow(new NoClaimRecordFoundException());

    mockMvc.perform(put("/change-status/uploaded-to-document-batch")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(documentUploadRequestWithRequestId)))
        .andExpect(status().isNotFound())
        .andExpect(content().string("No claim record found"));
  }

  @Test
  @DisplayName("/change-status/uploaded-to-document-batch failed due to claim status not being awaiting drs upload")
  void changeClaimStatusToUploadedToDocumentBatchWithClaimStatusNotBeingAwaitingDrsUpload() throws Exception {
    when(claimService.changeStatusToUploadedToDocumentBatch(any(DocumentUploadRequest.class)))
        .thenThrow(new ClaimHasWrongStatusException("Cannot change the claim status to UPLOADED_TO_DOCUMENT_BATCH"));

    mockMvc.perform(put("/change-status/uploaded-to-document-batch")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(documentUploadRequestWithRequestId)))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Cannot change the claim status to UPLOADED_TO_DOCUMENT_BATCH"));
  }

  @Test
  @DisplayName("/change-status/drs-error successful")
  void changeClaimStatusToDrsErrorSuccessful() throws Exception {
    when(claimService.changeStatusToDrsError(any(RequestIdRequest.class)))
        .thenReturn(drsErrorTravelToWorkClaimRequest);

    mockMvc.perform(put("/change-status/drs-error")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(documentUploadRequestWithRequestId)))
        .andExpect(status().isNoContent());
  }

  @Test
  @DisplayName("/change-status/drs-error failed due claim number not found")
  void changeClaimStatusToDrsErrorWithClaimReferenceNotFound() throws Exception {
    when(claimService.changeStatusToDrsError(any(RequestIdRequest.class)))
        .thenThrow(new NoClaimRecordFoundException());

    mockMvc.perform(put("/change-status/drs-error")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(documentUploadRequestWithRequestId)))
        .andExpect(status().isNotFound())
        .andExpect(content().string("No claim record found"));
  }

  @Test
  @DisplayName("/change-status/drs-error failed due to claim status not being upload to document batch")
  void changeClaimStatusToDrsErrorWithClaimStatusNotBeingAwaitingDrsUpload() throws Exception {
    when(claimService.changeStatusToDrsError(any()))
        .thenThrow(new ClaimHasWrongStatusException("Cannot change the claim status to DRS_ERROR"));

    mockMvc.perform(put("/change-status/drs-error")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(documentUploadRequestWithRequestId)))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Cannot change the claim status to DRS_ERROR"));
  }

  @Test
  @DisplayName("/change-status/awaiting-agent-approval successful")
  void changeClaimStatusToAwaitingAgentApprovalSuccessful() throws Exception {
    when(claimService.changeStatusToAwaitingAgentApproval(any()))
        .thenReturn(awaitingAgentApprovalTravelToWorkClaimRequest);

    mockMvc.perform(put("/change-status/awaiting-agent-approval")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(documentUploadRequestWithRequestId)))
        .andExpect(status().isNoContent());
  }

  @Test
  @DisplayName("/change-status/awaiting-agent-approval failed due claim number not found")
  void changeClaimStatusToAwaitingAgentApprovalWithClaimReferenceNotFound() throws Exception {
    when(claimService.changeStatusToAwaitingAgentApproval(any()))
        .thenThrow(new NoClaimRecordFoundException());

    mockMvc.perform(put("/change-status/awaiting-agent-approval")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(documentUploadRequestWithRequestId)))
        .andExpect(status().isNotFound())
        .andExpect(content().string("No claim record found"));
  }

  @Test
  @DisplayName("/change-status/awaiting-agent-approval failed due to claim status not being upload to document batch")
  void changeClaimStatusToAwaitingAgentApprovalNotBeingAwaitingDrsUpload() throws Exception {
    when(claimService.changeStatusToAwaitingAgentApproval(any()))
        .thenThrow(new ClaimHasWrongStatusException("Cannot change the claim status to AWAITING_AGENT_APPROVAL"));

    mockMvc.perform(put("/change-status/awaiting-agent-approval")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(documentUploadRequestWithRequestId)))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Cannot change the claim status to AWAITING_AGENT_APPROVAL"));
  }

  @Test
  @DisplayName("/update-workplace-contact-details passed with required information")
  void updateWorkplaceWithRequiredInformationSuccessful() throws Exception {
    when(claimService.updateWorkplaceDetails(any()))
            .thenReturn(updateWorkplaceContactForSupportWorkerClaimOneMonthRequest);

    mockMvc.perform(put("/update-workplace-contact-details")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(updateSupportWorkerWorkplaceContactInformationRequest)))
            .andExpect(status().isNoContent());
  }

  @Test
  @DisplayName("/update-workplace-contact-details failed due claim number not found")
  void updateWorkplaceWithRequiredInformationWithClaimReferenceNotFound() throws Exception {
    when(claimService.updateWorkplaceDetails(any()))
            .thenThrow(new NoClaimRecordFoundException());

    mockMvc.perform(put("/update-workplace-contact-details")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(updateSupportWorkerWorkplaceContactInformationRequest)))
            .andExpect(status().isNotFound())
            .andExpect(content().string("No claim record found"));
  }

  @Test
  @DisplayName("/claims-for-nino return no of rejected claims for a nino")
  void countRejectedClaimsForValidNino() throws Exception {
    when(claimService.countRejectedClaimsForNino(any(NinoRequest.class)))
        .thenReturn(rejectedClaimsForNinoCountResponse.getCount());

    mockMvc.perform(post("/count-rejected-claims")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(validRequestRejectedClaimsForNino)))
        .andExpect(status().isOk())
        .andExpect(content().json(asJsonString(rejectedClaimsForNinoCountResponse)));
  }

  @Test
  @DisplayName("/count-rejected-claims-by-claim-type return no of rejected claims for a nino and claimtype")
  void countRejectedClaimsForValidNinoAndClaimType() throws Exception {
    when(claimService.countRejectedClaimsForNinoAndClaimType(any(NinoRequest.class)))
        .thenReturn(List.of(rejectedClaimsForNinoAndClaimTypeCountResponse));

    mockMvc.perform(post("/count-rejected-claims-by-claim-type")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(validRequestRejectedClaimsForNinoAndClaimType)))
        .andExpect(status().isOk())
        .andExpect(content().json(asJsonString(List.of(rejectedClaimsForNinoAndClaimTypeCountResponse))));
  }

}
