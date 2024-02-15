package uk.gov.dwp.health.atw.msclaim.testData;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.Collections.singletonMap;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.resubmitedValidClaimNumber;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.validClaimNumberDoubleDigits;
import static uk.gov.dwp.health.atw.msclaim.testData.WorkplaceContactTestData.acceptedWorkplaceContactResponse;
import static uk.gov.dwp.health.atw.msclaim.testData.WorkplaceContactTestData.emailAndFullNameWorkplaceContact;
import static uk.gov.dwp.health.atw.msclaim.testData.WorkplaceContactTestData.rejectedWorkplaceContactResponse;
import static uk.gov.dwp.health.atw.msclaim.testData.WorkplaceContactTestData.selfEmployedWorkplaceContact;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.ATW_NUMBER;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.COST;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.DECLARATION_VERSION;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.NINO;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.claimant;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.evidences;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.monthYearOfSupport;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.newPayee;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.validClaimNumber;

import java.util.Collections;
import java.util.Map;
import uk.gov.dwp.health.atw.msclaim.models.SupportWorker;
import uk.gov.dwp.health.atw.msclaim.models.SupportWorkerClaim;
import uk.gov.dwp.health.atw.msclaim.models.TimeOfSupport;
import uk.gov.dwp.health.atw.msclaim.models.enums.ClaimStatus;
import uk.gov.dwp.health.atw.msclaim.models.enums.ClaimType;
import uk.gov.dwp.health.atw.msclaim.models.requests.SupportWorkerClaimRequest;
import uk.gov.dwp.health.atw.msclaim.models.responses.ClaimResponse;

public class SupportWorkerTestData {

  public static final ClaimResponse claimResponseSw = ClaimResponse.of(
      1L,
      ClaimType.SUPPORT_WORKER
  );

  public static final ClaimResponse claimResponseWithClaimNumber11Sw = ClaimResponse.of(
      11L,
      ClaimType.SUPPORT_WORKER
  );

  public static final ClaimResponse reSubmittedClaimResponseSw = ClaimResponse.of(
      2L,
      ClaimType.SUPPORT_WORKER
  );

  private static final TimeOfSupport timeOfSupport2Hours0Mins = TimeOfSupport.builder()
      .hoursOfSupport(2)
      .minutesOfSupport(0)
      .build();

  private static final TimeOfSupport timeOfSupport3Hours30Mins = TimeOfSupport.builder()
      .hoursOfSupport(3)
      .minutesOfSupport(30)
      .build();

  private static final SupportWorkerClaim supportWorkerClaim = SupportWorkerClaim.builder()
      .dayOfSupport(1)
      .timeOfSupport(timeOfSupport2Hours0Mins)
      .build();

  private static final SupportWorkerClaim supportWorkerClaimWithoutHoursOfSupportAndTimeOfSupport = SupportWorkerClaim.builder()
        .dayOfSupport(1)
        .build();

  private static final SupportWorkerClaim supportWorkerClaimWithTimeOfSupport3Hours30Mins = SupportWorkerClaim.builder()
        .dayOfSupport(2)
        .timeOfSupport(timeOfSupport3Hours30Mins)
        .build();

  public static final SupportWorker supportWorkerClaimForOneMonthWithNameOfSupport = SupportWorker.builder()
      .monthYear(monthYearOfSupport)
      .claim(asList(supportWorkerClaim, supportWorkerClaimWithTimeOfSupport3Hours30Mins))
      .build();

  public static final SupportWorkerClaimRequest validSupportWorkerClaimRequest =
      SupportWorkerClaimRequest.builder()
          .nino(NINO)
          .claimType(ClaimType.SUPPORT_WORKER)
          .cost(COST)
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .evidence(evidences)
          .payee(newPayee)
          .nameOfSupport("Person 3")
          .claim(singletonMap("0", supportWorkerClaimForOneMonthWithNameOfSupport))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(emailAndFullNameWorkplaceContact)
          .build();

  public static final SupportWorker supportWorkerClaimForOneMonthWithoutNameOfSupport = SupportWorker.builder()
      .monthYear(monthYearOfSupport)
      .claim(singletonList(supportWorkerClaim))
      .build();

