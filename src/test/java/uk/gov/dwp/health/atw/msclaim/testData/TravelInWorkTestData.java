package uk.gov.dwp.health.atw.msclaim.testData;


import static java.util.Collections.emptyList;
import static java.util.Collections.singletonMap;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.ATW_NUMBER;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.DECLARATION_VERSION;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.NINO;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.claimant;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.evidences;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.newPayee;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.newPayeeForExistingPayeeOldDataModel;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.newPayeeSetToFalseWithNoAddressBankDetailsWithAccountNumberAndPayeeDetailsWithNoEmailAddress;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.travelInWorkClaimForOneMonth;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.validClaimNumber;
import static uk.gov.dwp.health.atw.msclaim.testData.WorkplaceContactTestData.acceptedWorkplaceContactResponse;
import static uk.gov.dwp.health.atw.msclaim.testData.WorkplaceContactTestData.emailAndFullNameWorkplaceContact;
import static uk.gov.dwp.health.atw.msclaim.testData.WorkplaceContactTestData.invalidEmploymentStatusWorkplaceContact;
import static uk.gov.dwp.health.atw.msclaim.testData.WorkplaceContactTestData.invalidSelfEmployedWorkplaceContact;
import static uk.gov.dwp.health.atw.msclaim.testData.WorkplaceContactTestData.invalidTravelToWorkEmployedWithoutFullNameOrEmailAddressWorkplaceContact;
import static uk.gov.dwp.health.atw.msclaim.testData.WorkplaceContactTestData.invalidTravelToWorkSelfEmployedWorkplaceContact;
import static uk.gov.dwp.health.atw.msclaim.testData.WorkplaceContactTestData.rejectedWorkplaceContactResponse;
import static uk.gov.dwp.health.atw.msclaim.testData.WorkplaceContactTestData.selfEmployedWorkplaceContact;
import static uk.gov.dwp.health.atw.msclaim.testData.WorkplaceContactTestData.travelToWorkEmployedWorkplaceContact;

import java.util.Collections;
import java.util.Map;
import uk.gov.dwp.health.atw.msclaim.models.enums.ClaimStatus;
import uk.gov.dwp.health.atw.msclaim.models.enums.ClaimType;
import uk.gov.dwp.health.atw.msclaim.models.requests.ClaimRequest;
import uk.gov.dwp.health.atw.msclaim.models.requests.TravelInWorkClaimRequest;
import uk.gov.dwp.health.atw.msclaim.models.responses.ClaimResponse;

public class TravelInWorkTestData {

  public static final ClaimResponse claimResponseTiw = ClaimResponse.of(
      1L,
      ClaimType.TRAVEL_IN_WORK
  );

  public static final ClaimRequest validTravelInWorkClaim = ClaimRequest.builder()
      .id(validClaimNumber)
      .nino(NINO)
      .claimType(ClaimType.TRAVEL_IN_WORK)
      .cost(22.11)
      .hasContributions(true)
      .atwNumber(ATW_NUMBER)
      .claimant(claimant)
      .payee(newPayee)
      .declarationVersion(DECLARATION_VERSION)
      .journeyContext(Collections.emptyMap())
      .build();

  public static final TravelInWorkClaimRequest validTravelInWorkEmployedSubmitRequest =
      TravelInWorkClaimRequest.builder()
          .nino(validTravelInWorkClaim.getNino())
          .claimType(ClaimType.TRAVEL_IN_WORK)
          .cost(validTravelInWorkClaim.getCost())
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .evidence(evidences)
          .payee(validTravelInWorkClaim.getPayee())
          .totalMileage(10)
          .claim(singletonMap("0", travelInWorkClaimForOneMonth))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(travelToWorkEmployedWorkplaceContact)
          .build();

  public static final TravelInWorkClaimRequest validTravelInWorkEmployedWithExistingPayeeSubmitRequest =
      TravelInWorkClaimRequest.builder()
          .nino(validTravelInWorkClaim.getNino())
          .claimType(ClaimType.TRAVEL_IN_WORK)
          .cost(validTravelInWorkClaim.getCost())
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .evidence(evidences)
          .payee(newPayeeSetToFalseWithNoAddressBankDetailsWithAccountNumberAndPayeeDetailsWithNoEmailAddress)
          .totalMileage(10)
          .claim(singletonMap("0", travelInWorkClaimForOneMonth))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(travelToWorkEmployedWorkplaceContact)
          .build();

  public static final TravelInWorkClaimRequest validTravelInWorkEmployedWithExistingPayeeSubmitRequestOldDataModel =
      TravelInWorkClaimRequest.builder()
          .nino(validTravelInWorkClaim.getNino())
          .claimType(ClaimType.TRAVEL_IN_WORK)
          .cost(validTravelInWorkClaim.getCost())
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .evidence(evidences)
          .payee(newPayeeForExistingPayeeOldDataModel)
          .totalMileage(10)
          .claim(singletonMap("0", travelInWorkClaimForOneMonth))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(travelToWorkEmployedWorkplaceContact)
          .build();

