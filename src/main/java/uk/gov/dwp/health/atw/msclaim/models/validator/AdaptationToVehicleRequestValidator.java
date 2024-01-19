package uk.gov.dwp.health.atw.msclaim.models.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import uk.gov.dwp.health.atw.msclaim.models.Payee;
import uk.gov.dwp.health.atw.msclaim.models.requests.AdaptationToVehicleClaimRequest;

public class AdaptationToVehicleRequestValidator implements
    ConstraintValidator<ConfirmAdaptationToVehicleRequest, AdaptationToVehicleClaimRequest> {

  @Override
  public boolean isValid(final AdaptationToVehicleClaimRequest adaptationToVehicleClaimRequest,
                         final ConstraintValidatorContext constraintValidatorContext) {

    return validateCost(adaptationToVehicleClaimRequest)
        && validateNewPayee(adaptationToVehicleClaimRequest);
  }

  private boolean validateCost(
      final AdaptationToVehicleClaimRequest adaptationToVehicleClaimRequest) {
    return adaptationToVehicleClaimRequest.getCost() != null;
  }

  private boolean validateNewPayee(
      final AdaptationToVehicleClaimRequest adaptationToVehicleClaimRequest) {
    Payee payee = adaptationToVehicleClaimRequest.getPayee();

    return payee.isNewPayee();
  }
}
