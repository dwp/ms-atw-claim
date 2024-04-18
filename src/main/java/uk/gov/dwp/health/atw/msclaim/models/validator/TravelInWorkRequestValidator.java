package uk.gov.dwp.health.atw.msclaim.models.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.logging.log4j.util.Strings;
import uk.gov.dwp.health.atw.msclaim.models.WorkplaceContact;
import uk.gov.dwp.health.atw.msclaim.models.requests.TravelInWorkClaimRequest;

public class TravelInWorkRequestValidator implements
    ConstraintValidator<ConfirmTravelInWorkRequest, TravelInWorkClaimRequest> {

  @Override
  public boolean isValid(final TravelInWorkClaimRequest travelInWorkClaimRequest,
                         final ConstraintValidatorContext constraintValidatorContext) {

    return validateWorkplaceContact(travelInWorkClaimRequest)
        && validateCost(travelInWorkClaimRequest)
        && validateTotalMileage(travelInWorkClaimRequest)
        && validateEvidence(travelInWorkClaimRequest);
  }

  private boolean validateEvidence(final TravelInWorkClaimRequest travelInWorkClaimRequest) {
    return travelInWorkClaimRequest.getEvidence() != null
        && !travelInWorkClaimRequest.getEvidence().isEmpty();
  }

  private boolean validateWorkplaceContact(
      final TravelInWorkClaimRequest travelInWorkClaimRequest) {

    WorkplaceContact workplaceContact = travelInWorkClaimRequest.getWorkplaceContact();
    String employmentStatus = workplaceContact.getEmploymentStatus();
    String fullName = workplaceContact.getFullName();

    if (employmentStatus == null || (!employmentStatus.equalsIgnoreCase("employed")
        && !employmentStatus.equalsIgnoreCase("selfemployed"))) {
      return false;
    }

    if (employmentStatus.equalsIgnoreCase("employed")) {
      return !Strings.isEmpty(fullName) || !Strings.isEmpty(workplaceContact.getEmailAddress());
    } else if (employmentStatus.equalsIgnoreCase("selfemployed")) {
      return Strings.isEmpty(fullName) || Strings.isEmpty(workplaceContact.getEmailAddress());
    }

    return true;
  }

  private boolean validateCost(final TravelInWorkClaimRequest travelInWorkClaimRequest) {
    return travelInWorkClaimRequest.getCost() != null;
  }

  private boolean validateTotalMileage(final TravelInWorkClaimRequest travelInWorkClaimRequest) {
    return travelInWorkClaimRequest.getTotalMileage() != null;
  }
}
