package uk.gov.dwp.health.atw.msclaim.testData;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

import java.time.LocalDate;
import uk.gov.dwp.health.atw.msclaim.models.Address;
import uk.gov.dwp.health.atw.msclaim.models.BankDetails;
import uk.gov.dwp.health.atw.msclaim.models.ClaimReferenceNinoRequest;
import uk.gov.dwp.health.atw.msclaim.models.ClaimReferenceRequest;
import uk.gov.dwp.health.atw.msclaim.models.ClaimRetrievalRequest;
import uk.gov.dwp.health.atw.msclaim.models.Claimant;
import uk.gov.dwp.health.atw.msclaim.models.Date;
import uk.gov.dwp.health.atw.msclaim.models.DocumentUploadRequest;
import uk.gov.dwp.health.atw.msclaim.models.EquipmentOrAdaptation;
import uk.gov.dwp.health.atw.msclaim.models.Evidence;
import uk.gov.dwp.health.atw.msclaim.models.MonthYear;
import uk.gov.dwp.health.atw.msclaim.models.NinoRequest;
import uk.gov.dwp.health.atw.msclaim.models.Payee;
import uk.gov.dwp.health.atw.msclaim.models.PayeeDetails;
import uk.gov.dwp.health.atw.msclaim.models.RequestIdRequest;
import uk.gov.dwp.health.atw.msclaim.models.TravelDetails;
import uk.gov.dwp.health.atw.msclaim.models.TravelToWork;
import uk.gov.dwp.health.atw.msclaim.models.TravelToWorkClaim;
import uk.gov.dwp.health.atw.msclaim.models.enums.ClaimType;
import uk.gov.dwp.health.atw.msclaim.models.responses.ClaimCountResponse;
import uk.gov.dwp.health.atw.msclaim.models.responses.ClaimResponse;
import uk.gov.dwp.health.atw.msclaim.models.responses.CountResponse;

import java.util.List;

public class TestData {

  public static final String INVALID_NINO = "AA37073";
  public static final String NINO = "AA370773A";
  public static final String ATW_NUMBER = "ATW1234567";
  public static final String POSTCODE = "NE26 4RS";
  public static final String FORENAME = "Odin";
  public static final String SURNAME = "Surtsson";
  public static final String EMAIL = "odin.surtsson@norse.com";
  public static final String COMPANY = "Company 1";
  public static final String HOME_NUMBER = "01200000000";
  public static final String MOBILE_NUMBER = "07500000000";
  public static final LocalDate DATE_OF_BIRTH = LocalDate.of(1994, 11, 22);
  public static final Double COST = 22.11;
  public static final Double DECLARATION_VERSION = 2.3;
  public static final String UUID = "db1543c5-449a-448c-9cb7-5aab84bd4670";

  public static final long validClaimNumber = 1;

  public static final long validClaimNumberDoubleDigits = 11;

  public static final long resubmitedValidClaimNumber = 2;

  public static final long countRejectedClaimsForNinoCount = 1;

  public static final CountResponse rejectedClaimsForNinoCountResponse =  CountResponse.builder()
      .count(countRejectedClaimsForNinoCount)
      .build();

  public static final ClaimCountResponse countRejectedClaimsForNinoAndClaimTypeCount = ClaimCountResponse.builder()
      .claimType("SUPPORT_WORKER")
      .count(1L)
      .build();

  public static final ClaimCountResponse rejectedClaimsForNinoAndClaimTypeCountResponse =  ClaimCountResponse.builder()
      .claimType("SUPPORT_WORKER")
      .count(1L)
      .build();

  public static final ClaimResponse equipmentOrAdaptationsClaimResponse =
      ClaimResponse.of(validClaimNumber, ClaimType.EQUIPMENT_OR_ADAPTATION);

  public static final ClaimResponse travelToWorkClaimResponse =
      ClaimResponse.of(validClaimNumber, ClaimType.TRAVEL_TO_WORK);

