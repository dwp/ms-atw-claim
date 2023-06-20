package uk.gov.dwp.health.atw.msclaim.strategies;

import uk.gov.dwp.health.atw.msclaim.models.enums.ClaimType;
import uk.gov.dwp.health.atw.msclaim.models.exceptions.ClaimException;
import uk.gov.dwp.health.atw.msclaim.models.requests.ClaimRequest;
import uk.gov.dwp.health.atw.msclaim.models.responses.ClaimResponse;

public interface ClaimSubmissionStrategy {
  ClaimResponse submit(ClaimRequest claim, long claimNumberCounter);

  ClaimType getSupportedClaimType();
}
