package uk.gov.dwp.health.atw.msclaim.models.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.newPayeeWithMissingAddress;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.newPayeeWithMissingBankDetails;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.newPayeeSetToFalseWithDetailsAndAddress;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.newPayeeSetToFalseWithDetailsAndBankDetails;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.newPayee;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.newPayeeSetToFalseWithNoAddressOrBankDetailsAndPayeeDetailsWithNoEmailAddress;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.newPayeeWithoutEmailAddress;

import javax.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = PayeeValidator.class)
class PayeeValidatorTest {

  @Autowired
  private PayeeValidator payeeValidator;

  @Mock
  private ConfirmPayee confirmPayee;

  @Mock
  private ConstraintValidatorContext constraintValidatorContext;

  @Test
  @DisplayName("new payee which is set to true has address, bank details and details successful")
  void isValid_newPayeeSetToTrueHasAddressBankDetailsAndDetails() {
    assertTrue(payeeValidator.isValid(newPayee, constraintValidatorContext));
  }

  @Test
  @DisplayName("new payee which is set to true with missing address fails validation")
  void isValid_newPayeeSetToTrueWithMissingAddress() {
    assertFalse(payeeValidator.isValid(newPayeeWithMissingAddress, constraintValidatorContext));
  }

  @Test
  @DisplayName("new payee which is set to true with missing bank details fails validation")
  void isValid_newPayeeSetToTrueWithMissingBankDetails() {
    assertFalse(payeeValidator.isValid(newPayeeWithMissingBankDetails, constraintValidatorContext));
  }

  @Test
  @DisplayName("new payee which is set true which has no payee email address")
  void isValid_newPayeeSetToTrueHasNoPayeeEmailAddress() {
    assertFalse(payeeValidator.isValid(newPayeeWithoutEmailAddress, constraintValidatorContext));
  }

  @Test
  @DisplayName("new payee which is set to false has details successful")
  void isValid_newPayeeSetToFalseHasDetails() {
    assertTrue(payeeValidator.isValid(
        newPayeeSetToFalseWithNoAddressOrBankDetailsAndPayeeDetailsWithNoEmailAddress, constraintValidatorContext));
  }

  @Test
  @DisplayName("new payee which is set to false has details and address")
  void isValid_newPayeeSetToFalseHasDetailsAndAddress() {
    assertFalse(payeeValidator.isValid(newPayeeSetToFalseWithDetailsAndAddress, constraintValidatorContext));
  }

  @Test
  @DisplayName("new payee which is set to false has details and bank details")
  void isValid_newPayeeSetToFalseHasDetailsAndBankDetails() {
    assertFalse(payeeValidator.isValid(newPayeeSetToFalseWithDetailsAndBankDetails, constraintValidatorContext));
  }
}