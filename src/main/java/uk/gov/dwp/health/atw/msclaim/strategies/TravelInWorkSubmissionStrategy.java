package uk.gov.dwp.health.atw.msclaim.strategies;

import static uk.gov.dwp.health.atw.msclaim.models.enums.ClaimStatus.AWAITING_COUNTER_SIGN;
import static uk.gov.dwp.health.atw.msclaim.models.enums.ClaimStatus.AWAITING_DRS_UPLOAD;

import org.springframework.stereotype.Component;
import uk.gov.dwp.health.atw.msclaim.models.enums.ClaimType;
import uk.gov.dwp.health.atw.msclaim.models.enums.EmploymentStatus;
import uk.gov.dwp.health.atw.msclaim.models.requests.ClaimRequest;
import uk.gov.dwp.health.atw.msclaim.models.requests.TravelInWorkClaimRequest;
import uk.gov.dwp.health.atw.msclaim.models.responses.ClaimResponse;
import uk.gov.dwp.health.atw.msclaim.services.ClaimService;
import uk.gov.dwp.health.atw.msclaim.services.EmailNotificationService;

@Component
public class TravelInWorkSubmissionStrategy implements ClaimSubmissionStrategy {

  private final EmailNotificationService emailNotificationService;
  private final ClaimService claimService;

  public TravelInWorkSubmissionStrategy(EmailNotificationService emailNotificationService,
                                        ClaimService claimService) {
    this.emailNotificationService = emailNotificationService;
    this.claimService = claimService;
  }

  @Override
  public ClaimResponse submit(ClaimRequest claimRequest, long claimNumberCounter) {

    if (claimRequest instanceof TravelInWorkClaimRequest) {

      TravelInWorkClaimRequest travelInWorkClaimRequest = (TravelInWorkClaimRequest) claimRequest;
      String employmentStatus = travelInWorkClaimRequest
          .getWorkplaceContact().getEmploymentStatus();

      if (EmploymentStatus.valueOf(employmentStatus.toUpperCase()) == EmploymentStatus.EMPLOYED) {
        claimRequest.setClaimStatus(AWAITING_COUNTER_SIGN);

        ClaimRequest employedRequest =
            claimService.validateAndSaveActiveClaim(claimRequest, claimNumberCounter);
        claimService.handlePreviousClaim(claimRequest);

        ClaimResponse employedResponse = ClaimResponse.of(employedRequest.getId(),
            employedRequest.getClaimType());

        emailNotificationService.notifyWorkplaceContactOfClaimToReview(employedRequest);

        emailNotificationService.notifyClaimantThatRequestHasBeenSentToWorkplaceContact(
            employedRequest);

        return employedResponse;
      } else {
        claimRequest.setClaimStatus(AWAITING_DRS_UPLOAD);

        ClaimRequest selfEmployedRequest =
            claimService.validateAndSaveActiveClaim(claimRequest, claimNumberCounter);

        ClaimResponse selfEmployedResponse = ClaimResponse.of(selfEmployedRequest.getId(),
            selfEmployedRequest.getClaimType());
        claimService.uploadClaimToDocumentBatch(selfEmployedResponse.getClaimReference(),
            claimRequest.getNino());

        emailNotificationService.notifyClaimantWithNoCountersignIsBeingProcessed(
            selfEmployedRequest);

        return selfEmployedResponse;
      }
    }
    return new ClaimResponse();
  }

  @Override
  public ClaimType getSupportedClaimType() {
    return ClaimType.TRAVEL_IN_WORK;
  }
}
