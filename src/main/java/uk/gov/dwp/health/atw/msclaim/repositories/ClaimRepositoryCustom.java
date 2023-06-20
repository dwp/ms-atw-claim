package uk.gov.dwp.health.atw.msclaim.repositories;

import java.util.List;
import uk.gov.dwp.health.atw.msclaim.models.responses.ClaimCountResponse;

public interface ClaimRepositoryCustom {

  List<ClaimCountResponse> countClaimByNinoAndClaimTypeAndClaimStatus(String nino,
                                                                     String claimStatus);
}
