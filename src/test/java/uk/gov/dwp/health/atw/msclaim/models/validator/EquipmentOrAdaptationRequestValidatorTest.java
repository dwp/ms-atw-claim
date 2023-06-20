package uk.gov.dwp.health.atw.msclaim.models.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static uk.gov.dwp.health.atw.msclaim.testData.EquipmentOrAdaptationTestData.*;

import javax.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = EquipmentOrAdaptationRequestValidator.class)
class EquipmentOrAdaptationRequestValidatorTest {

    @Autowired
    private EquipmentOrAdaptationRequestValidator equipmentOrAdaptationRequestValidator;

    @Mock
    private ConfirmEquipmentOrAdaptationRequest confirmEquipmentOrAdaptationRequest;

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    @Test
    @DisplayName("equipment Or Adaptations with email address in workplace contact successful")
    void isValid_equipmentOrAdaptationsWithCostValue() {
      assertTrue(equipmentOrAdaptationRequestValidator.isValid(validEquipmentOrAdaptationSubmitRequest, constraintValidatorContext));
    }

    @Test
    @DisplayName("equipment Or Adaptations with new payee value set to false and no address or bank details for the payee")
    void isValid_equipmentOrAdaptationsWithNewPayeeFalse() {
        assertFalse(equipmentOrAdaptationRequestValidator.isValid(validEquipmentOrAdaptationWithNoAddressOrBankDetailsForPayeeRequest, constraintValidatorContext));
    }

    @Test
    @DisplayName("missing cost value")
    void isValid_supportWorkerMissingEmailAddressForWorkplaceContact() {
      assertFalse(equipmentOrAdaptationRequestValidator.isValid(
          invalidEquipmentOrAdaptationMissingCostSubmitRequest, constraintValidatorContext));
    }

}