package uk.gov.dwp.health.atw.msclaim.strategies;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelToWorkTestData.claimResponseTtw;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelToWorkTestData.submittedLiftForTwoMonthsTravelToWorkClaimRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelToWorkTestData.submittedLiftTravelToWorkClaimRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelToWorkTestData.submittedLiftTravelToWorkWithExistingPayeeClaimRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelToWorkTestData.submittedLiftTravelToWorkWithExistingPayeeClaimRequestOldDataModel;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelToWorkTestData.submittedTaxiTravelToWorkClaimRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelToWorkTestData.validLiftForTwoMonthsTravelToWorkSubmitRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelToWorkTestData.validLiftTravelToWorkSubmitRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelToWorkTestData.validLiftTravelToWorkWithExistingPayeeSubmitRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelToWorkTestData.validLiftTravelToWorkWithExistingPayeeSubmitRequestOldDataModel;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelToWorkTestData.validTaxiTravelToWorkSubmitRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelToWorkTestData.validTravelToWorkClaim;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import uk.gov.dwp.health.atw.msclaim.models.enums.ClaimStatus;
import uk.gov.dwp.health.atw.msclaim.models.requests.ClaimRequest;
import uk.gov.dwp.health.atw.msclaim.models.requests.TravelToWorkClaimRequest;
import uk.gov.dwp.health.atw.msclaim.services.ClaimService;
import uk.gov.dwp.health.atw.msclaim.services.EmailNotificationService;

@SpringBootTest(classes = TravelToWorkSubmissionStrategy.class)
class TravelToWorkSubmissionStrategyTests {

  @Autowired
  TravelToWorkSubmissionStrategy travelToWorkSubmissionStrategy;

  @MockBean
  private ClaimService claimService;

  @MockBean
  private EmailNotificationService emailNotificationService;

  @Test
  @DisplayName("submit Travel To Work claim with employed employment status successfully")
  void saveClaimWithTravelToWorkClaimTypeAndEmployedEmploymentStatus() {
    when(claimService.validateAndSaveActiveClaim(any(TravelToWorkClaimRequest.class),
            any(long.class)))
            .thenReturn(submittedLiftTravelToWorkClaimRequest);

    ClaimRequest spyValidTravelToWorkClaim = spy(validLiftTravelToWorkSubmitRequest);

    assertThat(travelToWorkSubmissionStrategy.submit(spyValidTravelToWorkClaim,
            validTravelToWorkClaim.getId()), samePropertyValuesAs(claimResponseTtw));

    verify(spyValidTravelToWorkClaim, times(1))
            .setClaimStatus(ClaimStatus.AWAITING_COUNTER_SIGN);

    verify(claimService, never()).uploadClaimToDocumentBatch(any(String.class), any(String.class));
    verify(emailNotificationService, times(1)).notifyWorkplaceContactOfClaimToReview(any(ClaimRequest.class));
    verify(emailNotificationService, times(1)).notifyClaimantThatRequestHasBeenSentToWorkplaceContact(any(ClaimRequest.class));
  }

  @Test
  @DisplayName("submit Travel To Work claim with existing payee with existing payee and employed employment status successfully")
  void saveClaimWithTravelToWorkClaimTypeWithExistingPayeeAndEmployedEmploymentStatus() {
    when(claimService.validateAndSaveActiveClaim(any(TravelToWorkClaimRequest.class),
            any(long.class)))
            .thenReturn(submittedLiftTravelToWorkWithExistingPayeeClaimRequest);

    ClaimRequest spyValidTravelToWorkClaim = spy(validLiftTravelToWorkWithExistingPayeeSubmitRequest);

    assertThat(travelToWorkSubmissionStrategy.submit(spyValidTravelToWorkClaim,
            validTravelToWorkClaim.getId()), samePropertyValuesAs(claimResponseTtw));

    verify(spyValidTravelToWorkClaim, times(1))
            .setClaimStatus(ClaimStatus.AWAITING_COUNTER_SIGN);

    verify(claimService, never()).uploadClaimToDocumentBatch(any(String.class), any(String.class));
    verify(emailNotificationService, times(1)).notifyWorkplaceContactOfClaimToReview(any(ClaimRequest.class));
    verify(emailNotificationService, times(1)).notifyClaimantThatRequestHasBeenSentToWorkplaceContact(any(ClaimRequest.class));
  }

