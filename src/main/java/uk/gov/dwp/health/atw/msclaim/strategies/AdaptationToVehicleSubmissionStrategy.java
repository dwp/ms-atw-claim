package uk.gov.dwp.health.atw.msclaim.strategies;

import org.springframework.stereotype.Component;
import uk.gov.dwp.health.atw.msclaim.models.enums.ClaimStatus;
import uk.gov.dwp.health.atw.msclaim.models.enums.ClaimType;
import uk.gov.dwp.health.atw.msclaim.models.requests.ClaimRequest;
import uk.gov.dwp.health.atw.msclaim.models.responses.ClaimResponse;
import uk.gov.dwp.health.atw.msclaim.services.ClaimService;
import uk.gov.dwp.health.atw.msclaim.services.EmailNotificationService;

@Component
public class AdaptationToVehicleSubmissionStrategy implements ClaimSubmissionStrategy {

  private final ClaimService claimService;
  private final EmailNotificationService emailNotificationService;

  public AdaptationToVehicleSubmissionStrategy(
          ClaimService claimService, EmailNotificationService emailNotificationService) {
    this.claimService = claimService;
    this.emailNotificationService = emailNotificationService;
  }

  @Override
  public ClaimResponse submit(ClaimRequest claim, long claimNumberCounter) {
    claim.setClaimStatus(ClaimStatus.AWAITING_DRS_UPLOAD);
    ClaimRequest response = claimService.validateAndSaveActiveClaim(claim, claimNumberCounter);

    ClaimResponse claimResponse = ClaimResponse.of(response.getId(),
        response.getClaimType());

    claimService.uploadClaimToDocumentBatch(claimResponse.getClaimReference(), claim.getNino());

    emailNotificationService.notifyClaimantWithNoCountersignIsBeingProcessed(response);

    return claimResponse;
  }

  @Override
  public ClaimType getSupportedClaimType() {
    return ClaimType.ADAPTATION_TO_VEHICLE;
  }
}
