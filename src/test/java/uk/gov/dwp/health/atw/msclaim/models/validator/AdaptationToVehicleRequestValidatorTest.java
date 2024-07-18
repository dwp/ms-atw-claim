package uk.gov.dwp.health.atw.msclaim.models.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static uk.gov.dwp.health.atw.msclaim.testData.AdaptationToVehicleTestData.invalidAdaptationToVehicleMissingCostSubmitRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.AdaptationToVehicleTestData.validAdaptationToVehicleSubmitRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.AdaptationToVehicleTestData.validAdaptationToVehicleWithNoAddressOrBankDetailsForPayeeRequest;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = AdaptationToVehicleRequestValidator.class)
class AdaptationToVehicleRequestValidatorTest {

  @Autowired
  private AdaptationToVehicleRequestValidator adaptationToVehicleRequestValidator;

  @Mock
  private ConfirmEquipmentOrAdaptationRequest confirmEquipmentOrAdaptationRequest;

  @Mock
  private ConstraintValidatorContext constraintValidatorContext;

  @Test
  @DisplayName("Adaptation To Vehicle with email address in workplace contact successful")
  void isValid_equipmentOrAdaptationsWithCostValue() {
    assertTrue(adaptationToVehicleRequestValidator.isValid(validAdaptationToVehicleSubmitRequest, constraintValidatorContext));
  }

  @Test
  @DisplayName("Adaptation To Vehicle with new payee value set to false and no address or bank details for the payee")
  void isValid_equipmentOrAdaptationsWithNewPayeeFalse() {
    assertFalse(adaptationToVehicleRequestValidator.isValid(validAdaptationToVehicleWithNoAddressOrBankDetailsForPayeeRequest, constraintValidatorContext));
  }

  @Test
  @DisplayName("missing cost value")
  void isValid_supportWorkerMissingEmailAddressForWorkplaceContact() {
    assertFalse(adaptationToVehicleRequestValidator.isValid(
        invalidAdaptationToVehicleMissingCostSubmitRequest, constraintValidatorContext));
  }
}