package uk.gov.dwp.health.atw.msclaim.models.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import uk.gov.dwp.health.atw.msclaim.models.Payee;
import uk.gov.dwp.health.atw.msclaim.models.requests.EquipmentOrAdaptationClaimRequest;

public class EquipmentOrAdaptationRequestValidator implements
    ConstraintValidator<ConfirmEquipmentOrAdaptationRequest, EquipmentOrAdaptationClaimRequest> {

  @Override
  public boolean isValid(final EquipmentOrAdaptationClaimRequest equipmentOrAdaptationClaimRequest,
                         final ConstraintValidatorContext constraintValidatorContext) {

    return validateCost(equipmentOrAdaptationClaimRequest)
        && validateNewPayee(equipmentOrAdaptationClaimRequest);
  }

  private boolean validateCost(
      final EquipmentOrAdaptationClaimRequest equipmentOrAdaptationClaimRequest) {
    return equipmentOrAdaptationClaimRequest.getCost() != null;
  }

  private boolean validateNewPayee(
      final EquipmentOrAdaptationClaimRequest equipmentOrAdaptationClaimRequest) {
    Payee payee = equipmentOrAdaptationClaimRequest.getPayee();

    return payee.isNewPayee();
  }
}
