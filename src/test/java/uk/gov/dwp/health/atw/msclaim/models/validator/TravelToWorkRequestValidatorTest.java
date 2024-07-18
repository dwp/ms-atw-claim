package uk.gov.dwp.health.atw.msclaim.models.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelToWorkTestData.invalidEmployStatusTravelToWorkSubmitRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelToWorkTestData.invalidEmployedWorkplaceContactForTaxiTravelToWorkSubmitRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelToWorkTestData.invalidJourneyOrMilesTypeToWorkSubmitRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelToWorkTestData.invalidLiftAndCostValueForTravelToWorkSubmitRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelToWorkTestData.invalidLiftTravelToWorkSubmitRequestWithoutEmploymentStatus;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelToWorkTestData.invalidLiftWithEvidenceToWorkSubmitRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelToWorkTestData.invalidSelfEmployedWorkplaceContactForTaxiTravelToWorkSubmitRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelToWorkTestData.invalidTaxiAndCostValueForTravelToWorkSubmitRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelToWorkTestData.invalidTaxiEmptyEvidenceToWorkSubmitRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelToWorkTestData.invalidTaxiMissingEvidenceToWorkSubmitRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelToWorkTestData.invalidTravelDetailsForTaxiWithSelfEmployedWorkplaceContactTravelToWorkSubmitRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelToWorkTestData.invalidTravelToWorkSubmitSelfEmployedRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelToWorkTestData.invalidWayToTravelToWorkSubmitRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelToWorkTestData.validLiftTravelToWorkSubmitRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.TravelToWorkTestData.validTaxiTravelToWorkSubmitRequest;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = TravelToWorkRequestValidator.class)
class TravelToWorkRequestValidatorTest {

  @Autowired
  private TravelToWorkRequestValidator travelToWorkRequestValidator;

  @Mock
  private ConfirmTravelToWorkRequest confirmTravelToWorkRequest;

  @Mock
  private ConstraintValidatorContext constraintValidatorContext;

  @Test
  @DisplayName("validate Travel To Work for a Lift with a valid Employed Workplace Contact successfully")
  void isValid_travelToWorkForLiftWithValidEmployedWorkplaceContact() {
    assertTrue(travelToWorkRequestValidator.isValid(validLiftTravelToWorkSubmitRequest, constraintValidatorContext));
  }

  @Test
  @DisplayName("validate Travel To Work for a Taxi with a valid Self Employed Workplace Contact successfully")
  void isValid_travelToWorkForTaxiWithNoJourneyOrMilesWithValidSelfEmployedWorkplaceContact() {
    assertTrue(travelToWorkRequestValidator.isValid(validTaxiTravelToWorkSubmitRequest, constraintValidatorContext));
  }

  @Test
  @DisplayName("validate Travel To Work with invalid Self Employed Workplace Contact")
  void isValid_travelToWorkWithInvalidSelfEmployedWorkplaceContact() {
    assertFalse(travelToWorkRequestValidator.isValid(invalidTravelToWorkSubmitSelfEmployedRequest, constraintValidatorContext));
  }

  @Test
  @DisplayName("validate Travel To Work for a Lift with a No Employment status")
  void isValid_travelToWorkForTaxiWithNoEmploymentStatus() {
    assertFalse(travelToWorkRequestValidator.isValid(invalidLiftTravelToWorkSubmitRequestWithoutEmploymentStatus, constraintValidatorContext));
  }

  @Test
  @DisplayName("validate Travel To Work for a unacceptable way to travel to work")
  void isValid_travelToWorkForUnacceptableWayToTravelToWork() {
    assertFalse(travelToWorkRequestValidator.isValid(invalidWayToTravelToWorkSubmitRequest, constraintValidatorContext));
  }

  @Test
  @DisplayName("validate Travel To Work for a unaccepted Journey Or Miles travel to work")
  void isValid_travelToWorkForUnacceptableJourneyOrMilesToTravelToWork() {
    assertFalse(travelToWorkRequestValidator.isValid(invalidJourneyOrMilesTypeToWorkSubmitRequest, constraintValidatorContext));
  }

  @Test
  @DisplayName("validate Travel To Work with invalid Travel Details (journeyOrMiles not empty) for Taxi")
  void isValid_travelToWorkWithInvalidTravelDetailsForTaxiAndValidSelfEmployedWorkplaceContact() {
    assertFalse(travelToWorkRequestValidator.isValid(
        invalidTravelDetailsForTaxiWithSelfEmployedWorkplaceContactTravelToWorkSubmitRequest, constraintValidatorContext));
  }

  @Test
  @DisplayName("validate Travel To Work for a taxi with an invalid Self Employed Workplace Contact")
  void isValid_travelToWorkWithAnInvalidSelfEmployedWorkplaceContact() {
    assertFalse(travelToWorkRequestValidator.isValid(
        invalidSelfEmployedWorkplaceContactForTaxiTravelToWorkSubmitRequest, constraintValidatorContext));
  }

  @Test
  @DisplayName("validate Travel To Work for a taxi with an invalid Employed (fullName and emailAddress are empty) Workplace Contact")
  void isValid_travelToWorkWithAnInvalidEmployedWorkplaceContact() {
    assertFalse(travelToWorkRequestValidator.isValid(
        invalidEmployedWorkplaceContactForTaxiTravelToWorkSubmitRequest, constraintValidatorContext));
  }

  @Test
  @DisplayName("validate Travel To Work for a taxi when evidence is not defined")
  void isValid_invalidTaxiMissingEvidenceToWorkSubmitRequest() {
    assertFalse(travelToWorkRequestValidator.isValid(invalidTaxiMissingEvidenceToWorkSubmitRequest, constraintValidatorContext));
  }

  @Test
  @DisplayName("validate Travel To Work for a lift but incorrectly provide evidence")
  void isValid_invalidLiftWithEvidenceToWorkSubmitRequest() {
    assertFalse(travelToWorkRequestValidator.isValid(invalidLiftWithEvidenceToWorkSubmitRequest, constraintValidatorContext));
  }

  @Test
  @DisplayName("validate Travel To Work for a taxi when evidence is defined but empty")
  void isValid_invalidTaxiEmptyEvidenceToWorkSubmitRequest() {
    assertFalse(travelToWorkRequestValidator.isValid(invalidTaxiEmptyEvidenceToWorkSubmitRequest, constraintValidatorContext));
  }

  @Test
  @DisplayName("travel to work with invalid employment status Workplace Contact")
  void isValid_travelToWorkWithInvalidEmploymentStatusWorkplaceContact() {
    assertFalse(travelToWorkRequestValidator.isValid(invalidEmployStatusTravelToWorkSubmitRequest, constraintValidatorContext));
  }

  @Test
  @DisplayName("travel to work for a taxi and no cost value")
  void isValid_travelToWorkWithTaxiAndMissingCostValue() {
    assertFalse(travelToWorkRequestValidator.isValid(invalidTaxiAndCostValueForTravelToWorkSubmitRequest, constraintValidatorContext));
  }

  @Test
  @DisplayName("travel to work with cost value")
  void isValid_travelToWorkWithLiftAndCostValue() {
    assertFalse(travelToWorkRequestValidator.isValid(invalidLiftAndCostValueForTravelToWorkSubmitRequest, constraintValidatorContext));
  }
}