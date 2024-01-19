package uk.gov.dwp.health.atw.msclaim.testData;

import static java.util.Collections.singletonMap;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.ATW_NUMBER;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.COST;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.DECLARATION_VERSION;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.INVALID_NINO;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.NINO;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.claimant;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.evidences;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.newPayee;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.newPayeeSetToFalseWithNoAddressOrBankDetailsAndPayeeDetailsWithNoEmailAddress;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.singleAdaptationToVehicleClaim;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.validClaimNumber;

import java.util.Collections;
import java.util.Map;
import uk.gov.dwp.health.atw.msclaim.models.enums.ClaimStatus;
import uk.gov.dwp.health.atw.msclaim.models.enums.ClaimType;
import uk.gov.dwp.health.atw.msclaim.models.requests.AdaptationToVehicleClaimRequest;
import uk.gov.dwp.health.atw.msclaim.models.requests.ClaimRequest;
import uk.gov.dwp.health.atw.msclaim.models.responses.ClaimResponse;

public class AdaptationToVehicleTestData {

  public static final ClaimResponse claimResponseAv = ClaimResponse.of(
      1L,
      ClaimType.ADAPTATION_TO_VEHICLE
  );

  public static final AdaptationToVehicleClaimRequest validAdaptationToVehicleSubmitRequest =
      AdaptationToVehicleClaimRequest.builder()
          .nino(NINO)
          .claimType(ClaimType.ADAPTATION_TO_VEHICLE)
          .cost(COST)
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .evidence(evidences)
          .payee(newPayee)
          .claim(singletonMap("0", singleAdaptationToVehicleClaim))
          .journeyContext(Collections.emptyMap())
          .declarationVersion(DECLARATION_VERSION)
          .build();

  public static final AdaptationToVehicleClaimRequest validAdaptationToVehicleTwoClaimsSubmitRequest =
      AdaptationToVehicleClaimRequest.builder()
          .nino(NINO)
          .claimType(ClaimType.ADAPTATION_TO_VEHICLE)
          .cost(COST)
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .evidence(evidences)
          .payee(newPayee)
          .claim(Map.of("0", singleAdaptationToVehicleClaim, "1", singleAdaptationToVehicleClaim))
          .journeyContext(Collections.emptyMap())
          .declarationVersion(DECLARATION_VERSION)
          .build();

  public static final AdaptationToVehicleClaimRequest
      validAdaptationToVehicleWithNoAddressOrBankDetailsForPayeeRequest =
      AdaptationToVehicleClaimRequest.builder()
          .nino(NINO)
          .claimType(ClaimType.EQUIPMENT_OR_ADAPTATION)
          .cost(COST)
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .evidence(evidences)
          .payee(newPayeeSetToFalseWithNoAddressOrBankDetailsAndPayeeDetailsWithNoEmailAddress)
          .claim(singletonMap("0", singleAdaptationToVehicleClaim))
          .journeyContext(Collections.emptyMap())
          .declarationVersion(DECLARATION_VERSION)
          .build();

  public static final AdaptationToVehicleClaimRequest
      invalidAdaptationToVehicleMissingCostSubmitRequest =
      AdaptationToVehicleClaimRequest.builder()
          .nino(NINO)
          .claimType(ClaimType.EQUIPMENT_OR_ADAPTATION)
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .evidence(evidences)
          .payee(newPayee)
          .claim(singletonMap("0", singleAdaptationToVehicleClaim))
          .journeyContext(Collections.emptyMap())
          .declarationVersion(DECLARATION_VERSION)
          .build();

  public static final AdaptationToVehicleClaimRequest submittedAdaptationToVehicleRequest =
    AdaptationToVehicleClaimRequest.builder()
          .id(validClaimNumber)
          .nino(NINO)
          .claimType(ClaimType.ADAPTATION_TO_VEHICLE)
          .cost(COST)
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .evidence(evidences)
          .payee(newPayee)
          .claimStatus(ClaimStatus.AWAITING_DRS_UPLOAD)
          .declarationVersion(DECLARATION_VERSION)
          .claim(singletonMap("0", singleAdaptationToVehicleClaim))
          .journeyContext(Collections.emptyMap())
          .build();

  public static final AdaptationToVehicleClaimRequest submittedAdaptationToVehicleTwoClaimsRequest =
    AdaptationToVehicleClaimRequest.builder()
          .id(validClaimNumber)
          .nino(NINO)
          .claimType(ClaimType.ADAPTATION_TO_VEHICLE)
          .cost(COST)
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .evidence(evidences)
          .payee(newPayee)
          .claimStatus(ClaimStatus.AWAITING_DRS_UPLOAD)
          .declarationVersion(DECLARATION_VERSION)
          .claim(Map.of("0", singleAdaptationToVehicleClaim, "1", singleAdaptationToVehicleClaim))
          .journeyContext(Collections.emptyMap())
          .build();

  public static final AdaptationToVehicleClaimRequest submittedInvalidAVdRequestWithPreviousId =
    AdaptationToVehicleClaimRequest.builder()
          .id(validClaimNumber)
          .nino(NINO)
          .claimType(ClaimType.ADAPTATION_TO_VEHICLE)
          .cost(COST)
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .evidence(evidences)
          .payee(newPayee)
          .claimStatus(ClaimStatus.AWAITING_DRS_UPLOAD)
          .declarationVersion(DECLARATION_VERSION)
          .claim(singletonMap("0", singleAdaptationToVehicleClaim))
          .journeyContext(Collections.emptyMap())
          .previousClaimId(1L)
          .build();

  public static final ClaimRequest invalidAVClaim = ClaimRequest.builder()
      .id(validClaimNumber)
      .nino(INVALID_NINO)
      .claimType(ClaimType.ADAPTATION_TO_VEHICLE)
      .cost(22.11)
      .payee(newPayee)
      .hasContributions(true)
      .atwNumber(ATW_NUMBER)
      .claimant(claimant)
      .declarationVersion(DECLARATION_VERSION)
      .claimStatus(ClaimStatus.AWAITING_COUNTER_SIGN)
      .previousClaimId(validClaimNumber)
      .journeyContext(Collections.emptyMap())
      .build();
}