  public static final TravelInWorkClaimRequest submittedTravelInWorkEmployedClaimRequest =
      TravelInWorkClaimRequest.builder()
          .id(validTravelInWorkClaim.getId())
          .nino(validTravelInWorkClaim.getNino())
          .claimType(ClaimType.TRAVEL_IN_WORK)
          .cost(validTravelInWorkClaim.getCost())
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .evidence(evidences)
          .payee(validTravelInWorkClaim.getPayee())
          .claimStatus(ClaimStatus.AWAITING_COUNTER_SIGN)
          .totalMileage(10)
          .claim(singletonMap("0", travelInWorkClaimForOneMonth))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(travelToWorkEmployedWorkplaceContact)
          .build();

  public static final TravelInWorkClaimRequest submittedTravelInWorkEmployedWithExistingPayeeClaimRequest =
      TravelInWorkClaimRequest.builder()
          .id(validTravelInWorkClaim.getId())
          .nino(validTravelInWorkClaim.getNino())
          .claimType(ClaimType.TRAVEL_IN_WORK)
          .cost(validTravelInWorkClaim.getCost())
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .evidence(evidences)
          .payee(newPayeeSetToFalseWithNoAddressBankDetailsWithAccountNumberAndPayeeDetailsWithNoEmailAddress)
          .claimStatus(ClaimStatus.AWAITING_COUNTER_SIGN)
          .totalMileage(10)
          .claim(singletonMap("0", travelInWorkClaimForOneMonth))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(travelToWorkEmployedWorkplaceContact)
          .build();

  public static final TravelInWorkClaimRequest submittedTravelInWorkEmployedWithExistingPayeeClaimRequestOldDataModel =
      TravelInWorkClaimRequest.builder()
          .id(validTravelInWorkClaim.getId())
          .nino(validTravelInWorkClaim.getNino())
          .claimType(ClaimType.TRAVEL_IN_WORK)
          .cost(validTravelInWorkClaim.getCost())
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .evidence(evidences)
          .payee(newPayeeForExistingPayeeOldDataModel)
          .claimStatus(ClaimStatus.AWAITING_COUNTER_SIGN)
          .totalMileage(10)
          .claim(singletonMap("0", travelInWorkClaimForOneMonth))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(travelToWorkEmployedWorkplaceContact)
          .build();

  public static final TravelInWorkClaimRequest validForTwoMonthsTravelInWorkEmployedSubmitRequest =
      TravelInWorkClaimRequest.builder()
          .nino(validTravelInWorkClaim.getNino())
          .claimType(ClaimType.TRAVEL_IN_WORK)
          .cost(validTravelInWorkClaim.getCost())
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .evidence(evidences)
          .payee(validTravelInWorkClaim.getPayee())
          .totalMileage(10)
          .claim(Map.of("0", travelInWorkClaimForOneMonth, "1", travelInWorkClaimForOneMonth))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(travelToWorkEmployedWorkplaceContact)
          .build();

  public static final TravelInWorkClaimRequest submittedForTwoMonthsTravelInWorkEmployedClaimRequest =
      TravelInWorkClaimRequest.builder()
          .id(validTravelInWorkClaim.getId())
          .nino(validTravelInWorkClaim.getNino())
          .claimType(ClaimType.TRAVEL_IN_WORK)
          .cost(validTravelInWorkClaim.getCost())
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .evidence(evidences)
          .payee(validTravelInWorkClaim.getPayee())
          .claimStatus(ClaimStatus.AWAITING_COUNTER_SIGN)
          .totalMileage(10)
          .claim(Map.of("0", travelInWorkClaimForOneMonth, "1", travelInWorkClaimForOneMonth))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(travelToWorkEmployedWorkplaceContact)
          .build();

  public static final TravelInWorkClaimRequest validTravelInWorkSubmitRequest =
      TravelInWorkClaimRequest.builder()
          .nino(validTravelInWorkClaim.getNino())
          .claimType(ClaimType.TRAVEL_IN_WORK)
          .cost(validTravelInWorkClaim.getCost())
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .evidence(evidences)
          .payee(validTravelInWorkClaim.getPayee())
          .totalMileage(10)
          .claim(singletonMap("0", travelInWorkClaimForOneMonth))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(selfEmployedWorkplaceContact)
          .build();

