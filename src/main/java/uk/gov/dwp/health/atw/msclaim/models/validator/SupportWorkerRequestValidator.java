package uk.gov.dwp.health.atw.msclaim.models.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import uk.gov.dwp.health.atw.msclaim.models.SupportWorker;
import uk.gov.dwp.health.atw.msclaim.models.SupportWorkerClaim;
import uk.gov.dwp.health.atw.msclaim.models.TimeOfSupport;
import uk.gov.dwp.health.atw.msclaim.models.requests.SupportWorkerClaimRequest;

@Slf4j
public class SupportWorkerRequestValidator implements
    ConstraintValidator<ConfirmSupportWorkerRequest, SupportWorkerClaimRequest> {

  @Override
  public boolean isValid(final SupportWorkerClaimRequest supportWorkerClaimRequest,
                         final ConstraintValidatorContext constraintValidatorContext) {

    return validateWorkplaceContact(supportWorkerClaimRequest)
        && validateCost(supportWorkerClaimRequest)
        && validateHoursOfMinutesAndTimeOfSupport(supportWorkerClaimRequest);
  }

  private boolean validateWorkplaceContact(
      final SupportWorkerClaimRequest supportWorkerClaimRequest) {

    return supportWorkerClaimRequest.getWorkplaceContact().getEmailAddress() != null;
  }

  private boolean validateCost(final SupportWorkerClaimRequest supportWorkerClaimRequest) {
    return supportWorkerClaimRequest.getCost() != null;
  }

  private boolean validateHoursOfMinutesAndTimeOfSupport(
      final SupportWorkerClaimRequest supportWorkerClaimRequest) {

    for (SupportWorker supportWorker : supportWorkerClaimRequest.getClaim().values()) {
      for (SupportWorkerClaim claim : supportWorker.getClaim()) {
        Double hoursOfSupport = claim.getHoursOfSupport();
        TimeOfSupport timeOfSupport = claim.getTimeOfSupport();
        if (hoursOfSupport == null && timeOfSupport == null) {
          log.error("Both hoursOfSupport and timeOfSupport are not provided.");
          return false;
        } else if (hoursOfSupport != null && timeOfSupport != null) {
          log.error("Both hoursOfSupport and timeOfSupport are provided.");
          return false;
        }
      }
    }
    return true;
  }
}
