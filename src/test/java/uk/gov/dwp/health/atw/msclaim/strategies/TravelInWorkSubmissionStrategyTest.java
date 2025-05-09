package uk.gov.dwp.health.atw.msclaim.strategies;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelInWorkTestData.claimResponseTiw;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelInWorkTestData.submittedForTwoMonthsTravelInWorkEmployedClaimRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelInWorkTestData.submittedTravelInWorkEmployedClaimRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelInWorkTestData.submittedTravelInWorkEmployedWithExistingPayeeClaimRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelInWorkTestData.submittedTravelInWorkEmployedWithExistingPayeeClaimRequestOldDataModel;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelInWorkTestData.submittedTravelInWorkSelfEmployedClaimRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelInWorkTestData.validForTwoMonthsTravelInWorkEmployedSubmitRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelInWorkTestData.validTravelInWorkEmployedSubmitRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelInWorkTestData.validTravelInWorkEmployedWithExistingPayeeSubmitRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelInWorkTestData.validTravelInWorkEmployedWithExistingPayeeSubmitRequestOldDataModel;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelInWorkTestData.validTravelInWorkSubmitRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelInWorkTestData.validTravelInWorkClaim;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import uk.gov.dwp.health.atw.msclaim.models.enums.ClaimStatus;
import uk.gov.dwp.health.atw.msclaim.models.requests.ClaimRequest;
import uk.gov.dwp.health.atw.msclaim.models.requests.TravelInWorkClaimRequest;
import uk.gov.dwp.health.atw.msclaim.services.ClaimService;
import uk.gov.dwp.health.atw.msclaim.services.EmailNotificationService;

@SpringBootTest(classes = TravelInWorkSubmissionStrategy.class)
class TravelInWorkSubmissionStrategyTest {

  @MockBean
  private EmailNotificationService emailNotificationService;
  @MockBean
  private ClaimService claimService;

  @Autowired
  TravelInWorkSubmissionStrategy travelInWorkSubmissionStrategy;

  @Test
  @DisplayName("submit Travel In Work claim with employed employment status successfully")
  void saveClaimWithTravelInWorkClaimTypeAndEmployedEmploymentStatus() {
    when(claimService.validateAndSaveActiveClaim(any(TravelInWorkClaimRequest.class),
        any(long.class)))
        .thenReturn(submittedTravelInWorkEmployedClaimRequest);

    ClaimRequest spyValidTravelInWorkClaim = spy(validTravelInWorkEmployedSubmitRequest);

    assertThat(travelInWorkSubmissionStrategy.submit(spyValidTravelInWorkClaim,
        validTravelInWorkClaim.getId()), samePropertyValuesAs(claimResponseTiw));

    verify(spyValidTravelInWorkClaim, times(1))
        .setClaimStatus(ClaimStatus.AWAITING_COUNTER_SIGN);

    verify(claimService, never()).uploadClaimToDocumentBatch(any(String.class), any(String.class));
    verify(emailNotificationService, times(1)).notifyWorkplaceContactOfClaimToReview(any(ClaimRequest.class));
    verify(emailNotificationService, times(1)).notifyClaimantThatRequestHasBeenSentToWorkplaceContact(any(ClaimRequest.class));
  }

  @Test
  @DisplayName("submit Travel In Work claim with existing payee")
  void saveClaimWithTravelInWorkClaimWithExistingPayee() {
    when(claimService.validateAndSaveActiveClaim(any(TravelInWorkClaimRequest.class),
        any(long.class)))
        .thenReturn(submittedTravelInWorkEmployedWithExistingPayeeClaimRequest);

    ClaimRequest spyValidTravelInWorkClaim = spy(validTravelInWorkEmployedWithExistingPayeeSubmitRequest);

    assertThat(travelInWorkSubmissionStrategy.submit(spyValidTravelInWorkClaim,
        validTravelInWorkClaim.getId()), samePropertyValuesAs(claimResponseTiw));

    verify(spyValidTravelInWorkClaim, times(1))
        .setClaimStatus(ClaimStatus.AWAITING_COUNTER_SIGN);

    verify(claimService, never()).uploadClaimToDocumentBatch(any(String.class), any(String.class));
    verify(emailNotificationService, times(1)).notifyWorkplaceContactOfClaimToReview(any(ClaimRequest.class));
    verify(emailNotificationService, times(1)).notifyClaimantThatRequestHasBeenSentToWorkplaceContact(any(ClaimRequest.class));
  }

