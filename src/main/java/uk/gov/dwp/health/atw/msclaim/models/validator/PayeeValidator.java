package uk.gov.dwp.health.atw.msclaim.models.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
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
        && !validateBankDetails(payee)
        && validateDetails(payee);
  }

  private boolean validateAddress(final Payee payee) {
    return payee.getAddress() != null;
  }

  private boolean validateBankDetails(final Payee payee) {
    return payee.getBankDetails() != null;
  }

  private boolean validateDetails(final Payee payee) {
    return payee.getDetails() != null;
  }

  private boolean validatePayeeDetailsEmailAddress(final Payee payee) {
    return payee.getDetails().getEmailAddress() != null;
  }
}
