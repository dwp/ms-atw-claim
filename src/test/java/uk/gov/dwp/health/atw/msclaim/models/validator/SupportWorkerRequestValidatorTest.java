package uk.gov.dwp.health.atw.msclaim.models.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static uk.gov.dwp.health.atw.msclaim.testData.SupportWorkerTestData.invalidSupportWorkerClaimRequestBothHoursOfSupportAndTimeOfSupport;
import static uk.gov.dwp.health.atw.msclaim.testData.SupportWorkerTestData.invalidSupportWorkerClaimRequestWithoutHoursOfSupportAndTimeOfSupport;
import static uk.gov.dwp.health.atw.msclaim.testData.SupportWorkerTestData.invalidSupportWorkerMissingCostValueClaimRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.SupportWorkerTestData.invalidWorkplaceContactForSupportWorkerClaimRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.SupportWorkerTestData.validSupportWorkerClaimRequest;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = SupportWorkerRequestValidator.class)
class SupportWorkerRequestValidatorTest {

  @Autowired
  private SupportWorkerRequestValidator supportWorkerRequestValidator;

  @Mock
  private ConstraintValidatorContext constraintValidatorContext;

  @Test
  @DisplayName("support worker with valid workplace contact and cost value successful")
  void isValid_supportWorkerWithValidWorkplaceContactAndCostValue() {
    assertTrue(supportWorkerRequestValidator.isValid(validSupportWorkerClaimRequest, constraintValidatorContext));
  }

  @Test
  @DisplayName("missing email address in workplace contact")
  void isValid_supportWorkerMissingEmailAddressForWorkplaceContact() {
    assertFalse(supportWorkerRequestValidator.isValid(
        invalidWorkplaceContactForSupportWorkerClaimRequest, constraintValidatorContext));
  }

  @Test
  @DisplayName("missing cost value from submission")
  void isValid_supportWorkerMissingCostValueInRequest() {
    assertFalse(supportWorkerRequestValidator.isValid(invalidSupportWorkerMissingCostValueClaimRequest, constraintValidatorContext));
  }

  @Test
  @DisplayName("missing both hoursOfSupport and timeOfSupport")
  void isValid_supportWorkerMissingBothHoursOfSupportAndTimeOfSupport() {
    assertFalse(supportWorkerRequestValidator.isValid(invalidSupportWorkerClaimRequestWithoutHoursOfSupportAndTimeOfSupport, constraintValidatorContext));
  }

  @Test
  @DisplayName("missing both hoursOfSupport and timeOfSupport")
  void isValid_supportWorkerWithHoursOfSupportAndNotTimeOfSupport() {
    assertFalse(supportWorkerRequestValidator.isValid(invalidSupportWorkerClaimRequestBothHoursOfSupportAndTimeOfSupport, constraintValidatorContext));
  }
}