    public static final TravelInWorkClaimRequest submittedTravelInWorkSelfEmployedClaimRequest =
      TravelInWorkClaimRequest.builder()
          .id(validTravelInWorkClaim.getId())
          .nino(validTravelInWorkClaim.getNino())
          .claimType(ClaimType.TRAVEL_IN_WORK)
          .cost(validTravelInWorkClaim.getCost())
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .evidence(evidences)
          .payee(validTravelInWorkClaim.getPayee())
          .claimStatus(ClaimStatus.AWAITING_DRS_UPLOAD)
          .totalMileage(10)
          .claim(singletonMap("0", travelInWorkClaimForOneMonth))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(selfEmployedWorkplaceContact)
          .build();

  public static final TravelInWorkClaimRequest invalidClaimForSelfEmployedTravelInWorkWithPreviousIdRequest =
      TravelInWorkClaimRequest.builder()
          .id(validTravelInWorkClaim.getId())
          .nino(validTravelInWorkClaim.getNino())
          .claimType(ClaimType.TRAVEL_IN_WORK)
          .cost(validTravelInWorkClaim.getCost())
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .payee(validTravelInWorkClaim.getPayee())
          .claimStatus(ClaimStatus.AWAITING_COUNTER_SIGN)
          .totalMileage(10)
          .claim(singletonMap("0", travelInWorkClaimForOneMonth))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(invalidTravelToWorkSelfEmployedWorkplaceContact)
          .previousClaimId(1L)
          .build();

  public static final TravelInWorkClaimRequest validTravelInWorkSelfEmployedSubmitRequest =
      TravelInWorkClaimRequest.builder()
          .nino(validTravelInWorkClaim.getNino())
          .claimType(ClaimType.TRAVEL_IN_WORK)
          .cost(validTravelInWorkClaim.getCost())
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .evidence(evidences)
          .payee(validTravelInWorkClaim.getPayee())
          .totalMileage(10)
          .claim(singletonMap("0", travelInWorkClaimForOneMonth))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(selfEmployedWorkplaceContact)
          .build();

  public static final TravelInWorkClaimRequest invalidTravelInWorkSubmitRequestWithoutEmploymentStatus =
      TravelInWorkClaimRequest.builder()
          .nino(validTravelInWorkClaim.getNino())
          .claimType(ClaimType.TRAVEL_IN_WORK)
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .payee(validTravelInWorkClaim.getPayee())
          .totalMileage(10)
          .claim(singletonMap("0", travelInWorkClaimForOneMonth))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(emailAndFullNameWorkplaceContact)
          .build();

  public static final TravelInWorkClaimRequest invalidEmployStatusTravelInWorkSubmitRequest =
      TravelInWorkClaimRequest.builder()
          .nino(validTravelInWorkClaim.getNino())
          .claimType(ClaimType.TRAVEL_IN_WORK)
          .cost(validTravelInWorkClaim.getCost())
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .evidence(evidences)
          .payee(validTravelInWorkClaim.getPayee())
          .totalMileage(10)
          .claim(singletonMap("0", travelInWorkClaimForOneMonth))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(invalidEmploymentStatusWorkplaceContact)
          .build();

  public static final TravelInWorkClaimRequest invalidTravelInWorkSubmitSelfEmployedRequest =
      TravelInWorkClaimRequest.builder()
          .nino(validTravelInWorkClaim.getNino())
          .claimType(ClaimType.TRAVEL_IN_WORK)
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .payee(validTravelInWorkClaim.getPayee())
          .totalMileage(10)
          .claim(singletonMap("0", travelInWorkClaimForOneMonth))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(invalidSelfEmployedWorkplaceContact)
          .build();

  public static final TravelInWorkClaimRequest invalidSelfEmployedWorkplaceContactForTravelInWorkSubmitRequest =
      TravelInWorkClaimRequest.builder()
          .nino(validTravelInWorkClaim.getNino())
          .claimType(ClaimType.TRAVEL_IN_WORK)
          .cost(validTravelInWorkClaim.getCost())
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .evidence(evidences)
          .payee(validTravelInWorkClaim.getPayee())
          .totalMileage(10)
          .claim(singletonMap("0", travelInWorkClaimForOneMonth))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(invalidTravelToWorkSelfEmployedWorkplaceContact)
          .build();

  public static final TravelInWorkClaimRequest invalidEmployedWorkplaceContactForTravelInWorkSubmitRequest =
      TravelInWorkClaimRequest.builder()
          .nino(validTravelInWorkClaim.getNino())
          .claimType(ClaimType.TRAVEL_IN_WORK)
          .cost(validTravelInWorkClaim.getCost())
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .evidence(evidences)
          .payee(validTravelInWorkClaim.getPayee())
          .totalMileage(10)
          .claim(singletonMap("0", travelInWorkClaimForOneMonth))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(invalidTravelToWorkEmployedWithoutFullNameOrEmailAddressWorkplaceContact)
          .build();

