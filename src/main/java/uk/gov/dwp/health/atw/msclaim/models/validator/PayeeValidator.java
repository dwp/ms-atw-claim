package uk.gov.dwp.health.atw.msclaim.models.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import uk.gov.dwp.health.atw.msclaim.models.BankDetails;
import uk.gov.dwp.health.atw.msclaim.models.Payee;

public class PayeeValidator implements ConstraintValidator<ConfirmPayee, Payee> {

  @Override
  public boolean isValid(Payee payee, ConstraintValidatorContext constraintValidatorContext) {
    if (payee.isNewPayee()) {
      return validateAddress(payee)
          && validateBankDetails(payee)
          && validateDetails(payee)
          && validatePayeeDetailsEmailAddress(payee);
    }

    return !validateAddress(payee)
        && validateBankDetails(payee)
        && validateDetails(payee);
  }

  private boolean validateAddress(final Payee payee) {
    return payee.getAddress() != null;
  }

  private boolean validateBankDetails(final Payee payee) {

    BankDetails bankDetails = payee.getBankDetails();

    if (payee.isNewPayee()) {
      return bankDetails.getAccountHolderName() != null
          && bankDetails.getSortCode() != null
          && bankDetails.getAccountNumber() != null;

    } else {
      return bankDetails.getAccountNumber() != null;
    }

  }

  private boolean validateDetails(final Payee payee) {
    return payee.getDetails() != null;
  }

  private boolean validatePayeeDetailsEmailAddress(final Payee payee) {
    return payee.getDetails().getEmailAddress() != null;
  }
}
