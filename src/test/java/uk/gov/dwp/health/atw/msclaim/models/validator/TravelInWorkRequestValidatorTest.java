package uk.gov.dwp.health.atw.msclaim.models.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelInWorkTestData.invalidEmployStatusTravelInWorkSubmitRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelInWorkTestData.invalidEmployedWorkplaceContactForTravelInWorkSubmitRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelInWorkTestData.invalidEmptyEvidenceTravelInWorkSubmitRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelInWorkTestData.invalidMissingEvidenceTravelInWorkSubmitRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelInWorkTestData.invalidNoCostValueForTravelInWorkSubmitRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelInWorkTestData.invalidNoTotalMileageValueForTravelInWorkSubmitRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelInWorkTestData.invalidSelfEmployedWorkplaceContactForTravelInWorkSubmitRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelInWorkTestData.invalidTravelInWorkSubmitRequestWithoutEmploymentStatus;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelInWorkTestData.invalidTravelInWorkSubmitSelfEmployedRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelInWorkTestData.validTravelInWorkEmployedSubmitRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelInWorkTestData.validTravelInWorkSelfEmployedSubmitRequest;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = TravelInWorkRequestValidator.class)
class TravelInWorkRequestValidatorTest {

  @Autowired
  private TravelInWorkRequestValidator travelInWorkRequestValidator;

  @Mock
  private ConfirmTravelInWorkRequest confirmTravelInWorkRequest;

  @Mock
  private ConstraintValidatorContext constraintValidatorContext;


  @Test
  @DisplayName("validate Travel In Work with a valid Employed Workplace Contact successfully")
  void isValid_travelInWorkWithValidEmployedWorkplaceContactSuccessful() {
    assertTrue(travelInWorkRequestValidator.isValid(validTravelInWorkEmployedSubmitRequest, constraintValidatorContext));
  }

  @Test
  @DisplayName("validate Travel In Work with a valid Self Employed Workplace Contact successfully")
  void isValid_travelInWorkWithValidSelfEmployedWorkplaceContactSuccessful() {
    assertTrue(travelInWorkRequestValidator.isValid(validTravelInWorkSelfEmployedSubmitRequest, constraintValidatorContext));
  }

  //validateWorkplaceContact
  @Test
  @DisplayName("validate Travel In Work with a No Employment status")
  void isValid_travelInWorkWithNoEmploymentStatus() {
    assertFalse(travelInWorkRequestValidator.isValid(invalidTravelInWorkSubmitRequestWithoutEmploymentStatus, constraintValidatorContext));
  }

  @Test
  @DisplayName("travel in work with invalid employment (unemployed) status Workplace Contact")
  void isValid_travelInWorkWithInvalidEmploymentStatusWorkplaceContact() {
    assertFalse(travelInWorkRequestValidator.isValid(invalidEmployStatusTravelInWorkSubmitRequest, constraintValidatorContext));
  }

  @Test
  @DisplayName("validate Travel In Work with invalid Self Employed Workplace Contact")
  void isValid_travelInWorkWithInvalidSelfEmployedWorkplaceContact() {
    assertFalse(travelInWorkRequestValidator.isValid(invalidTravelInWorkSubmitSelfEmployedRequest, constraintValidatorContext));
  }

  @Test
  @DisplayName("validate Travel In Work with an invalid Self Employed Workplace Contact")
  void isValid_travelInWorkWithAnInvalidSelfEmployedWorkplaceContact() {
    assertFalse(travelInWorkRequestValidator.isValid(
        invalidSelfEmployedWorkplaceContactForTravelInWorkSubmitRequest, constraintValidatorContext));
  }

  @Test
  @DisplayName("validate Travel In Work with an invalid Employed (fullName and emailAddress are empty) Workplace Contact")
  void isValid_travelInWorkWithAnInvalidEmployedWorkplaceContact() {
    assertFalse(travelInWorkRequestValidator.isValid(
        invalidEmployedWorkplaceContactForTravelInWorkSubmitRequest, constraintValidatorContext));
  }

  //validateCost
  @Test
  @DisplayName("travel in work with no cost value")
  void isValid_travelInWorkWithMissingCostValue() {
    assertFalse(travelInWorkRequestValidator.isValid(invalidNoCostValueForTravelInWorkSubmitRequest, constraintValidatorContext));
  }

  //validateTotalMileage
  @Test
  @DisplayName("travel in work with no total mileage value")
  void isValid_travelInWorkWithMissingTotalMileageValue() {
    assertFalse(travelInWorkRequestValidator.isValid(invalidNoTotalMileageValueForTravelInWorkSubmitRequest, constraintValidatorContext));
  }

  //validateEvidence
  @Test
  @DisplayName("validate Travel In Work when evidence is not defined")
  void isValid_invalidMissingEvidenceToWorkSubmitRequest() {
    assertFalse(travelInWorkRequestValidator.isValid(invalidMissingEvidenceTravelInWorkSubmitRequest, constraintValidatorContext));
  }

  @Test
  @DisplayName("validate Travel In Work when evidence is defined but empty")
  void isValid_invalidEmptyEvidenceToWorkSubmitRequest() {
    assertFalse(travelInWorkRequestValidator.isValid(invalidEmptyEvidenceTravelInWorkSubmitRequest, constraintValidatorContext));
  }
}