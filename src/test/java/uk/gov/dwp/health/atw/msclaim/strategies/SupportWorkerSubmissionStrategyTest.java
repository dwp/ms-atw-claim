package uk.gov.dwp.health.atw.msclaim.strategies;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static uk.gov.dwp.health.atw.msclaim.testData.SupportWorkerTestData.claimResponseSw;
import static uk.gov.dwp.health.atw.msclaim.testData.SupportWorkerTestData.claimResponseWithClaimNumber11Sw;
import static uk.gov.dwp.health.atw.msclaim.testData.SupportWorkerTestData.reSubmittedClaimResponseSw;
import static uk.gov.dwp.health.atw.msclaim.testData.SupportWorkerTestData.resubmittedSupportWorkerClaimForOneMonthRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.SupportWorkerTestData.resubmittedValidSupportWorkerClaimRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.SupportWorkerTestData.submittedSupportWorkerClaimForOneMonthRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.SupportWorkerTestData.submittedSupportWorkerClaimForOneMonthWithoutNameOfSupportRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.SupportWorkerTestData.submittedSupportWorkerClaimForTwoMonthsRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.SupportWorkerTestData.submittedSupportWorkerClaimRequestWithNameOfSupportOnSupportWorkerClaim;
import static uk.gov.dwp.health.atw.msclaim.testData.SupportWorkerTestData.submittedSupportWorkerClaimWithExistingPayee;
import static uk.gov.dwp.health.atw.msclaim.testData.SupportWorkerTestData.submittedSupportWorkerClaimWithExistingPayeeOldDataModel;
import static uk.gov.dwp.health.atw.msclaim.testData.SupportWorkerTestData.validSupportWorkerClaimRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.SupportWorkerTestData.validSupportWorkerClaimRequestWithNameOfSupportOnSupportWorkerClaim;
import static uk.gov.dwp.health.atw.msclaim.testData.SupportWorkerTestData.validSupportWorkerClaimRequestWithoutNameOfSupport;
import static uk.gov.dwp.health.atw.msclaim.testData.SupportWorkerTestData.validSupportWorkerClaimWithExistingPayeeOldDataModelRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.SupportWorkerTestData.validSupportWorkerClaimWithExistingPayeeRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.resubmitedValidClaimNumber;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.validClaimNumber;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.validClaimNumberDoubleDigits;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import uk.gov.dwp.health.atw.msclaim.models.enums.ClaimStatus;
import uk.gov.dwp.health.atw.msclaim.models.requests.ClaimRequest;
import uk.gov.dwp.health.atw.msclaim.services.ClaimService;
import uk.gov.dwp.health.atw.msclaim.services.EmailNotificationService;

@SpringBootTest(classes = SupportWorkerSubmissionStrategy.class)
class SupportWorkerSubmissionStrategyTest {

  @Autowired
  SupportWorkerSubmissionStrategy supportWorkerSubmissionStrategy;

  @MockBean
  EmailNotificationService emailNotificationService;

  @MockBean
  ClaimService claimService;

  @Test
  @DisplayName("submit Support Worker claim for one month successfully")
  void saveClaimWithSupportWorkerForOneMonthClaimType() {

    ClaimRequest spyValidSupportWorkerClaim = spy(validSupportWorkerClaimRequest);

    when(claimService.validateAndSaveActiveClaim(any(ClaimRequest.class), anyLong())).thenReturn(submittedSupportWorkerClaimForOneMonthRequest);

    assertThat(supportWorkerSubmissionStrategy.submit(spyValidSupportWorkerClaim,
        validClaimNumber), samePropertyValuesAs(claimResponseSw));

    verify(spyValidSupportWorkerClaim, times(1)).setClaimStatus(ClaimStatus.AWAITING_COUNTER_SIGN);
    verify(claimService, times(1)).handlePreviousClaim(any(ClaimRequest.class));
    verify(emailNotificationService, times(1)).notifyWorkplaceContactOfClaimToReview(any(ClaimRequest.class));
    verify(emailNotificationService, times(1)).notifyClaimantThatRequestHasBeenSentToWorkplaceContact(any(ClaimRequest.class));
  }

  @Test
  @DisplayName("submit Support Worker claim with existing payee")
  void saveClaimWithSupportWorkerWithAnExistingPayee() {

    ClaimRequest spyValidSupportWorkerClaim = spy(validSupportWorkerClaimWithExistingPayeeRequest);

    when(claimService.validateAndSaveActiveClaim(any(ClaimRequest.class), anyLong())).thenReturn(submittedSupportWorkerClaimWithExistingPayee);

    assertThat(supportWorkerSubmissionStrategy.submit(spyValidSupportWorkerClaim,
        validClaimNumber), samePropertyValuesAs(claimResponseSw));



    verify(spyValidSupportWorkerClaim, times(1)).setClaimStatus(ClaimStatus.AWAITING_COUNTER_SIGN);
    verify(claimService, times(1)).handlePreviousClaim(any(ClaimRequest.class));
    verify(emailNotificationService, times(1)).notifyWorkplaceContactOfClaimToReview(any(ClaimRequest.class));
    verify(emailNotificationService, times(1)).notifyClaimantThatRequestHasBeenSentToWorkplaceContact(any(ClaimRequest.class));
  }

