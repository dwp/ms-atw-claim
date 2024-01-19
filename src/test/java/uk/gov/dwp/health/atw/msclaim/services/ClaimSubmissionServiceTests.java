package uk.gov.dwp.health.atw.msclaim.services;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static uk.gov.dwp.health.atw.msclaim.testData.AdaptationToVehicleTestData.validAdaptationToVehicleSubmitRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.EquipmentOrAdaptationTestData.validEquipmentOrAdaptationSubmitRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.SupportWorkerTestData.validSupportWorkerClaimRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.adaptationToVehicleClaimResponse;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.equipmentOrAdaptationsClaimResponse;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.supportWorkerClaimResponse;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.travelToWorkClaimResponse;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.validClaimNumber;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelToWorkTestData.validLiftTravelToWorkSubmitRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import uk.gov.dwp.health.atw.msclaim.models.enums.ClaimType;
import uk.gov.dwp.health.atw.msclaim.models.requests.ClaimRequest;
import uk.gov.dwp.health.atw.msclaim.models.responses.ClaimResponse;
import uk.gov.dwp.health.atw.msclaim.strategies.AdaptationToVehicleSubmissionStrategy;
import uk.gov.dwp.health.atw.msclaim.strategies.EquipmentOrAdaptationsSubmissionStrategy;
import uk.gov.dwp.health.atw.msclaim.strategies.SupportWorkerSubmissionStrategy;
import uk.gov.dwp.health.atw.msclaim.strategies.TravelToWorkSubmissionStrategy;

@SpringBootTest(classes = ClaimSubmissionService.class)
class ClaimSubmissionServiceTests {
  private EquipmentOrAdaptationsSubmissionStrategy equipmentOrAdaptationsSubmissionStrategy;
  private SupportWorkerSubmissionStrategy supportWorkerSubmissionStrategy;
  private TravelToWorkSubmissionStrategy travelToWorkSubmissionStrategy;
  private AdaptationToVehicleSubmissionStrategy adaptationToVehicleSubmissionStrategy;

  ClaimSubmissionService claimSubmissionService;

  @BeforeEach
  void setup() {
    equipmentOrAdaptationsSubmissionStrategy =
            mock(EquipmentOrAdaptationsSubmissionStrategy.class);
    when(equipmentOrAdaptationsSubmissionStrategy.submit(any(ClaimRequest.class),
            any(long.class))).thenReturn(equipmentOrAdaptationsClaimResponse);
    when(equipmentOrAdaptationsSubmissionStrategy.getSupportedClaimType()).thenReturn(ClaimType.EQUIPMENT_OR_ADAPTATION);

    supportWorkerSubmissionStrategy = mock(SupportWorkerSubmissionStrategy.class);
    when(supportWorkerSubmissionStrategy.submit(any(ClaimRequest.class),
            any(long.class))).thenReturn(supportWorkerClaimResponse);
    when(supportWorkerSubmissionStrategy.getSupportedClaimType()).thenReturn(ClaimType.SUPPORT_WORKER);

    travelToWorkSubmissionStrategy = mock(TravelToWorkSubmissionStrategy.class);
    when(travelToWorkSubmissionStrategy.submit(any(ClaimRequest.class),
            any(long.class))).thenReturn(travelToWorkClaimResponse);
    when(travelToWorkSubmissionStrategy.getSupportedClaimType()).thenReturn(ClaimType.TRAVEL_TO_WORK);

    adaptationToVehicleSubmissionStrategy = mock(AdaptationToVehicleSubmissionStrategy.class);
    when(adaptationToVehicleSubmissionStrategy.submit(any(ClaimRequest.class),
            any(long.class))).thenReturn(adaptationToVehicleClaimResponse);
    when(adaptationToVehicleSubmissionStrategy.getSupportedClaimType()).thenReturn(ClaimType.ADAPTATION_TO_VEHICLE);

    claimSubmissionService = new ClaimSubmissionService(asList(equipmentOrAdaptationsSubmissionStrategy,
            supportWorkerSubmissionStrategy, travelToWorkSubmissionStrategy, adaptationToVehicleSubmissionStrategy));
  }