  public static final SupportWorkerClaimRequest validSupportWorkerClaimRequestWithoutNameOfSupport =
      SupportWorkerClaimRequest.builder()
          .nino(NINO)
          .claimType(ClaimType.SUPPORT_WORKER)
          .cost(COST)
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .evidence(evidences)
          .payee(newPayee)
          .claim(singletonMap("0", supportWorkerClaimForOneMonthWithoutNameOfSupport))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(emailAndFullNameWorkplaceContact)
          .build();

  public static final SupportWorkerClaimRequest submittedSupportWorkerClaimForOneMonthWithoutNameOfSupportRequest =
      SupportWorkerClaimRequest.builder()
          .id(validClaimNumber)
          .nino(NINO)
          .claimType(ClaimType.SUPPORT_WORKER)
          .cost(COST)
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .evidence(evidences)
          .payee(newPayee)
          .claimStatus(ClaimStatus.AWAITING_COUNTER_SIGN)
          .claim(singletonMap("0", supportWorkerClaimForOneMonthWithoutNameOfSupport))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(emailAndFullNameWorkplaceContact)
          .build();

  private static final SupportWorker supportWorkerWithoutHoursOfSupportAndTimeOfSupport = SupportWorker.builder()
        .monthYear(monthYearOfSupport)
        .claim(asList(supportWorkerClaimWithoutHoursOfSupportAndTimeOfSupport, supportWorkerClaimWithTimeOfSupport3Hours30Mins))
        .build();

  public static final SupportWorkerClaimRequest invalidSupportWorkerClaimRequestWithoutHoursOfSupportAndTimeOfSupport =
      SupportWorkerClaimRequest.builder()
          .nino(NINO)
          .claimType(ClaimType.SUPPORT_WORKER)
          .cost(COST)
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .evidence(evidences)
          .payee(newPayee)
          .claim(singletonMap("0", supportWorkerWithoutHoursOfSupportAndTimeOfSupport))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(emailAndFullNameWorkplaceContact)
          .build();

  public static final SupportWorkerClaimRequest resubmittedValidSupportWorkerClaimRequest =
      SupportWorkerClaimRequest.builder()
          .nino(NINO)
          .claimType(ClaimType.SUPPORT_WORKER)
          .cost(COST)
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .evidence(evidences)
          .payee(newPayee)
          .nameOfSupport("Person 3")
          .claim(singletonMap("0", supportWorkerClaimForOneMonthWithNameOfSupport))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(emailAndFullNameWorkplaceContact)
          .previousClaimId(validClaimNumber)
          .build();

  public static final SupportWorkerClaimRequest resubmittedSavedValidSupportWorkerClaimRequest =
      SupportWorkerClaimRequest.builder()
          .id(resubmitedValidClaimNumber)
          .nino(NINO)
          .claimType(ClaimType.SUPPORT_WORKER)
          .cost(COST)
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .evidence(evidences)
          .payee(newPayee)
          .claimStatus(ClaimStatus.AWAITING_COUNTER_SIGN)
          .nameOfSupport("Person 3")
          .claim(singletonMap("0", supportWorkerClaimForOneMonthWithNameOfSupport))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(emailAndFullNameWorkplaceContact)
          .previousClaimId(validClaimNumber)
          .build();

  public static final SupportWorkerClaimRequest resubmittedSupportWorkerClaimForOneMonthRequest =
      SupportWorkerClaimRequest.builder()
          .id(resubmitedValidClaimNumber)
          .nino(NINO)
          .claimType(ClaimType.SUPPORT_WORKER)
          .cost(COST)
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .evidence(evidences)
          .payee(newPayee)
          .claimStatus(ClaimStatus.REPLACED_BY_NEW_CLAIM)
          .nameOfSupport("Person 3")
          .claim(singletonMap("0", supportWorkerClaimForOneMonthWithNameOfSupport))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(emailAndFullNameWorkplaceContact)
          .previousClaimId(validClaimNumber)
          .build();

