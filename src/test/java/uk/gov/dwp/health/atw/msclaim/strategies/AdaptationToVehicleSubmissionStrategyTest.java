package uk.gov.dwp.health.atw.msclaim.strategies;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static uk.gov.dwp.health.atw.msclaim.testData.AdaptationToVehicleTestData.claimResponseAv;
import static uk.gov.dwp.health.atw.msclaim.testData.AdaptationToVehicleTestData.submittedAdaptationToVehicleRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.AdaptationToVehicleTestData.submittedAdaptationToVehicleTwoClaimsRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.AdaptationToVehicleTestData.validAdaptationToVehicleSubmitRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.AdaptationToVehicleTestData.validAdaptationToVehicleTwoClaimsSubmitRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.validClaimNumber;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import uk.gov.dwp.health.atw.msclaim.models.enums.ClaimStatus;
import uk.gov.dwp.health.atw.msclaim.models.requests.AdaptationToVehicleClaimRequest;
import uk.gov.dwp.health.atw.msclaim.models.requests.ClaimRequest;
import uk.gov.dwp.health.atw.msclaim.services.ClaimService;
import uk.gov.dwp.health.atw.msclaim.services.EmailNotificationService;

@SpringBootTest(classes = AdaptationToVehicleSubmissionStrategy.class)
class AdaptationToVehicleSubmissionStrategyTest {

  @Autowired
  private AdaptationToVehicleSubmissionStrategy adaptationToVehicleSubmissionStrategy;

  @MockBean
  private ClaimService claimService;

  @MockBean
  private EmailNotificationService emailNotificationService;

  @Test
  @DisplayName("submit Adaptation To Vehicle claim successfully")
  void saveAdaptationToVehicleClaim() {
    when(claimService.validateAndSaveActiveClaim(any(AdaptationToVehicleClaimRequest.class),
        any(long.class)))
        .thenReturn(submittedAdaptationToVehicleRequest);

    ClaimRequest spyValidAdaptationToVehicleClaim = spy(validAdaptationToVehicleSubmitRequest);

    assertThat(adaptationToVehicleSubmissionStrategy.submit(spyValidAdaptationToVehicleClaim,
        validClaimNumber), samePropertyValuesAs(claimResponseAv));

    verify(spyValidAdaptationToVehicleClaim, times(1))
        .setClaimStatus(ClaimStatus.AWAITING_DRS_UPLOAD);

    verify(claimService, times(1)).uploadClaimToDocumentBatch(any(String.class), any(String.class));
    verify(emailNotificationService, times(1)).notifyClaimantWithNoCountersignIsBeingProcessed(any(ClaimRequest.class));
  }

  @Test
  @DisplayName("submit Adaptation To Vehicle with two claims successfully")
  void saveAdaptationToVehicleWithTwoClaims() {
    when(claimService.validateAndSaveActiveClaim(any(AdaptationToVehicleClaimRequest.class),
        any(long.class)))
        .thenReturn(submittedAdaptationToVehicleTwoClaimsRequest);

    ClaimRequest spyValidAdaptationToVehicleClaim = spy(validAdaptationToVehicleTwoClaimsSubmitRequest);

    assertThat(adaptationToVehicleSubmissionStrategy.submit(spyValidAdaptationToVehicleClaim,
        validClaimNumber), samePropertyValuesAs(claimResponseAv));

    verify(spyValidAdaptationToVehicleClaim, times(1))
        .setClaimStatus(ClaimStatus.AWAITING_DRS_UPLOAD);

    verify(claimService, times(1)).uploadClaimToDocumentBatch(any(String.class), any(String.class));
    verify(emailNotificationService, times(1)).notifyClaimantWithNoCountersignIsBeingProcessed(any(ClaimRequest.class));
  }
}