  @Test
  @DisplayName("submit Travel In Work claim with existing payee old data model")
  void saveClaimWithTravelInWorkClaimWithExistingPayeeOldDataModel() {
    when(claimService.validateAndSaveActiveClaim(any(TravelInWorkClaimRequest.class),
        any(long.class)))
        .thenReturn(submittedTravelInWorkEmployedWithExistingPayeeClaimRequestOldDataModel);

    ClaimRequest spyValidTravelInWorkClaim = spy(validTravelInWorkEmployedWithExistingPayeeSubmitRequestOldDataModel);

    assertThat(travelInWorkSubmissionStrategy.submit(spyValidTravelInWorkClaim,
        validTravelInWorkClaim.getId()), samePropertyValuesAs(claimResponseTiw));

    verify(spyValidTravelInWorkClaim, times(1))
        .setClaimStatus(ClaimStatus.AWAITING_COUNTER_SIGN);

    verify(claimService, never()).uploadClaimToDocumentBatch(any(String.class), any(String.class));
    verify(emailNotificationService, times(1)).notifyWorkplaceContactOfClaimToReview(any(ClaimRequest.class));
    verify(emailNotificationService, times(1)).notifyClaimantThatRequestHasBeenSentToWorkplaceContact(any(ClaimRequest.class));
  }

  @Test
  @DisplayName("submit Travel In Work claim for two months with employed employment status successfully")
  void saveClaimWithTravelInWorkForTwoMonthsAndEmployedEmploymentStatus() {
    when(claimService.validateAndSaveActiveClaim(any(TravelInWorkClaimRequest.class),
        any(long.class)))
        .thenReturn(submittedForTwoMonthsTravelInWorkEmployedClaimRequest);

    ClaimRequest spyValidTravelInWorkClaim = spy(validForTwoMonthsTravelInWorkEmployedSubmitRequest);

    assertThat(travelInWorkSubmissionStrategy.submit(spyValidTravelInWorkClaim,
        validTravelInWorkClaim.getId()), samePropertyValuesAs(claimResponseTiw));

    verify(spyValidTravelInWorkClaim, times(1))
        .setClaimStatus(ClaimStatus.AWAITING_COUNTER_SIGN);

    verify(claimService, never()).uploadClaimToDocumentBatch(any(String.class), any(String.class));
    verify(emailNotificationService, times(1)).notifyWorkplaceContactOfClaimToReview(any(ClaimRequest.class));
    verify(emailNotificationService, times(1)).notifyClaimantThatRequestHasBeenSentToWorkplaceContact(any(ClaimRequest.class));
  }

  @Test
  @DisplayName("submit Travel In Work claim and is self employed successfully")
  void saveClaimWithTravelInWorkAndSelfEmployedEmploymentStatus() {
    when(claimService.validateAndSaveActiveClaim(any(TravelInWorkClaimRequest.class),
        any(long.class)))
        .thenReturn(submittedTravelInWorkSelfEmployedClaimRequest);

    ClaimRequest spyValidTravelInWorkClaim = spy(validTravelInWorkSubmitRequest);

    assertThat(travelInWorkSubmissionStrategy.submit(spyValidTravelInWorkClaim,
        validTravelInWorkClaim.getId()), samePropertyValuesAs(claimResponseTiw));

    verify(spyValidTravelInWorkClaim, times(1))
        .setClaimStatus(ClaimStatus.AWAITING_DRS_UPLOAD);
    verify(claimService, times(1)).uploadClaimToDocumentBatch(any(String.class), any(String.class));
    verify(emailNotificationService, times(1)).notifyClaimantWithNoCountersignIsBeingProcessed(any(ClaimRequest.class));
  }
}