  public static final SupportWorkerClaimRequest submittedSupportWorkerClaimForOneMonthRequest =
      SupportWorkerClaimRequest.builder()
          .id(validClaimNumber)
          .nino(NINO)
          .claimType(ClaimType.SUPPORT_WORKER)
          .cost(COST)
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .evidence(evidences)
          .payee(newPayee)
          .claimStatus(ClaimStatus.AWAITING_COUNTER_SIGN)
          .nameOfSupport("Person 3")
          .claim(singletonMap("0", supportWorkerClaimForOneMonthWithNameOfSupport))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(emailAndFullNameWorkplaceContact)
          .build();

  public static final SupportWorkerClaimRequest submittedSupportWorkerClaimForTwoMonthsRequest =
      SupportWorkerClaimRequest.builder()
          .id(validClaimNumberDoubleDigits)
          .nino(NINO)
          .claimType(ClaimType.SUPPORT_WORKER)
          .cost(COST)
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .evidence(evidences)
          .payee(newPayee)
          .claimStatus(ClaimStatus.AWAITING_COUNTER_SIGN)
          .nameOfSupport("Person 3")
          .claim(Map.of("0", supportWorkerClaimForOneMonthWithNameOfSupport, "1", supportWorkerClaimForOneMonthWithNameOfSupport))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(emailAndFullNameWorkplaceContact)
          .build();

  public static final SupportWorkerClaimRequest invalidWorkplaceContactForSupportWorkerClaimRequest =
      SupportWorkerClaimRequest.builder()
          .nino(NINO)
          .claimType(ClaimType.SUPPORT_WORKER)
          .cost(COST)
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .evidence(evidences)
          .payee(newPayee)
          .nameOfSupport("Person 3")
          .claim(singletonMap("0", supportWorkerClaimForOneMonthWithNameOfSupport))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(selfEmployedWorkplaceContact)
          .build();

  public static final SupportWorkerClaimRequest invalidSupportWorkerMissingCostValueClaimRequest =
      SupportWorkerClaimRequest.builder()
          .nino(NINO)
          .claimType(ClaimType.SUPPORT_WORKER)
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .evidence(evidences)
          .payee(newPayee)
          .nameOfSupport("Person 3")
          .claim(singletonMap("0", supportWorkerClaimForOneMonthWithNameOfSupport))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(emailAndFullNameWorkplaceContact)
          .build();

  public static final SupportWorkerClaimRequest existingSupportWorkerClaimRequest =
      SupportWorkerClaimRequest.builder()
          .id(validClaimNumber)
          .nino(NINO)
          .claimType(ClaimType.SUPPORT_WORKER)
          .cost(COST)
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .evidence(evidences)
          .payee(newPayee)
          .claimStatus(ClaimStatus.AWAITING_COUNTER_SIGN)
          .nameOfSupport("Person 3")
          .claim(singletonMap("0", supportWorkerClaimForOneMonthWithNameOfSupport))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(emailAndFullNameWorkplaceContact)
          .build();

  public static final SupportWorkerClaimRequest submittedAcceptedSupportWorkerClaimRequest =
      SupportWorkerClaimRequest.builder()
          .id(validClaimNumber)
          .nino(NINO)
          .claimType(ClaimType.SUPPORT_WORKER)
          .cost(COST)
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .evidence(evidences)
          .payee(newPayee)
          .claimStatus(ClaimStatus.AWAITING_DRS_UPLOAD)
          .nameOfSupport("Person 3")
          .claim(singletonMap("0", supportWorkerClaimForOneMonthWithNameOfSupport))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(acceptedWorkplaceContactResponse)
          .build();

  public static final SupportWorkerClaimRequest submittedRejectedSupportWorkerClaimRequest =
      SupportWorkerClaimRequest.builder()
          .id(validClaimNumber)
          .nino(NINO)
          .claimType(ClaimType.SUPPORT_WORKER)
          .cost(COST)
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .evidence(evidences)
          .payee(newPayee)
          .claimStatus(ClaimStatus.COUNTER_SIGN_REJECTED)
          .nameOfSupport("Person 3")
          .claim(singletonMap("0", supportWorkerClaimForOneMonthWithNameOfSupport))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(rejectedWorkplaceContactResponse)
          .build();

