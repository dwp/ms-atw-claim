package uk.gov.dwp.health.atw.msclaim.repositories;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import uk.gov.dwp.health.atw.msclaim.models.requests.ClaimRequest;
import uk.gov.dwp.health.atw.msclaim.models.responses.ClaimCountResponse;

@Repository
public interface ClaimRepository<T extends ClaimRequest> extends MongoRepository<T, String>,
    ClaimRepositoryCustom {
  List<T> findClaimsByNinoAndClaimType(String nino, String claimType);

  T findClaimByIdAndClaimType(long id, String claimType);

  T findClaimByIdAndClaimTypeAndNino(long id, String claimType, String nino);

  long countClaimByNinoAndClaimStatus(String nino, String claimStatus);

  List<ClaimCountResponse> countClaimByNinoAndClaimTypeAndClaimStatus(String nino,
                                                                     String claimStatus);

  T findClaimByDocumentBatchRequestId(String requestId);
}