  public static final ClaimResponse supportWorkerClaimResponse =
      ClaimResponse.of(validClaimNumber, ClaimType.SUPPORT_WORKER);

  public static final Date dateOfPurchase = Date.builder()
      .dd("29")
      .mm("09")
      .yyyy("2021")
      .build();

  public static final MonthYear monthYearOfSupport = MonthYear.builder()
      .mm("09")
      .yyyy("2021")
      .build();

  public static final TravelToWorkClaim travelToWorkClaim = TravelToWorkClaim.builder()
      .dayOfTravel(12)
      .totalTravel(13.5)
      .build();

  public static final TravelToWork travelToWorkClaimForOneMonth = TravelToWork.builder()
      .monthYear(monthYearOfSupport)
      .claim(asList(travelToWorkClaim, travelToWorkClaim))
      .build();

  public static final TravelDetails travelDetailsForLiftWithMiles = TravelDetails.builder()
      .howDidYouTravelForWork("lift")
      .journeysOrMileage("mileage")
      .build();

  public static final TravelDetails travelDetailsForTaxi = TravelDetails.builder()
      .howDidYouTravelForWork("taxi")
      .build();

  public static final TravelDetails invalidWayToTravelToWork = TravelDetails.builder()
      .howDidYouTravelForWork("bike")
      .build();

  public static final TravelDetails invalidJourneyOrMilesForLiftTravelToWork = TravelDetails.builder()
      .howDidYouTravelForWork("lift")
      .journeysOrMileage("miles")
      .build();

  public static final TravelDetails invalidTravelDetailsForTaxi = TravelDetails.builder()
      .howDidYouTravelForWork("taxi")
      .journeysOrMileage("journeys")
      .build();

  public static final EquipmentOrAdaptation equipmentOrAdaptation = EquipmentOrAdaptation.builder()
      .description("Item 1")
      .dateOfPurchase(dateOfPurchase)
      .build();

  public static final List<EquipmentOrAdaptation> equipmentOrAdaptations =
      singletonList(equipmentOrAdaptation);

  public static final Evidence evidence = Evidence.builder()
      .fileId("633ce73b-1414-433e-8a08-72449a0244fc/144b2aca-996d-4c27-bdf2-1e9b418874d3")
      .fileName("6b99f480c27e246fa5dd0453cd4fba29.pdf")
      .build();

  public static final List<Evidence> evidences = singletonList(evidence);

  public static final PayeeDetails payeeDetailsWithoutEmailAddress = PayeeDetails.builder()
      .fullName("Citizen One")
      .build();

  public static final PayeeDetails fullPayeeDetails = PayeeDetails.builder()
      .fullName("Citizen One")
      .emailAddress("citizenone@payeedetails.com")
      .build();

  public static final Address address = Address.builder()
      .address1("THE COTTAGE 1")
      .address3("WHITLEY BAY")
      .postcode(POSTCODE)
      .build();

  public static final BankDetails bankDetails = BankDetails.builder()
      .accountHolderName("Citizen One")
      .sortCode("000004")
      .accountNumber("12345677")
      .build();

  public static final Payee newPayee = Payee.builder()
      .newPayee(true)
      .details(fullPayeeDetails)
      .address(address)
      .bankDetails(bankDetails)
      .build();

  public static final Payee newPayeeWithoutEmailAddress = Payee.builder()
      .newPayee(true)
      .details(payeeDetailsWithoutEmailAddress)
      .address(address)
      .bankDetails(bankDetails)
      .build();

  public static final Payee newPayeeWithMissingAddress = Payee.builder()
      .newPayee(true)
      .details(payeeDetailsWithoutEmailAddress)
      .bankDetails(bankDetails)
      .build();

  public static final Payee newPayeeWithMissingBankDetails = Payee.builder()
      .newPayee(true)
      .details(payeeDetailsWithoutEmailAddress)
      .address(address)
      .build();

