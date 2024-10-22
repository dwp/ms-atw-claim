package uk.gov.dwp.health.atw.msclaim.testData;

import static java.util.Collections.singletonList;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.ATW_NUMBER;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.COST;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.DECLARATION_VERSION;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.INVALID_NINO;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.NINO;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.claimant;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.equipmentOrAdaptation;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.evidences;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.newPayee;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.newPayeeSetToFalseWithNoAddressOrBankDetailsAndPayeeDetailsWithNoEmailAddress;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.validClaimNumber;

import java.util.Collections;
import java.util.Map;
import uk.gov.dwp.health.atw.msclaim.models.enums.ClaimStatus;
import uk.gov.dwp.health.atw.msclaim.models.enums.ClaimType;
import uk.gov.dwp.health.atw.msclaim.models.requests.ClaimRequest;
import uk.gov.dwp.health.atw.msclaim.models.requests.EquipmentOrAdaptationClaimRequest;
import uk.gov.dwp.health.atw.msclaim.models.responses.ClaimResponse;

public class EquipmentOrAdaptationTestData {

  public static final ClaimResponse claimResponseEa = ClaimResponse.of(
      1L,
      ClaimType.EQUIPMENT_OR_ADAPTATION
  );

  public static final EquipmentOrAdaptationClaimRequest validEquipmentOrAdaptationSubmitRequest =
      EquipmentOrAdaptationClaimRequest.builder()
          .nino(NINO)
          .claimType(ClaimType.EQUIPMENT_OR_ADAPTATION)
          .cost(COST)
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .evidence(evidences)
          .payee(newPayee)
          .claim(Map.of("0", singletonList(equipmentOrAdaptation)))
          .journeyContext(Collections.emptyMap())
          .declarationVersion(DECLARATION_VERSION)
          .build();

  public static final EquipmentOrAdaptationClaimRequest
      validEquipmentOrAdaptationWithNoAddressOrBankDetailsForPayeeRequest =
      EquipmentOrAdaptationClaimRequest.builder()
          .nino(NINO)
          .claimType(ClaimType.EQUIPMENT_OR_ADAPTATION)
          .cost(COST)
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .evidence(evidences)
          .payee(newPayeeSetToFalseWithNoAddressOrBankDetailsAndPayeeDetailsWithNoEmailAddress)
          .claim(Map.of("0", singletonList(equipmentOrAdaptation)))
          .journeyContext(Collections.emptyMap())
          .declarationVersion(DECLARATION_VERSION)
          .build();

  public static final EquipmentOrAdaptationClaimRequest
      invalidEquipmentOrAdaptationMissingCostSubmitRequest =
      EquipmentOrAdaptationClaimRequest.builder()
          .nino(NINO)
          .claimType(ClaimType.EQUIPMENT_OR_ADAPTATION)
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .evidence(evidences)
          .payee(newPayee)
          .claim(Map.of("0", singletonList(equipmentOrAdaptation)))
          .journeyContext(Collections.emptyMap())
          .declarationVersion(DECLARATION_VERSION)
          .build();

  public static final EquipmentOrAdaptationClaimRequest submittedEquipmentOrAdaptationRequest =
      EquipmentOrAdaptationClaimRequest.builder()
          .id(validClaimNumber)
          .nino(NINO)
          .claimType(ClaimType.EQUIPMENT_OR_ADAPTATION)
          .cost(COST)
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .evidence(evidences)
          .payee(newPayee)
          .claimStatus(ClaimStatus.AWAITING_DRS_UPLOAD)
          .declarationVersion(DECLARATION_VERSION)
          .claim(Map.of("0", singletonList(equipmentOrAdaptation)))
          .journeyContext(Collections.emptyMap())
          .build();

  public static final EquipmentOrAdaptationClaimRequest
      submittedInvalidEquipmentOrAdaptationRequest =
      EquipmentOrAdaptationClaimRequest.builder()
          .id(validClaimNumber)
          .nino(NINO)
          .claimType(ClaimType.EQUIPMENT_OR_ADAPTATION)
          .cost(COST)
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .evidence(evidences)
          .payee(newPayee)
          .claimStatus(ClaimStatus.AWAITING_DRS_UPLOAD)
          .declarationVersion(DECLARATION_VERSION)
          .claim(Map.of("0", singletonList(equipmentOrAdaptation)))
          .journeyContext(Collections.emptyMap())
          .build();

  public static final EquipmentOrAdaptationClaimRequest submittedInvalidEAdRequestWithPreviousId =
      EquipmentOrAdaptationClaimRequest.builder()
          .id(validClaimNumber)
          .nino(NINO)
          .claimType(ClaimType.EQUIPMENT_OR_ADAPTATION)
          .cost(COST)
          .hasContributions(true)
          .atwNumber(ATW_NUMBER)
          .claimant(claimant)
          .evidence(evidences)
          .payee(newPayee)
          .claimStatus(ClaimStatus.AWAITING_DRS_UPLOAD)
          .declarationVersion(DECLARATION_VERSION)
          .claim(Map.of("0", singletonList(equipmentOrAdaptation)))
          .journeyContext(Collections.emptyMap())
          .previousClaimId(1L)
          .build();

  public static final ClaimRequest invalidNinoClaim = ClaimRequest.builder()
      .id(validClaimNumber)
      .nino(INVALID_NINO)
      .claimType(ClaimType.EQUIPMENT_OR_ADAPTATION)
      .cost(22.11)
      .payee(newPayee)
      .hasContributions(true)
      .atwNumber(ATW_NUMBER)
      .claimant(claimant)
      .declarationVersion(DECLARATION_VERSION)
      .journeyContext(Collections.emptyMap())
      .build();

  public static final ClaimRequest invalidEAClaim = ClaimRequest.builder()
      .id(validClaimNumber)
      .nino(INVALID_NINO)
      .claimType(ClaimType.EQUIPMENT_OR_ADAPTATION)
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
