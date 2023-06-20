package uk.gov.dwp.health.atw.msclaim.models.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.logging.log4j.util.Strings;
import uk.gov.dwp.health.atw.msclaim.models.Payee;
import uk.gov.dwp.health.atw.msclaim.models.WorkplaceContact;
import uk.gov.dwp.health.atw.msclaim.models.requests.SupportWorkerClaimRequest;
import uk.gov.dwp.health.atw.msclaim.models.requests.TravelToWorkClaimRequest;

public class TravelToWorkRequestValidator implements
    ConstraintValidator<ConfirmTravelToWorkRequest, TravelToWorkClaimRequest> {

  @Override
  public boolean isValid(final TravelToWorkClaimRequest travelToWorkClaimRequest,
                         final ConstraintValidatorContext constraintValidatorContext) {

    return validateTravelDetails(travelToWorkClaimRequest)
        && validateWorkplaceContact(travelToWorkClaimRequest)
        && validateCost(travelToWorkClaimRequest)
        && validateEvidence(travelToWorkClaimRequest);
  }

  private boolean validateEvidence(final TravelToWorkClaimRequest travelToWorkClaimRequest) {
    String howDidYouTravelForWork =
        travelToWorkClaimRequest.getTravelDetails().getHowDidYouTravelForWork();

    if (howDidYouTravelForWork.equalsIgnoreCase("lift")) {
      return travelToWorkClaimRequest.getEvidence() == null;
    } else {
      return travelToWorkClaimRequest.getEvidence() != null
          && !travelToWorkClaimRequest.getEvidence().isEmpty();
    }
  }

  private boolean validateTravelDetails(final TravelToWorkClaimRequest travelToWorkClaimRequest) {
    String howDidYouTravelForWork =
        travelToWorkClaimRequest.getTravelDetails().getHowDidYouTravelForWork();
    if (!howDidYouTravelForWork.equalsIgnoreCase("taxi")
        && !howDidYouTravelForWork.equalsIgnoreCase("lift")) {
      return false;
    }

    String journeysOrMiles = travelToWorkClaimRequest.getTravelDetails().getJourneysOrMileage();
    if (journeysOrMiles != null && (!journeysOrMiles.equalsIgnoreCase("mileage")
        && !journeysOrMiles.equalsIgnoreCase("journeys"))) {
      return false;
    }

    if (howDidYouTravelForWork.equalsIgnoreCase("taxi")) {
      return Strings.isEmpty(journeysOrMiles);
    }

    return true;
  }

  private boolean validateWorkplaceContact(
      final TravelToWorkClaimRequest travelToWorkClaimRequest) {

    WorkplaceContact workplaceContact = travelToWorkClaimRequest.getWorkplaceContact();
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

  private boolean validateCost(final TravelToWorkClaimRequest travelToWorkClaimRequest) {

    String howDidYouTravelForWork =
        travelToWorkClaimRequest.getTravelDetails().getHowDidYouTravelForWork();
    if (howDidYouTravelForWork.equalsIgnoreCase("taxi")) {
      return travelToWorkClaimRequest.getCost() != null;
    } else if (howDidYouTravelForWork.equalsIgnoreCase("lift")) {
      return travelToWorkClaimRequest.getCost() == null;
    }

    return true;
  }
}