  @Test
  @DisplayName("submit Travel To Work claim with existing payee with existing payee old data model")
  void saveClaimWithTravelToWorkClaimTypeWithExistingPayeeOldDataModel() {
    when(claimService.validateAndSaveActiveClaim(any(TravelToWorkClaimRequest.class),
            any(long.class)))
            .thenReturn(submittedLiftTravelToWorkWithExistingPayeeClaimRequestOldDataModel);

    ClaimRequest spyValidTravelToWorkClaim = spy(validLiftTravelToWorkWithExistingPayeeSubmitRequestOldDataModel);

    assertThat(travelToWorkSubmissionStrategy.submit(spyValidTravelToWorkClaim,
            validTravelToWorkClaim.getId()), samePropertyValuesAs(claimResponseTtw));

    verify(spyValidTravelToWorkClaim, times(1))
            .setClaimStatus(ClaimStatus.AWAITING_COUNTER_SIGN);

    verify(claimService, never()).uploadClaimToDocumentBatch(any(String.class), any(String.class));
    verify(emailNotificationService, times(1)).notifyWorkplaceContactOfClaimToReview(any(ClaimRequest.class));
    verify(emailNotificationService, times(1)).notifyClaimantThatRequestHasBeenSentToWorkplaceContact(any(ClaimRequest.class));
  }

  @Test
  @DisplayName("submit Travel To Work claim using Lift for two months with employed employment status successfully")
  void saveClaimWithTravelToWorkUsingLiftForTwoMonthClaimTypeAndEmployedEmploymentStatus() {
    when(claimService.validateAndSaveActiveClaim(any(TravelToWorkClaimRequest.class),
            any(long.class)))
            .thenReturn(submittedLiftForTwoMonthsTravelToWorkClaimRequest);

    ClaimRequest spyValidTravelToWorkClaim = spy(validLiftForTwoMonthsTravelToWorkSubmitRequest);

    assertThat(travelToWorkSubmissionStrategy.submit(spyValidTravelToWorkClaim,
            validTravelToWorkClaim.getId()), samePropertyValuesAs(claimResponseTtw));

    verify(spyValidTravelToWorkClaim, times(1))
            .setClaimStatus(ClaimStatus.AWAITING_COUNTER_SIGN);
    verify(claimService, never()).uploadClaimToDocumentBatch(any(String.class), any(String.class));
    verify(emailNotificationService, times(1)).notifyWorkplaceContactOfClaimToReview(any(ClaimRequest.class));
    verify(emailNotificationService, times(1)).notifyClaimantThatRequestHasBeenSentToWorkplaceContact(any(ClaimRequest.class));
  }

  @Test
  @DisplayName("submit Travel To Work claim Using taxi and is self employed successfully")
  void saveClaimWithTravelToWorkUsingTaxiAndSelfEmployedEmploymentStatus() {
    when(claimService.validateAndSaveActiveClaim(any(TravelToWorkClaimRequest.class),
            any(long.class)))
            .thenReturn(submittedTaxiTravelToWorkClaimRequest);

    ClaimRequest spyValidTravelToWorkClaim = spy(validTaxiTravelToWorkSubmitRequest);

    assertThat(travelToWorkSubmissionStrategy.submit(spyValidTravelToWorkClaim,
            validTravelToWorkClaim.getId()), samePropertyValuesAs(claimResponseTtw));

    verify(spyValidTravelToWorkClaim, times(1))
            .setClaimStatus(ClaimStatus.AWAITING_DRS_UPLOAD);
    verify(claimService, times(1)).uploadClaimToDocumentBatch(any(String.class), any(String.class));
    verify(emailNotificationService, times(1)).notifyClaimantWithNoCountersignIsBeingProcessed(any(ClaimRequest.class));
  }
}

