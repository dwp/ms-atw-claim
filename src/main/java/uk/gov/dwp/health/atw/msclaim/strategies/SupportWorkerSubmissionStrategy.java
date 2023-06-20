package uk.gov.dwp.health.atw.msclaim.strategies;

import static uk.gov.dwp.health.atw.msclaim.models.enums.ClaimStatus.AWAITING_COUNTER_SIGN;

import org.springframework.stereotype.Component;
import uk.gov.dwp.health.atw.msclaim.models.enums.ClaimType;
import uk.gov.dwp.health.atw.msclaim.models.requests.ClaimRequest;
import uk.gov.dwp.health.atw.msclaim.models.responses.ClaimResponse;
import uk.gov.dwp.health.atw.msclaim.services.ClaimService;
import uk.gov.dwp.health.atw.msclaim.services.EmailNotificationService;

@Component
public class SupportWorkerSubmissionStrategy implements ClaimSubmissionStrategy {

  private final EmailNotificationService emailNotificationService;
  private final ClaimService claimService;

  public SupportWorkerSubmissionStrategy(EmailNotificationService emailNotificationService,
                                         ClaimService claimService) {
    this.emailNotificationService = emailNotificationService;
    this.claimService = claimService;
  }

  @Override
  public ClaimResponse submit(ClaimRequest claim, long claimNumberCounter) {
    claim.setClaimStatus(AWAITING_COUNTER_SIGN);
    ClaimRequest claimRequest = claimService.validateAndSaveActiveClaim(claim, claimNumberCounter);

    claimService.handlePreviousClaim(claim);

    ClaimResponse claimResponse = ClaimResponse.of(claimRequest.getId(),
        claimRequest.getClaimType());

    emailNotificationService.notifyWorkplaceContactOfClaimToReview(claimRequest);

    emailNotificationService.notifyClaimantThatRequestHasBeenSentToWorkplaceContact(claimRequest);

    return claimResponse;
  }

  @Override
  public ClaimType getSupportedClaimType() {
    return ClaimType.SUPPORT_WORKER;
  }
}
