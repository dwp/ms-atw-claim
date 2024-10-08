package uk.gov.dwp.health.atw.msclaim.testData;

import static java.util.Collections.singletonMap;
import static uk.gov.dwp.health.atw.msclaim.testData.SupportWorkerTestData.supportWorkerClaimForOneMonthWithTwoDaysOfSupport;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.ATW_NUMBER;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.COST;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.DECLARATION_VERSION;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.NINO;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.address;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.claimant;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.evidences;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.newPayee;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.travelDetailsForLiftWithMiles;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.travelInWorkClaimForOneMonth;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.travelToWorkClaimForOneMonth;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.validClaimNumber;

import java.util.Collections;
import java.util.Map;
import uk.gov.dwp.health.atw.msclaim.models.WorkplaceContact;
import uk.gov.dwp.health.atw.msclaim.models.enums.ClaimStatus;
import uk.gov.dwp.health.atw.msclaim.models.enums.ClaimType;
import uk.gov.dwp.health.atw.msclaim.models.requests.SupportWorkerClaimRequest;
import uk.gov.dwp.health.atw.msclaim.models.requests.TravelInWorkClaimRequest;
import uk.gov.dwp.health.atw.msclaim.models.requests.TravelToWorkClaimRequest;
import uk.gov.dwp.health.atw.msclaim.models.requests.UpdateWorkplaceContactRequest;

public class UpdateWorkplaceContactInformationTestData {

  public static WorkplaceContact updatedCounterSign = WorkplaceContact.builder()
      .fullName("full name")
      .emailAddress("emails@emailaddress.com")
      .address(address)
      .build();

  public static WorkplaceContact updatedTravelToWorkEmployedWorkplaceContact =
      WorkplaceContact.builder()
          .employmentStatus("employed")
          .fullName("full name")
          .emailAddress("emails@emailaddress.com")
          .address(address)
          .build();

  public static UpdateWorkplaceContactRequest
      updateSupportWorkerWorkplaceContactInformationRequest =
      UpdateWorkplaceContactRequest.builder()
          .nino(NINO)
          .claimReference("SW000" + validClaimNumber)
          .workplaceContact(updatedCounterSign)
          .build();

  public static UpdateWorkplaceContactRequest
      updateTravelToWorkWorkplaceContactInformationRequest =
      UpdateWorkplaceContactRequest.builder()
          .nino(NINO)
          .claimReference("TW000" + validClaimNumber)
          .workplaceContact(updatedCounterSign)
          .build();

  public static UpdateWorkplaceContactRequest
      updateTravelInWorkWorkplaceContactInformationRequest =
      UpdateWorkplaceContactRequest.builder()
          .nino(NINO)
          .claimReference("TIW000" + validClaimNumber)
          .workplaceContact(updatedCounterSign)
          .build();

  public static SupportWorkerClaimRequest
      updateWorkplaceContactForSupportWorkerClaimOneMonthRequest =
      SupportWorkerClaimRequest.builder()
          .id(validClaimNumber)
          .nino(NINO)
          .claimType(ClaimType.SUPPORT_WORKER)
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .cost(COST)
          .evidence(evidences)
          .payee(newPayee)
          .claimStatus(ClaimStatus.AWAITING_COUNTER_SIGN)
          .claim(singletonMap("0", supportWorkerClaimForOneMonthWithTwoDaysOfSupport))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(updatedCounterSign)
          .build();

  public static TravelToWorkClaimRequest updateWorkplaceContactForTravelToWorkClaimRequest =
      TravelToWorkClaimRequest.builder()
          .id(validClaimNumber)
          .nino(NINO)
          .claimType(ClaimType.TRAVEL_TO_WORK)
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .cost(COST)
          .payee(newPayee)
          .claimStatus(ClaimStatus.AWAITING_COUNTER_SIGN)
          .travelDetails(travelDetailsForLiftWithMiles)
          .claim(Map.of("0", travelToWorkClaimForOneMonth, "1", travelToWorkClaimForOneMonth))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(updatedTravelToWorkEmployedWorkplaceContact)
          .build();

  public static TravelInWorkClaimRequest updateWorkplaceContactForTravelInWorkClaimRequest =
      TravelInWorkClaimRequest.builder()
          .id(validClaimNumber)
          .nino(NINO)
          .claimType(ClaimType.TRAVEL_IN_WORK)
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .cost(COST)
          .payee(newPayee)
          .claimStatus(ClaimStatus.AWAITING_COUNTER_SIGN)
          .claim(Map.of("0", travelInWorkClaimForOneMonth, "1", travelInWorkClaimForOneMonth))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(updatedTravelToWorkEmployedWorkplaceContact)
          .build();
}