  public static final Payee
      newPayeeSetToFalseWithNoAddressOrBankDetailsAndPayeeDetailsWithNoEmailAddress = Payee.builder()
      .newPayee(false)
      .details(payeeDetailsWithoutEmailAddress)
      .build();

  public static final Payee newPayeeSetToFalseWithDetailsAndAddress = Payee.builder()
      .newPayee(false)
      .details(payeeDetailsWithoutEmailAddress)
      .address(address)
      .build();

  public static final Payee newPayeeSetToFalseWithDetailsAndBankDetails = Payee.builder()
      .newPayee(false)
      .details(payeeDetailsWithoutEmailAddress)
      .bankDetails(bankDetails)
      .build();

  public static final Claimant claimant = Claimant.builder()
      .dateOfBirth(DATE_OF_BIRTH)
      .surname(SURNAME)
      .forename(FORENAME)
      .homeNumber(HOME_NUMBER)
      .mobileNumber(MOBILE_NUMBER)
      .emailAddress(EMAIL)
      .address(address)
      .company(COMPANY)
      .build();

  public static final Claimant claimantWithNoEmail = Claimant.builder()
      .dateOfBirth(DATE_OF_BIRTH)
      .surname(SURNAME)
      .forename(FORENAME)
      .homeNumber(HOME_NUMBER)
      .mobileNumber(MOBILE_NUMBER)
      .address(address)
      .company(COMPANY)
      .build();

  public static final ClaimRetrievalRequest claimRetrievalRequest = ClaimRetrievalRequest.builder()
      .nino(NINO)
      .claimType(ClaimType.EQUIPMENT_OR_ADAPTATION)
      .build();

  public static final ClaimReferenceNinoRequest validClaimReferenceNinoForTravelToWorkClaim =
      ClaimReferenceNinoRequest.builder()
          .claimReference("TW000" + validClaimNumber)
          .nino(NINO)
          .build();

  public static final ClaimReferenceNinoRequest invalidClaimReferenceNinoForTravelToWorkClaim =
      ClaimReferenceNinoRequest.builder()
          .claimReference("TW0001SW")
          .nino(NINO)
          .build();

  public static final ClaimReferenceRequest validClaimReferenceForTravelToWorkClaim =
      ClaimReferenceRequest.builder()
          .claimReference("TW000" + validClaimNumber)
          .build();

  public static final ClaimReferenceRequest invalidClaimReferenceForTravelToWorkClaim =
      ClaimReferenceRequest.builder()
          .claimReference("TW0001SW")
          .build();

  public static final ClaimReferenceNinoRequest invalidClaimReferenceNinoWithClaimNumberOnly =
      ClaimReferenceNinoRequest.builder()
          .claimReference("0001")
          .nino(NINO)
          .build();

  public static final ClaimReferenceNinoRequest invalidClaimReferenceNinoWithClaimTypeOnly =
      ClaimReferenceNinoRequest.builder()
          .claimReference("TW")
          .nino(NINO)
          .build();

  public static final ClaimReferenceRequest validClaimReferenceForEquipmentOrAdaptationClaim =
      ClaimReferenceRequest.builder()
          .claimReference("EA000" + validClaimNumber)
          .build();

   public static final NinoRequest validRequestRejectedClaimsForNino =
       NinoRequest.builder()
           .nino(NINO)
           .build();

  public static final NinoRequest nullRequestRejectedClaimsForNino =
      NinoRequest.builder()
          .nino("test")
          .build();

  public static final NinoRequest validRequestRejectedClaimsForNinoAndClaimType =
      NinoRequest.builder()
          .nino(NINO)
          .build();

  public static final DocumentUploadRequest documentUploadRequestWithRequestId =
      DocumentUploadRequest.builder()
      .requestId(UUID)
      .claimReference("TW001")
      .build();

  public static final RequestIdRequest requestIdRequest = RequestIdRequest.builder()
      .requestId(UUID)
      .build();


}