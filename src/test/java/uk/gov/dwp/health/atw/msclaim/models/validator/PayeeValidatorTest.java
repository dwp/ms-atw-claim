package uk.gov.dwp.health.atw.msclaim.models.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.newPayee;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.newPayeeSetToFalseWithNoAddressBankDetailsWithAccountNumberAndPayeeDetailsWithNoEmailAddress;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.newPayeeWithMissingAccountHolderForBankDetails;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.newPayeeWithMissingAddress;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.newPayeeWithMissingSortCodeForBankDetails;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.newPayeeWithoutEmailAddress;

import jakarta.validation.ConstraintValidatorContext;
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
  @DisplayName("new payee which is set true which has no payee email address")
  void isValid_newPayeeSetToTrueHasNoPayeeEmailAddress() {
    assertFalse(payeeValidator.isValid(newPayeeWithoutEmailAddress, constraintValidatorContext));
  }

  @Test
  @DisplayName("new payee which is set true which has no account holder name for bank details")
  void isValid_newPayeeSetToTrueMissingAccountHolderNameForBankDetails() {
    assertFalse(payeeValidator.isValid(newPayeeWithMissingAccountHolderForBankDetails, constraintValidatorContext));
  }

  @Test
  @DisplayName("new payee which is set true which has no sort code for bank details")
  void isValid_newPayeeSetToTrueMissingSortCodeForBankDetails() {
    assertFalse(payeeValidator.isValid(newPayeeWithMissingSortCodeForBankDetails, constraintValidatorContext));
  }

  @Test
  @DisplayName("new payee which is set to false has details with no email address and existing payee (bank details has account number) successful")
  void isValid_newPayeeSetToFalseHasDetailsAndExistingPayee() {
    assertTrue(payeeValidator.isValid(
        newPayeeSetToFalseWithNoAddressBankDetailsWithAccountNumberAndPayeeDetailsWithNoEmailAddress, constraintValidatorContext));
  }
}