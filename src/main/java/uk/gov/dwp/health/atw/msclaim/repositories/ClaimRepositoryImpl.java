package uk.gov.dwp.health.atw.msclaim.repositories;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import uk.gov.dwp.health.atw.msclaim.models.responses.ClaimCountResponse;

public class ClaimRepositoryImpl implements ClaimRepositoryCustom {

  @Autowired
  MongoTemplate mongoTemplate;

  @Override
  public List<ClaimCountResponse> countClaimByNinoAndClaimTypeAndClaimStatus(String nino,
                                                                             String claimStatus) {
    MatchOperation unqiueNino = Aggregation.match(Criteria.where("nino").is(nino));
    MatchOperation counterSignedRejectedOp = Aggregation.match(Criteria.where("claimStatus")
        .is(claimStatus));
    GroupOperation groupOperation = Aggregation.group("claimType").count().as("count");
    ProjectionOperation projectionOperation = Aggregation.project("count")
        .and("claimType").previousOperation();
    SortOperation sortOperation = Aggregation.sort(Sort.by(Sort.Direction.ASC, "count"));
    Aggregation aggregation = Aggregation.newAggregation(unqiueNino, counterSignedRejectedOp,
        groupOperation, projectionOperation, sortOperation);
    AggregationResults<ClaimCountResponse> result = mongoTemplate.aggregate(aggregation,
        "claims", ClaimCountResponse.class);
    return result.getMappedResults();
  }
}
