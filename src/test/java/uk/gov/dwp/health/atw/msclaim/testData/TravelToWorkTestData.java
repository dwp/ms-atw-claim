package uk.gov.dwp.health.atw.msclaim.testData;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.Collections.singletonMap;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.ATW_NUMBER;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.DECLARATION_VERSION;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.NINO;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.claimant;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.claimantWithNoEmail;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.evidences;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.invalidJourneyOrMilesForLiftTravelToWork;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.invalidTravelDetailsForTaxi;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.invalidWayToTravelToWork;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.newPayee;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.travelDetailsForLiftWithMiles;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.travelDetailsForTaxi;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.travelToWorkClaimForOneMonth;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.validClaimNumber;
import static uk.gov.dwp.health.atw.msclaim.testData.WorkplaceContactTestData.acceptWorkplaceContact;
import static uk.gov.dwp.health.atw.msclaim.testData.WorkplaceContactTestData.acceptedWorkplaceContactResponse;
import static uk.gov.dwp.health.atw.msclaim.testData.WorkplaceContactTestData.emailAndFullNameWorkplaceContact;
import static uk.gov.dwp.health.atw.msclaim.testData.WorkplaceContactTestData.emailOnlyWorkplaceContact;
import static uk.gov.dwp.health.atw.msclaim.testData.WorkplaceContactTestData.invalidAcceptedTravelToWorkClaimNoDeclarationVersion;
import static uk.gov.dwp.health.atw.msclaim.testData.WorkplaceContactTestData.invalidEmploymentStatusWorkplaceContact;
import static uk.gov.dwp.health.atw.msclaim.testData.WorkplaceContactTestData.invalidSelfEmployedWorkplaceContact;
import static uk.gov.dwp.health.atw.msclaim.testData.WorkplaceContactTestData.invalidTravelToWorkEmployedWithoutFullNameOrEmailAddressWorkplaceContact;
import static uk.gov.dwp.health.atw.msclaim.testData.WorkplaceContactTestData.invalidTravelToWorkSelfEmployedWorkplaceContact;
import static uk.gov.dwp.health.atw.msclaim.testData.WorkplaceContactTestData.rejectedWorkplaceContactResponse;
import static uk.gov.dwp.health.atw.msclaim.testData.WorkplaceContactTestData.selfEmployedWorkplaceContact;
import static uk.gov.dwp.health.atw.msclaim.testData.WorkplaceContactTestData.travelToWorkEmployedWorkplaceContact;

import java.util.Collections;
import java.util.Map;
import uk.gov.dwp.health.atw.msclaim.models.Evidence;
import uk.gov.dwp.health.atw.msclaim.models.enums.ClaimStatus;
import uk.gov.dwp.health.atw.msclaim.models.enums.ClaimType;
import uk.gov.dwp.health.atw.msclaim.models.requests.ClaimRequest;
import uk.gov.dwp.health.atw.msclaim.models.requests.TravelToWorkClaimRequest;
import uk.gov.dwp.health.atw.msclaim.models.responses.ClaimResponse;

public class TravelToWorkTestData {

  public static final ClaimResponse claimResponseTtw = ClaimResponse.of(
      1L,
      ClaimType.TRAVEL_TO_WORK
  );


  public static final ClaimRequest validTravelToWorkClaim = ClaimRequest.builder()
      .id(validClaimNumber)
      .nino(NINO)
      .claimType(ClaimType.TRAVEL_TO_WORK)
      .cost(22.11)
      .hasContributions(true)
      .hasContributions(true)
      .atwNumber(ATW_NUMBER)
      .claimant(claimant)
      .payee(newPayee)
      .declarationVersion(DECLARATION_VERSION)
      .journeyContext(Collections.emptyMap())
      .build();

  public static final TravelToWorkClaimRequest existingTravelToWorkClaimRequest =
      TravelToWorkClaimRequest.builder()
          .id(validTravelToWorkClaim.getId())
          .nino(validTravelToWorkClaim.getNino())
          .claimType(ClaimType.TRAVEL_TO_WORK)
          .cost(validTravelToWorkClaim.getCost())
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .payee(validTravelToWorkClaim.getPayee())
          .claimStatus(ClaimStatus.AWAITING_COUNTER_SIGN)
          .travelDetails(travelDetailsForLiftWithMiles)
          .claim(singletonMap("0", travelToWorkClaimForOneMonth))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(emailOnlyWorkplaceContact)
          .build();