  public static final TravelInWorkClaimRequest invalidMissingEvidenceTravelInWorkSubmitRequest =
      TravelInWorkClaimRequest.builder()
          .nino(validTravelInWorkClaim.getNino())
          .claimType(ClaimType.TRAVEL_IN_WORK)
          .cost(validTravelInWorkClaim.getCost())
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .payee(validTravelInWorkClaim.getPayee())
          .totalMileage(10)
          .claim(singletonMap("0", travelInWorkClaimForOneMonth))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(selfEmployedWorkplaceContact)
          .build();

  public static final TravelInWorkClaimRequest invalidEmptyEvidenceTravelInWorkSubmitRequest =
      TravelInWorkClaimRequest.builder()
          .nino(validTravelInWorkClaim.getNino())
          .claimType(ClaimType.TRAVEL_IN_WORK)
          .cost(validTravelInWorkClaim.getCost())
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .payee(validTravelInWorkClaim.getPayee())
          .evidence(emptyList())
          .totalMileage(10)
          .claim(singletonMap("0", travelInWorkClaimForOneMonth))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(selfEmployedWorkplaceContact)
          .build();

  public static final TravelInWorkClaimRequest invalidNoCostValueForTravelInWorkSubmitRequest =
      TravelInWorkClaimRequest.builder()
          .nino(validTravelInWorkClaim.getNino())
          .claimType(ClaimType.TRAVEL_IN_WORK)
          .evidence(evidences)
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .payee(validTravelInWorkClaim.getPayee())
          .totalMileage(10)
          .claim(singletonMap("0", travelInWorkClaimForOneMonth))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(travelToWorkEmployedWorkplaceContact)
          .build();

  public static final TravelInWorkClaimRequest invalidNoTotalMileageValueForTravelInWorkSubmitRequest =
      TravelInWorkClaimRequest.builder()
          .nino(validTravelInWorkClaim.getNino())
          .claimType(ClaimType.TRAVEL_IN_WORK)
          .cost(validTravelInWorkClaim.getCost())
          .evidence(evidences)
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .payee(validTravelInWorkClaim.getPayee())
          .claim(singletonMap("0", travelInWorkClaimForOneMonth))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(travelToWorkEmployedWorkplaceContact)
          .build();

  public static final TravelInWorkClaimRequest existingTravelInWorkClaimRequest =
      TravelInWorkClaimRequest.builder()
          .id(validTravelInWorkClaim.getId())
          .nino(validTravelInWorkClaim.getNino())
          .claimType(ClaimType.TRAVEL_IN_WORK)
          .cost(validTravelInWorkClaim.getCost())
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .payee(validTravelInWorkClaim.getPayee())
          .claimStatus(ClaimStatus.AWAITING_COUNTER_SIGN)
          .totalMileage(10)
          .claim(singletonMap("0", travelInWorkClaimForOneMonth))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(travelToWorkEmployedWorkplaceContact)
          .build();

  public static final TravelInWorkClaimRequest submittedAcceptedTravelInWorkClaimRequest =
      TravelInWorkClaimRequest.builder()
          .id(validTravelInWorkClaim.getId())
          .nino(validTravelInWorkClaim.getNino())
          .claimType(ClaimType.TRAVEL_IN_WORK)
          .cost(validTravelInWorkClaim.getCost())
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .payee(validTravelInWorkClaim.getPayee())
          .claimStatus(ClaimStatus.AWAITING_DRS_UPLOAD)
          .totalMileage(10)
          .claim(singletonMap("0", travelInWorkClaimForOneMonth))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(acceptedWorkplaceContactResponse)
          .build();

  public static final TravelInWorkClaimRequest submittedRejectedTravelInWorkClaimRequest =
      TravelInWorkClaimRequest.builder()
          .id(validTravelInWorkClaim.getId())
          .nino(validTravelInWorkClaim.getNino())
          .claimType(ClaimType.TRAVEL_IN_WORK)
          .cost(validTravelInWorkClaim.getCost())
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .payee(validTravelInWorkClaim.getPayee())
          .claimStatus(ClaimStatus.COUNTER_SIGN_REJECTED)
          .claim(singletonMap("0", travelInWorkClaimForOneMonth))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(rejectedWorkplaceContactResponse)
          .build();

  public static TravelInWorkClaimRequest invalidSelfEmployedAwaitingCounterSignTravelInWorkClaimRequest =
      TravelInWorkClaimRequest.builder()
          .id(validTravelInWorkClaim.getId())
          .nino(validTravelInWorkClaim.getNino())
          .claimType(ClaimType.TRAVEL_IN_WORK)
          .cost(validTravelInWorkClaim.getCost())
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .evidence(evidences)
          .payee(validTravelInWorkClaim.getPayee())
          .claimStatus(ClaimStatus.AWAITING_COUNTER_SIGN)
          .claim(singletonMap("0", travelInWorkClaimForOneMonth))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(selfEmployedWorkplaceContact)
          .build();
}