  @Test
  @DisplayName("submit Support Worker claim with existing payee old data model")
  void saveClaimWithSupportWorkerWithAnExistingPayeeOldDataModel() {

    ClaimRequest spyValidSupportWorkerClaim = spy(validSupportWorkerClaimWithExistingPayeeOldDataModelRequest);

    when(claimService.validateAndSaveActiveClaim(any(ClaimRequest.class), anyLong())).thenReturn(submittedSupportWorkerClaimWithExistingPayeeOldDataModel);

    assertThat(supportWorkerSubmissionStrategy.submit(spyValidSupportWorkerClaim,
        validClaimNumber), samePropertyValuesAs(claimResponseSw));

    verify(spyValidSupportWorkerClaim, times(1)).setClaimStatus(ClaimStatus.AWAITING_COUNTER_SIGN);
    verify(claimService, times(1)).handlePreviousClaim(any(ClaimRequest.class));
    verify(emailNotificationService, times(1)).notifyWorkplaceContactOfClaimToReview(any(ClaimRequest.class));
    verify(emailNotificationService, times(1)).notifyClaimantThatRequestHasBeenSentToWorkplaceContact(any(ClaimRequest.class));
  }

  @Test
  @DisplayName("submit Support Worker claim for one month without name of support successfully")
  void saveClaimWithSupportWorkerForOneMonthWithoutNameOfSupportClaimType() {

    ClaimRequest spyValidSupportWorkerClaim = spy(validSupportWorkerClaimRequestWithoutNameOfSupport);

    when(claimService.validateAndSaveActiveClaim(any(ClaimRequest.class), anyLong())).thenReturn(submittedSupportWorkerClaimForOneMonthWithoutNameOfSupportRequest);

    assertThat(supportWorkerSubmissionStrategy.submit(spyValidSupportWorkerClaim,
        validClaimNumber), samePropertyValuesAs(claimResponseSw));

    verify(spyValidSupportWorkerClaim, times(1)).setClaimStatus(ClaimStatus.AWAITING_COUNTER_SIGN);
    verify(claimService, times(1)).handlePreviousClaim(any(ClaimRequest.class));
    verify(emailNotificationService, times(1)).notifyWorkplaceContactOfClaimToReview(any(ClaimRequest.class));
    verify(emailNotificationService, times(1)).notifyClaimantThatRequestHasBeenSentToWorkplaceContact(any(ClaimRequest.class));
  }

  @Test
  @DisplayName("submit Support Worker claim for two months successfully")
  void saveClaimWithSupportWorkerForTwoMonthClaimType() {

    ClaimRequest spyValidSupportWorkerClaim = spy(validSupportWorkerClaimRequest);

    when(claimService.validateAndSaveActiveClaim(any(ClaimRequest.class), anyLong())).thenReturn(submittedSupportWorkerClaimForTwoMonthsRequest);

    assertThat(supportWorkerSubmissionStrategy.submit(spyValidSupportWorkerClaim,
        validClaimNumberDoubleDigits), samePropertyValuesAs(claimResponseWithClaimNumber11Sw));

    verify(spyValidSupportWorkerClaim, times(1)).setClaimStatus(ClaimStatus.AWAITING_COUNTER_SIGN);
    verify(claimService, times(1)).handlePreviousClaim(any(ClaimRequest.class));
    verify(emailNotificationService, times(1)).notifyWorkplaceContactOfClaimToReview(any(ClaimRequest.class));
    verify(emailNotificationService, times(1)).notifyClaimantThatRequestHasBeenSentToWorkplaceContact(any(ClaimRequest.class));
  }

  @Test
  @DisplayName("submit Support Worker claim where nameOfSupport is on Support worker claim")
  void saveSubmitSupportWorkerClaimWithNameOfSupportOnSupportWorkerClaim() {

    ClaimRequest spyValidSupportWorkerClaim = spy(validSupportWorkerClaimRequestWithNameOfSupportOnSupportWorkerClaim);

    when(claimService.validateAndSaveActiveClaim(any(ClaimRequest.class), anyLong())).thenReturn(submittedSupportWorkerClaimRequestWithNameOfSupportOnSupportWorkerClaim);

    assertThat(supportWorkerSubmissionStrategy.submit(spyValidSupportWorkerClaim,
        validClaimNumberDoubleDigits), samePropertyValuesAs(claimResponseWithClaimNumber11Sw));

    verify(spyValidSupportWorkerClaim, times(1)).setClaimStatus(ClaimStatus.AWAITING_COUNTER_SIGN);
    verify(claimService, times(1)).handlePreviousClaim(any(ClaimRequest.class));
    verify(emailNotificationService, times(1)).notifyWorkplaceContactOfClaimToReview(any(ClaimRequest.class));
    verify(emailNotificationService, times(1)).notifyClaimantThatRequestHasBeenSentToWorkplaceContact(any(ClaimRequest.class));
  }

  @Test
  @DisplayName("submit Support Worker claim successfully")
  void saveResubmittedSupportWorkerClaim() {
    ClaimRequest spyResubmittedValidSupportWorkerClaim = spy(
        resubmittedValidSupportWorkerClaimRequest);

    when(claimService.validateAndSaveActiveClaim(any(ClaimRequest.class), anyLong())).thenReturn(resubmittedSupportWorkerClaimForOneMonthRequest);

    assertThat(supportWorkerSubmissionStrategy.submit(spyResubmittedValidSupportWorkerClaim,
        resubmitedValidClaimNumber), samePropertyValuesAs(reSubmittedClaimResponseSw));

    verify(spyResubmittedValidSupportWorkerClaim, times(1)).setClaimStatus(ClaimStatus.AWAITING_COUNTER_SIGN);
    verify(claimService, times(1)).handlePreviousClaim(any(ClaimRequest.class));
    verify(emailNotificationService, times(1)).notifyWorkplaceContactOfClaimToReview(any(ClaimRequest.class));
    verify(emailNotificationService, times(1)).notifyClaimantThatRequestHasBeenSentToWorkplaceContact(any(ClaimRequest.class));
  }
}