  public static final TravelToWorkClaimRequest invalidClaimTypeForTravelToWorkRequest =
      TravelToWorkClaimRequest.builder()
          .id(validTravelToWorkClaim.getId())
          .nino(validTravelToWorkClaim.getNino())
          .claimType(ClaimType.EQUIPMENT_OR_ADAPTATION)
          .cost(validTravelToWorkClaim.getCost())
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .payee(validTravelToWorkClaim.getPayee())
          .claimStatus(ClaimStatus.AWAITING_COUNTER_SIGN)
          .travelDetails(travelDetailsForLiftWithMiles)
          .claim(singletonMap("0", travelToWorkClaimForOneMonth))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(acceptWorkplaceContact)
          .build();

  public static final TravelToWorkClaimRequest submittedLiftTravelToWorkClaimRequest =
      TravelToWorkClaimRequest.builder()
          .id(validTravelToWorkClaim.getId())
          .nino(validTravelToWorkClaim.getNino())
          .claimType(ClaimType.TRAVEL_TO_WORK)
          .cost(validTravelToWorkClaim.getCost())
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .payee(validTravelToWorkClaim.getPayee())
          .claimStatus(ClaimStatus.AWAITING_COUNTER_SIGN)
          .travelDetails(travelDetailsForLiftWithMiles)
          .claim(singletonMap("0", travelToWorkClaimForOneMonth))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(travelToWorkEmployedWorkplaceContact)
          .build();

  public static final TravelToWorkClaimRequest submittedLiftForTwoMonthsTravelToWorkClaimRequest =
      TravelToWorkClaimRequest.builder()
          .id(validTravelToWorkClaim.getId())
          .nino(validTravelToWorkClaim.getNino())
          .claimType(ClaimType.TRAVEL_TO_WORK)
          .cost(validTravelToWorkClaim.getCost())
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .payee(validTravelToWorkClaim.getPayee())
          .claimStatus(ClaimStatus.AWAITING_COUNTER_SIGN)
          .travelDetails(travelDetailsForLiftWithMiles)
          .claim(Map.of("0", travelToWorkClaimForOneMonth, "1", travelToWorkClaimForOneMonth))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(travelToWorkEmployedWorkplaceContact)
          .build();