  public static final SupportWorkerClaimRequest invalidClaimTypeSupportWorkerClaimRequest =
      SupportWorkerClaimRequest.builder()
          .id(validClaimNumber)
          .nino(NINO)
          .claimType(ClaimType.EQUIPMENT_OR_ADAPTATION)
          .cost(COST)
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .evidence(evidences)
          .payee(newPayee)
          .claimStatus(ClaimStatus.COUNTER_SIGN_REJECTED)
          .nameOfSupport("Person 3")
          .claim(singletonMap("0", supportWorkerClaimForOneMonthWithNameOfSupport))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(rejectedWorkplaceContactResponse)
          .build();

  public static final SupportWorkerClaimRequest
      invalidRejectWorkplaceContactWithEmptyReasonDescriptionForSupportWorkerClaimRequest =
      SupportWorkerClaimRequest.builder()
          .id(validClaimNumber)
          .nino(NINO)
          .claimType(ClaimType.SUPPORT_WORKER)
          .cost(COST)
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .evidence(evidences)
          .payee(newPayee)
          .claimStatus(ClaimStatus.AWAITING_COUNTER_SIGN)
          .nameOfSupport("Person 3")
          .claim(singletonMap("0", supportWorkerClaimForOneMonthWithNameOfSupport))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(emailAndFullNameWorkplaceContact)
          .build();

  private static final SupportWorkerClaim supportWorkerClaimBothHoursOfSupportAndTimeOfSupport =
      SupportWorkerClaim.builder()
          .dayOfSupport(1)
          .timeOfSupport(timeOfSupport3Hours30Mins)
          .hoursOfSupport(3.15)
          .build();

  private static final SupportWorker supportWorkerBothHoursOfSupportAndTimeOfSupport =
      SupportWorker.builder()
          .monthYear(monthYearOfSupport)
          .claim(asList(supportWorkerClaimBothHoursOfSupportAndTimeOfSupport, supportWorkerClaimWithTimeOfSupport3Hours30Mins))
          .build();

  public static final SupportWorkerClaimRequest invalidSupportWorkerClaimRequestBothHoursOfSupportAndTimeOfSupport =
      SupportWorkerClaimRequest.builder()
          .nino(NINO)
          .claimType(ClaimType.SUPPORT_WORKER)
          .cost(COST)
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .evidence(evidences)
          .payee(newPayee)
          .claim(singletonMap("0", supportWorkerBothHoursOfSupportAndTimeOfSupport))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(emailAndFullNameWorkplaceContact)
          .build();

  private static final SupportWorkerClaim supportWorkerClaimWithNameOfSupport = SupportWorkerClaim.builder()
      .dayOfSupport(2)
      .timeOfSupport(timeOfSupport3Hours30Mins)
      .nameOfSupport("Person 2")
      .build();

  private static final SupportWorker supportWorkerClaimForOneMonthWithNameOfSupportOnSupportWorkerClaim = SupportWorker.builder()
      .monthYear(monthYearOfSupport)
      .claim(singletonList(supportWorkerClaimWithNameOfSupport))
      .build();

  public static final SupportWorkerClaimRequest validSupportWorkerClaimRequestWithNameOfSupportOnSupportWorkerClaim =
      SupportWorkerClaimRequest.builder()
          .nino(NINO)
          .claimType(ClaimType.SUPPORT_WORKER)
          .cost(COST)
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .evidence(evidences)
          .payee(newPayee)
          .claim(singletonMap("0", supportWorkerClaimForOneMonthWithNameOfSupportOnSupportWorkerClaim))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(emailAndFullNameWorkplaceContact)
          .build();

  public static final SupportWorkerClaimRequest submittedSupportWorkerClaimRequestWithNameOfSupportOnSupportWorkerClaim =
      SupportWorkerClaimRequest.builder()
          .id(validClaimNumberDoubleDigits)
          .nino(NINO)
          .claimType(ClaimType.SUPPORT_WORKER)
          .cost(COST)
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .evidence(evidences)
          .payee(newPayee)
          .claimStatus(ClaimStatus.AWAITING_COUNTER_SIGN)
          .claim(singletonMap("0", supportWorkerClaimForOneMonthWithNameOfSupportOnSupportWorkerClaim))
          .declarationVersion(DECLARATION_VERSION)
          .journeyContext(Collections.emptyMap())
          .workplaceContact(emailAndFullNameWorkplaceContact)
          .build();
}