  @Test
  @DisplayName("Use appropriate strategy for a claim - EA")
  void useAppropriateStrategyForEAClaim() {

    ClaimResponse claimResponse =
        claimSubmissionService.submitClaim(validEquipmentOrAdaptationSubmitRequest,
            validClaimNumber);

    assertEquals(equipmentOrAdaptationsClaimResponse.getClaimType(), claimResponse.getClaimType());
    assertEquals(equipmentOrAdaptationsClaimResponse.getClaimNumber(), claimResponse.getClaimNumber());
    assertEquals(equipmentOrAdaptationsClaimResponse.getClaimReference(), claimResponse.getClaimReference());

    verify(equipmentOrAdaptationsSubmissionStrategy, times(1)).submit(any(ClaimRequest.class),
            any(long.class));
    verify(travelToWorkSubmissionStrategy, never()).submit(any(ClaimRequest.class),
            any(long.class));
    verify(supportWorkerSubmissionStrategy, never()).submit(any(ClaimRequest.class),
            any(long.class));
  }

  @Test
  @DisplayName("Use appropriate strategy for a claim - SW")
  void useAppropriateStrategyForSWClaim() {

    ClaimResponse claimResponse =
        claimSubmissionService.submitClaim(validSupportWorkerClaimRequest,
            validClaimNumber);

    assertEquals(supportWorkerClaimResponse.getClaimType(), claimResponse.getClaimType());
    assertEquals(supportWorkerClaimResponse.getClaimNumber(), claimResponse.getClaimNumber());
    assertEquals(supportWorkerClaimResponse.getClaimReference(), claimResponse.getClaimReference());

    verify(equipmentOrAdaptationsSubmissionStrategy, never()).submit(any(ClaimRequest.class),
            any(long.class));
    verify(travelToWorkSubmissionStrategy, never()).submit(any(ClaimRequest.class),
            any(long.class));
    verify(supportWorkerSubmissionStrategy, times(1)).submit(any(ClaimRequest.class),
            any(long.class));
  }

  @Test
  @DisplayName("Use appropriate strategy for a claim - TW")
  void useAppropriateStrategyForTWClaim() {

    ClaimResponse claimResponse =
        claimSubmissionService.submitClaim(validLiftTravelToWorkSubmitRequest,
            validClaimNumber);

    assertEquals(travelToWorkClaimResponse.getClaimType(), claimResponse.getClaimType());
    assertEquals(travelToWorkClaimResponse.getClaimNumber(), claimResponse.getClaimNumber());
    assertEquals(travelToWorkClaimResponse.getClaimReference(), claimResponse.getClaimReference());

    verify(equipmentOrAdaptationsSubmissionStrategy, never()).submit(any(ClaimRequest.class),
            any(long.class));
    verify(travelToWorkSubmissionStrategy, times(1)).submit(any(ClaimRequest.class),
            any(long.class));
    verify(supportWorkerSubmissionStrategy, never()).submit(any(ClaimRequest.class),
            any(long.class));
  }

  @Test
  @DisplayName("Use appropriate strategy for a claim - AV")
  void useAppropriateStrategyForAVClaim() {

    ClaimResponse claimResponse =
        claimSubmissionService.submitClaim(validAdaptationToVehicleSubmitRequest,
            validClaimNumber);

    assertEquals(adaptationToVehicleClaimResponse.getClaimType(), claimResponse.getClaimType());
    assertEquals(adaptationToVehicleClaimResponse.getClaimNumber(), claimResponse.getClaimNumber());
    assertEquals(adaptationToVehicleClaimResponse.getClaimReference(), claimResponse.getClaimReference());

    verify(adaptationToVehicleSubmissionStrategy, times(1)).submit(any(ClaimRequest.class),
        any(long.class));
    verify(equipmentOrAdaptationsSubmissionStrategy, never()).submit(any(ClaimRequest.class),
        any(long.class));
    verify(travelToWorkSubmissionStrategy, never()).submit(any(ClaimRequest.class),
        any(long.class));
    verify(supportWorkerSubmissionStrategy, never()).submit(any(ClaimRequest.class),
        any(long.class));
  }
}