  public static final TravelToWorkClaimRequest submittedLiftWithNoClaimantEmail =
      TravelToWorkClaimRequest.builder()
          .id(validTravelToWorkClaim.getId())
          .nino(validTravelToWorkClaim.getNino())
          .claimType(ClaimType.TRAVEL_TO_WORK)
          .cost(validTravelToWorkClaim.getCost())
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimantWithNoEmail)
          .payee(validTravelToWorkClaim.getPayee())
          .claimStatus(ClaimStatus.AWAITING_COUNTER_SIGN)
          .travelDetails(travelDetailsForLiftWithMiles)
          .claim(Map.of("0", travelToWorkClaimForOneMonth, "1", travelToWorkClaimForOneMonth))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(travelToWorkEmployedWorkplaceContact)
          .build();

  public static final TravelToWorkClaimRequest submittedTaxiTravelToWorkClaimRequest =
      TravelToWorkClaimRequest.builder()
          .id(validTravelToWorkClaim.getId())
          .nino(validTravelToWorkClaim.getNino())
          .claimType(ClaimType.TRAVEL_TO_WORK)
          .cost(validTravelToWorkClaim.getCost())
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .evidence(evidences)
          .payee(validTravelToWorkClaim.getPayee())
          .claimStatus(ClaimStatus.AWAITING_DRS_UPLOAD)
          .travelDetails(travelDetailsForTaxi)
          .claim(singletonMap("0", travelToWorkClaimForOneMonth))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(selfEmployedWorkplaceContact)
          .build();

  public static TravelToWorkClaimRequest
      invalidSelfEmployedAwaitingCounterSignTravelToWorkClaimRequest =
      TravelToWorkClaimRequest.builder()
          .id(validTravelToWorkClaim.getId())
          .nino(validTravelToWorkClaim.getNino())
          .claimType(ClaimType.TRAVEL_TO_WORK)
          .cost(validTravelToWorkClaim.getCost())
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .evidence(evidences)
          .payee(validTravelToWorkClaim.getPayee())
          .claimStatus(ClaimStatus.AWAITING_COUNTER_SIGN)
          .travelDetails(travelDetailsForTaxi)
          .claim(singletonMap("0", travelToWorkClaimForOneMonth))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(selfEmployedWorkplaceContact)
          .build();

  public static final TravelToWorkClaimRequest submittedAcceptedTravelToWorkClaimRequest =
      TravelToWorkClaimRequest.builder()
          .id(validTravelToWorkClaim.getId())
          .nino(validTravelToWorkClaim.getNino())
          .claimType(ClaimType.TRAVEL_TO_WORK)
          .cost(validTravelToWorkClaim.getCost())
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .payee(validTravelToWorkClaim.getPayee())
          .claimStatus(ClaimStatus.AWAITING_DRS_UPLOAD)
          .travelDetails(travelDetailsForLiftWithMiles)
          .claim(singletonMap("0", travelToWorkClaimForOneMonth))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(acceptedWorkplaceContactResponse)
          .build();

  public static final TravelToWorkClaimRequest
      submittedAcceptedTravelToWorkClaimRequestWithTwoClaims =
      TravelToWorkClaimRequest.builder()
          .id(validTravelToWorkClaim.getId())
          .nino(validTravelToWorkClaim.getNino())
          .claimType(ClaimType.TRAVEL_TO_WORK)
          .cost(validTravelToWorkClaim.getCost())
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .payee(validTravelToWorkClaim.getPayee())
          .claimStatus(ClaimStatus.AWAITING_DRS_UPLOAD)
          .travelDetails(travelDetailsForLiftWithMiles)
          .claim(Map.of("0", travelToWorkClaimForOneMonth, "1", travelToWorkClaimForOneMonth))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(acceptedWorkplaceContactResponse)
          .build();

  public static final TravelToWorkClaimRequest submittedRejectedTravelToWorkClaimRequest =
      TravelToWorkClaimRequest.builder()
          .id(validTravelToWorkClaim.getId())
          .nino(validTravelToWorkClaim.getNino())
          .claimType(ClaimType.TRAVEL_TO_WORK)
          .cost(validTravelToWorkClaim.getCost())
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .payee(validTravelToWorkClaim.getPayee())
          .claimStatus(ClaimStatus.COUNTER_SIGN_REJECTED)
          .travelDetails(travelDetailsForLiftWithMiles)
          .claim(singletonMap("0", travelToWorkClaimForOneMonth))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(rejectedWorkplaceContactResponse)
          .build();

  public static final TravelToWorkClaimRequest uploadedToDocumentBatchTravelToWorkClaimRequest =
      TravelToWorkClaimRequest.builder()
          .id(validTravelToWorkClaim.getId())
          .nino(validTravelToWorkClaim.getNino())
          .claimType(ClaimType.TRAVEL_TO_WORK)
          .cost(validTravelToWorkClaim.getCost())
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .payee(validTravelToWorkClaim.getPayee())
          .claimStatus(ClaimStatus.UPLOADED_TO_DOCUMENT_BATCH)
          .travelDetails(travelDetailsForLiftWithMiles)
          .claim(singletonMap("0", travelToWorkClaimForOneMonth))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(acceptedWorkplaceContactResponse)
          .build();

  public static final TravelToWorkClaimRequest drsErrorTravelToWorkClaimRequest =
      TravelToWorkClaimRequest.builder()
          .id(validTravelToWorkClaim.getId())
          .nino(validTravelToWorkClaim.getNino())
          .claimType(ClaimType.TRAVEL_TO_WORK)
          .cost(validTravelToWorkClaim.getCost())
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .payee(validTravelToWorkClaim.getPayee())
          .claimStatus(ClaimStatus.DRS_ERROR)
          .travelDetails(travelDetailsForLiftWithMiles)
          .claim(singletonMap("0", travelToWorkClaimForOneMonth))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(acceptedWorkplaceContactResponse)
          .build();

  public static final TravelToWorkClaimRequest awaitingAgentApprovalTravelToWorkClaimRequest =
      TravelToWorkClaimRequest.builder()
          .id(validTravelToWorkClaim.getId())
          .nino(validTravelToWorkClaim.getNino())
          .claimType(ClaimType.TRAVEL_TO_WORK)
          .cost(validTravelToWorkClaim.getCost())
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .payee(validTravelToWorkClaim.getPayee())
          .claimStatus(ClaimStatus.AWAITING_AGENT_APPROVAL)
          .travelDetails(travelDetailsForLiftWithMiles)
          .claim(singletonMap("0", travelToWorkClaimForOneMonth))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(acceptedWorkplaceContactResponse)
          .build();

  public static final TravelToWorkClaimRequest validLiftTravelToWorkSubmitRequest =
      TravelToWorkClaimRequest.builder()
          .nino(validTravelToWorkClaim.getNino())
          .claimType(ClaimType.TRAVEL_TO_WORK)
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .payee(validTravelToWorkClaim.getPayee())
          .travelDetails(travelDetailsForLiftWithMiles)
          .claim(singletonMap("0", travelToWorkClaimForOneMonth))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(travelToWorkEmployedWorkplaceContact)
          .build();

  public static final TravelToWorkClaimRequest invalidTravelToWorkSubmitSelfEmployedRequest =
      TravelToWorkClaimRequest.builder()
          .nino(validTravelToWorkClaim.getNino())
          .claimType(ClaimType.TRAVEL_TO_WORK)
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .payee(validTravelToWorkClaim.getPayee())
          .travelDetails(travelDetailsForLiftWithMiles)
          .claim(singletonMap("0", travelToWorkClaimForOneMonth))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(invalidSelfEmployedWorkplaceContact)
          .build();


  public static final TravelToWorkClaimRequest
      invalidLiftTravelToWorkSubmitRequestWithoutEmploymentStatus =
      TravelToWorkClaimRequest.builder()
          .nino(validTravelToWorkClaim.getNino())
          .claimType(ClaimType.TRAVEL_TO_WORK)
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .payee(validTravelToWorkClaim.getPayee())
          .travelDetails(travelDetailsForLiftWithMiles)
          .claim(singletonMap("0", travelToWorkClaimForOneMonth))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(emailAndFullNameWorkplaceContact)
          .build();

  public static final TravelToWorkClaimRequest validLiftForTwoMonthsTravelToWorkSubmitRequest =
      TravelToWorkClaimRequest.builder()
          .nino(validTravelToWorkClaim.getNino())
          .claimType(ClaimType.TRAVEL_TO_WORK)
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .payee(validTravelToWorkClaim.getPayee())
          .travelDetails(travelDetailsForLiftWithMiles)
          .claim(Map.of("0", travelToWorkClaimForOneMonth, "1", travelToWorkClaimForOneMonth))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(travelToWorkEmployedWorkplaceContact)
          .build();

  public static final TravelToWorkClaimRequest validTaxiTravelToWorkSubmitRequest =
      TravelToWorkClaimRequest.builder()
          .nino(validTravelToWorkClaim.getNino())
          .claimType(ClaimType.TRAVEL_TO_WORK)
          .cost(validTravelToWorkClaim.getCost())
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .evidence(evidences)
          .payee(validTravelToWorkClaim.getPayee())
          .travelDetails(travelDetailsForTaxi)
          .claim(singletonMap("0", travelToWorkClaimForOneMonth))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(selfEmployedWorkplaceContact)
          .build();

  public static final TravelToWorkClaimRequest invalidWayToTravelToWorkSubmitRequest =
      TravelToWorkClaimRequest.builder()
          .nino(validTravelToWorkClaim.getNino())
          .claimType(ClaimType.TRAVEL_TO_WORK)
          .cost(validTravelToWorkClaim.getCost())
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .payee(validTravelToWorkClaim.getPayee())
          .travelDetails(invalidWayToTravelToWork)
          .claim(singletonMap("0", travelToWorkClaimForOneMonth))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(selfEmployedWorkplaceContact)
          .build();


  public static final TravelToWorkClaimRequest invalidTaxiMissingEvidenceToWorkSubmitRequest =
      TravelToWorkClaimRequest.builder()
          .nino(validTravelToWorkClaim.getNino())
          .claimType(ClaimType.TRAVEL_TO_WORK)
          .cost(validTravelToWorkClaim.getCost())
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .payee(validTravelToWorkClaim.getPayee())
          .travelDetails(travelDetailsForTaxi)
          .claim(singletonMap("0", travelToWorkClaimForOneMonth))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(selfEmployedWorkplaceContact)
          .build();

  public static final TravelToWorkClaimRequest invalidLiftWithEvidenceToWorkSubmitRequest =
      TravelToWorkClaimRequest.builder()
          .nino(validTravelToWorkClaim.getNino())
          .claimType(ClaimType.TRAVEL_TO_WORK)
          .evidence(evidences)
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .payee(validTravelToWorkClaim.getPayee())
          .travelDetails(travelDetailsForLiftWithMiles)
          .claim(singletonMap("0", travelToWorkClaimForOneMonth))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(selfEmployedWorkplaceContact)
          .build();

  public static final TravelToWorkClaimRequest invalidTaxiEmptyEvidenceToWorkSubmitRequest =
      TravelToWorkClaimRequest.builder()
          .nino(validTravelToWorkClaim.getNino())
          .claimType(ClaimType.TRAVEL_TO_WORK)
          .cost(validTravelToWorkClaim.getCost())
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .payee(validTravelToWorkClaim.getPayee())
          .evidence(emptyList())
          .travelDetails(travelDetailsForTaxi)
          .claim(singletonMap("0", travelToWorkClaimForOneMonth))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(selfEmployedWorkplaceContact)
          .build();

  public static final TravelToWorkClaimRequest invalidJourneyOrMilesTypeToWorkSubmitRequest =
      TravelToWorkClaimRequest.builder()
          .nino(validTravelToWorkClaim.getNino())
          .claimType(ClaimType.TRAVEL_TO_WORK)
          .cost(validTravelToWorkClaim.getCost())
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .payee(validTravelToWorkClaim.getPayee())
          .travelDetails(invalidJourneyOrMilesForLiftTravelToWork)
          .claim(singletonMap("0", travelToWorkClaimForOneMonth))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(selfEmployedWorkplaceContact)
          .build();

  public static final TravelToWorkClaimRequest
      invalidSelfEmployedWorkplaceContactForTaxiTravelToWorkSubmitRequest =
      TravelToWorkClaimRequest.builder()
          .nino(validTravelToWorkClaim.getNino())
          .claimType(ClaimType.TRAVEL_TO_WORK)
          .cost(validTravelToWorkClaim.getCost())
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .evidence(evidences)
          .payee(validTravelToWorkClaim.getPayee())
          .travelDetails(travelDetailsForTaxi)
          .claim(singletonMap("0", travelToWorkClaimForOneMonth))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(invalidTravelToWorkSelfEmployedWorkplaceContact)
          .build();

  public static final TravelToWorkClaimRequest
      invalidTravelDetailsForTaxiWithSelfEmployedWorkplaceContactTravelToWorkSubmitRequest =
      TravelToWorkClaimRequest.builder()
          .nino(validTravelToWorkClaim.getNino())
          .claimType(ClaimType.TRAVEL_TO_WORK)
          .cost(validTravelToWorkClaim.getCost())
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .evidence(evidences)
          .payee(validTravelToWorkClaim.getPayee())
          .travelDetails(invalidTravelDetailsForTaxi)
          .claim(singletonMap("0", travelToWorkClaimForOneMonth))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(selfEmployedWorkplaceContact)
          .build();

  public static final TravelToWorkClaimRequest
      invalidEmployedWorkplaceContactForTaxiTravelToWorkSubmitRequest =
      TravelToWorkClaimRequest.builder()
          .nino(validTravelToWorkClaim.getNino())
          .claimType(ClaimType.TRAVEL_TO_WORK)
          .cost(validTravelToWorkClaim.getCost())
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .evidence(evidences)
          .payee(validTravelToWorkClaim.getPayee())
          .travelDetails(travelDetailsForTaxi)
          .claim(singletonMap("0", travelToWorkClaimForOneMonth))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(
              invalidTravelToWorkEmployedWithoutFullNameOrEmailAddressWorkplaceContact)
          .build();

  public static final TravelToWorkClaimRequest invalidEmployStatusTravelToWorkSubmitRequest =
      TravelToWorkClaimRequest.builder()
          .nino(validTravelToWorkClaim.getNino())
          .claimType(ClaimType.TRAVEL_TO_WORK)
          .cost(validTravelToWorkClaim.getCost())
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .evidence(evidences)
          .payee(validTravelToWorkClaim.getPayee())
          .travelDetails(travelDetailsForTaxi)
          .claim(singletonMap("0", travelToWorkClaimForOneMonth))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(invalidEmploymentStatusWorkplaceContact)
          .build();

  public static final TravelToWorkClaimRequest invalidTaxiAndCostValueForTravelToWorkSubmitRequest =
      TravelToWorkClaimRequest.builder()
          .nino(validTravelToWorkClaim.getNino())
          .claimType(ClaimType.TRAVEL_TO_WORK)
          .evidence(evidences)
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .payee(validTravelToWorkClaim.getPayee())
          .travelDetails(travelDetailsForTaxi)
          .claim(singletonMap("0", travelToWorkClaimForOneMonth))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(travelToWorkEmployedWorkplaceContact)
          .build();

  public static final TravelToWorkClaimRequest invalidLiftAndCostValueForTravelToWorkSubmitRequest =
      TravelToWorkClaimRequest.builder()
          .nino(validTravelToWorkClaim.getNino())
          .claimType(ClaimType.TRAVEL_TO_WORK)
          .cost(validTravelToWorkClaim.getCost())
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .payee(validTravelToWorkClaim.getPayee())
          .travelDetails(travelDetailsForLiftWithMiles)
          .claim(singletonMap("0", travelToWorkClaimForOneMonth))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(travelToWorkEmployedWorkplaceContact)
          .build();

  public static final TravelToWorkClaimRequest travelToWorkWithMissingEvidenceClaimRequest =
      getTravelToWorkWithMissingFileNameEvidenceClaimRequest();

  private static TravelToWorkClaimRequest getTravelToWorkWithMissingFileNameEvidenceClaimRequest() {

    Evidence missingFileNameForEvidence = new Evidence();
    missingFileNameForEvidence.setFileId(
        "633ce73b-1414-433e-8a08-72449a0244fc/144b2aca-996d-4c27-bdf2-1e9b418874d3");

    return TravelToWorkClaimRequest.builder()
        .nino(validTravelToWorkClaim.getNino())
        .claimType(ClaimType.TRAVEL_TO_WORK)
        .cost(validTravelToWorkClaim.getCost())
        .hasContributions(true)
        .atwNumber(ATW_NUMBER)
        .claimant(claimant)
        .evidence(singletonList(missingFileNameForEvidence))
        .payee(validTravelToWorkClaim.getPayee())
        .travelDetails(travelDetailsForLiftWithMiles)
        .claim(singletonMap("0", travelToWorkClaimForOneMonth))
        .declarationVersion(DECLARATION_VERSION)
        .journeyContext(Collections.emptyMap())
        .workplaceContact(emailOnlyWorkplaceContact).build();
  }

  public static final TravelToWorkClaimRequest
      invalidAcceptWorkplaceContactWithReasonDescriptionForTravelToWorkRequest =
      TravelToWorkClaimRequest.builder()
          .id(validTravelToWorkClaim.getId())
          .nino(validTravelToWorkClaim.getNino())
          .claimType(ClaimType.TRAVEL_TO_WORK)
          .cost(validTravelToWorkClaim.getCost())
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .payee(validTravelToWorkClaim.getPayee())
          .claimStatus(ClaimStatus.AWAITING_COUNTER_SIGN)
          .travelDetails(travelDetailsForLiftWithMiles)
          .claim(singletonMap("0", travelToWorkClaimForOneMonth))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(rejectedWorkplaceContactResponse)
          .build();

  public static final TravelToWorkClaimRequest
      invalidAcceptWorkplaceContactWithoutDeclarationVersionForTravelToWorkRequest =
      TravelToWorkClaimRequest.builder()
          .id(validTravelToWorkClaim.getId())
          .nino(validTravelToWorkClaim.getNino())
          .claimType(ClaimType.TRAVEL_TO_WORK)
          .cost(validTravelToWorkClaim.getCost())
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .payee(validTravelToWorkClaim.getPayee())
          .claimStatus(ClaimStatus.AWAITING_COUNTER_SIGN)
          .travelDetails(travelDetailsForLiftWithMiles)
          .claim(singletonMap("0", travelToWorkClaimForOneMonth))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(invalidAcceptedTravelToWorkClaimNoDeclarationVersion)
          .build();

  public static final TravelToWorkClaimRequest invalidClaimForTravelToWorkWithPreviousIdRequest =
      TravelToWorkClaimRequest.builder()
          .id(validTravelToWorkClaim.getId())
          .nino(validTravelToWorkClaim.getNino())
          .claimType(ClaimType.TRAVEL_TO_WORK)
          .cost(validTravelToWorkClaim.getCost())
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .payee(validTravelToWorkClaim.getPayee())
          .claimStatus(ClaimStatus.AWAITING_COUNTER_SIGN)
          .travelDetails(travelDetailsForLiftWithMiles)
          .claim(singletonMap("0", travelToWorkClaimForOneMonth))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(invalidTravelToWorkSelfEmployedWorkplaceContact)
          .previousClaimId(1L)
          .build();
}
