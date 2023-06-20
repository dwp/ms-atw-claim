package uk.gov.dwp.health.atw.msclaim.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import uk.gov.dwp.health.atw.msclaim.models.enums.ClaimType;
import uk.gov.dwp.health.atw.msclaim.models.exceptions.ClaimException;
import uk.gov.dwp.health.atw.msclaim.models.requests.ClaimRequest;
import uk.gov.dwp.health.atw.msclaim.models.responses.ClaimResponse;
import uk.gov.dwp.health.atw.msclaim.strategies.ClaimSubmissionStrategy;

@Service
public class ClaimSubmissionService {
  private final Map<ClaimType, ClaimSubmissionStrategy> strategyMap = new HashMap<>();

  ClaimSubmissionService(List<ClaimSubmissionStrategy> strategies) {
    for (ClaimSubmissionStrategy strategy : strategies) {
      strategyMap.put(strategy.getSupportedClaimType(), strategy);
    }
  }

  public ClaimResponse submitClaim(ClaimRequest claimRequest, long claimNumberCounter) {
    return strategyMap.get(claimRequest.getClaimType()).submit(claimRequest, claimNumberCounter);
  }
}
