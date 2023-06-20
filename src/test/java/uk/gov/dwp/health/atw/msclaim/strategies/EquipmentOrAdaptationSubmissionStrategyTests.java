package uk.gov.dwp.health.atw.msclaim.strategies;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static uk.gov.dwp.health.atw.msclaim.testData.EquipmentOrAdaptationTestData.claimResponseEa;
import static uk.gov.dwp.health.atw.msclaim.testData.EquipmentOrAdaptationTestData.submittedEquipmentOrAdaptationRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.EquipmentOrAdaptationTestData.validEquipmentOrAdaptationSubmitRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.validClaimNumber;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import uk.gov.dwp.health.atw.msclaim.models.enums.ClaimStatus;
import uk.gov.dwp.health.atw.msclaim.models.requests.ClaimRequest;
import uk.gov.dwp.health.atw.msclaim.models.requests.EquipmentOrAdaptationClaimRequest;
import uk.gov.dwp.health.atw.msclaim.services.ClaimService;
import uk.gov.dwp.health.atw.msclaim.services.EmailNotificationService;

@SpringBootTest(classes = EquipmentOrAdaptationsSubmissionStrategy.class)
class EquipmentOrAdaptationSubmissionStrategyTests {

  @Autowired
  EquipmentOrAdaptationsSubmissionStrategy equipmentOrAdaptationsSubmissionStrategy;

  @MockBean
  private ClaimService claimService;

  @MockBean
  private EmailNotificationService emailNotificationService;

  @Test
  @DisplayName("submit Equipment Or Adaptation claim successfully")
  void saveEquipmentOrAdaptationClaim() {
    when(claimService.validateAndSaveActiveClaim(any(EquipmentOrAdaptationClaimRequest.class),
            any(long.class)))
            .thenReturn(submittedEquipmentOrAdaptationRequest);

    ClaimRequest spyValidEquipmentOrAdaptationsClaim = spy(validEquipmentOrAdaptationSubmitRequest);

    assertThat(equipmentOrAdaptationsSubmissionStrategy.submit(spyValidEquipmentOrAdaptationsClaim,
            validClaimNumber), samePropertyValuesAs(claimResponseEa));

    verify(spyValidEquipmentOrAdaptationsClaim, times(1))
            .setClaimStatus(ClaimStatus.AWAITING_DRS_UPLOAD);

    verify(claimService, times(1)).uploadClaimToDocumentBatch(any(String.class), any(String.class));
    verify(emailNotificationService, times(1)).notifyClaimantWithNoCountersignIsBeingProcessed(any(ClaimRequest.class));
  }
}
