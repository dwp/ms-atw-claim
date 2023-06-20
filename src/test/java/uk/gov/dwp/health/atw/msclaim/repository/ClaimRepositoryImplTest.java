package uk.gov.dwp.health.atw.msclaim.repository;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import uk.gov.dwp.health.atw.msclaim.models.responses.ClaimCountResponse;
import uk.gov.dwp.health.atw.msclaim.repositories.ClaimRepositoryImpl;

import java.util.Collections;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.countRejectedClaimsForNinoAndClaimTypeCount;

@SpringBootTest(classes = ClaimRepositoryImpl.class)
public class ClaimRepositoryImplTest {

  @MockBean
  private MongoTemplate mongoTemplate;

  @Autowired
  ClaimRepositoryImpl claimRepositoryImpl;

  @Test
  @DisplayName("Test that an emptyList is returned from aggregationresult")
  void testAggregationListReturnZero(){
    AggregationResults<ClaimCountResponse>  resultMock = mock(AggregationResults.class);
    when(resultMock.getMappedResults()).thenReturn(Collections.emptyList());
    when(mongoTemplate.aggregate(any(Aggregation.class), any(String.class),
        eq(ClaimCountResponse.class))).thenReturn(resultMock);
    List<ClaimCountResponse> results =
        claimRepositoryImpl.countClaimByNinoAndClaimTypeAndClaimStatus("","");
    assertEquals(0, results.size());
    assertEquals(true, results.isEmpty());
  }

  @Test
  @DisplayName("Test that appropriate list size is returned from aggregationresult")
  void testAggregationListReturnAppropriateSize(){
    List<ClaimCountResponse> list = singletonList(countRejectedClaimsForNinoAndClaimTypeCount);
    AggregationResults<ClaimCountResponse>  resultMock = mock(AggregationResults.class);
    when(resultMock.getMappedResults()).thenReturn(list);
    when(mongoTemplate.aggregate(any(Aggregation.class), any(String.class),
        eq(ClaimCountResponse.class))).thenReturn(resultMock);
    List<ClaimCountResponse> results =
        claimRepositoryImpl.countClaimByNinoAndClaimTypeAndClaimStatus("","");
    assertEquals(1, results.size());
    assertEquals(countRejectedClaimsForNinoAndClaimTypeCount.getClaimType(),
        results.get(0).getClaimType());
  }

}