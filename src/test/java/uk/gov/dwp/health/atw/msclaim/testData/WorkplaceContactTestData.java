package uk.gov.dwp.health.atw.msclaim.testData;

import static uk.gov.dwp.health.atw.msclaim.testData.TestData.address;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.validClaimNumber;

import java.time.LocalDateTime;
import uk.gov.dwp.health.atw.msclaim.models.WorkplaceContact;
import uk.gov.dwp.health.atw.msclaim.models.enums.ClaimType;
import uk.gov.dwp.health.atw.msclaim.models.requests.WorkplaceContactRequest;

public class WorkplaceContactTestData {

  public static WorkplaceContact emailOnlyWorkplaceContact = WorkplaceContact.builder()
      .emailAddress("email@company.com")
      .build();

  public static WorkplaceContact travelToWorkEmployedWorkplaceContact = WorkplaceContact.builder()
      .employmentStatus("employed")
      .fullName("counter Signer")
      .emailAddress("email@company.com")
      .address(address)
      .build();

  public static WorkplaceContact emailAndFullNameWorkplaceContact = WorkplaceContact.builder()
      .emailAddress("email@company.com")
      .fullName("Counter Sign")
      .address(address)
      .build();

  public static WorkplaceContact
      invalidTravelToWorkEmployedWithoutFullNameOrEmailAddressWorkplaceContact =
      WorkplaceContact.builder()
          .employmentStatus("employed")
          .build();

  public static WorkplaceContact invalidEmploymentStatusWorkplaceContact =
      WorkplaceContact.builder()
          .employmentStatus("unemployed")
          .build();

  public static WorkplaceContact selfEmployedWorkplaceContact = WorkplaceContact.builder()
      .employmentStatus("selfEmployed")
      .build();

  public static WorkplaceContact invalidSelfEmployedWorkplaceContact = WorkplaceContact.builder()
      .employmentStatus("selfEmployed")
      .fullName("counter Signer")
      .emailAddress("email@company.com")
      .build();

  public static WorkplaceContact invalidTravelToWorkSelfEmployedWorkplaceContact =
      WorkplaceContact.builder()
          .employmentStatus("selfEmployed")
          .fullName("counter Signer")
          .address(address)
          .emailAddress("email@company.com")
          .build();

  public static WorkplaceContact acceptWorkplaceContact = WorkplaceContact.builder()
      .emailAddress("email@company.com")
      .organisation("Organisation")
      .address(address)
      .jobTitle("boss")
      .build();

  public static WorkplaceContact acceptedWorkplaceContactResponse = WorkplaceContact.builder()
      .emailAddress("email@company.com")
      .organisation("Organisation")
      .jobTitle("boss")
      .address(address)
      .updatedOn(LocalDateTime.now())
      .build();

  public static WorkplaceContact rejectedWorkplaceContactResponse = WorkplaceContact.builder()
      .emailAddress("email@company.com")
      .organisation("Organisation")
      .jobTitle("boss")
      .address(address)
      .reason("not valid claim")
      .updatedOn(LocalDateTime.now())
      .build();

  public static WorkplaceContact invalidAcceptedTravelToWorkClaimNoDeclarationVersion =
      WorkplaceContact.builder()
          .emailAddress("email@company.com")
          .organisation("Organisation")
          .jobTitle("boss")
          .address(address)
          .declarationVersion(1.0)
          .updatedOn(LocalDateTime.now())
          .build();

  public static WorkplaceContactRequest
      invalidRejectedSupportWorkerWorkplaceContactReasonOver300Char =
      WorkplaceContactRequest.builder()
          .claimNumber(validClaimNumber)
          .claimType(ClaimType.SUPPORT_WORKER)
          .organisation("Organisation")
          .jobTitle("boss")
          .address(address)
          .declarationVersion(1.0)
          .reason(
              "nulla facilisi etiam dignissim diam quis enim lobortis scelerisque fermentum dui faucibus in ornare quam viverra orci sagittis eu volutpat odio facilisis mauris sit amet massa vitae tortor condimentum lacinia quis vel eros donec ac odio tempor orci dapibus ultrices in iaculis nunc sed augue lacus viverra vita")
          .build();

  public static WorkplaceContactRequest validAcceptedSupportWorkerClaim =
      WorkplaceContactRequest.builder()
          .claimNumber(validClaimNumber)
          .claimType(ClaimType.SUPPORT_WORKER)
          .organisation("organisation")
          .address(address)
          .jobTitle("job title")
              .declarationVersion(1.0)
          .build();

  public static WorkplaceContactRequest inValidAcceptedTravelToWorkClaimMissingDeclarationVersion =
          WorkplaceContactRequest.builder()
                  .claimNumber(validClaimNumber)
                  .claimType(ClaimType.TRAVEL_TO_WORK)
                  .organisation("organisation")
                  .address(address)
                  .jobTitle("job title")
                  .build();

  public static WorkplaceContactRequest validRejectedSupportWorkerClaim =
      WorkplaceContactRequest.builder()
          .claimNumber(validClaimNumber)
          .claimType(ClaimType.SUPPORT_WORKER)
          .organisation("Organisation")
          .jobTitle("boss")
          .address(address)
          .reason("not valid claim")
          .build();

  public static WorkplaceContactRequest inValidRejectedSupportWorkerClaimHasDeclarationVersion =
          WorkplaceContactRequest.builder()
                  .claimNumber(validClaimNumber)
                  .claimType(ClaimType.SUPPORT_WORKER)
                  .organisation("Organisation")
                  .jobTitle("boss")
                  .address(address)
                  .declarationVersion(1.0)
                  .reason("not valid claim")
                  .build();


  public static WorkplaceContactRequest validRejectedTravelToWorkClaim =
      WorkplaceContactRequest.builder()
          .claimNumber(validClaimNumber)
          .claimType(ClaimType.TRAVEL_TO_WORK)
          .organisation("Organisation")
          .jobTitle("boss")
          .address(address)
          .reason("not valid claim")
          .build();

  public static WorkplaceContactRequest validRejectedTravelInWorkClaim =
      WorkplaceContactRequest.builder()
          .claimNumber(validClaimNumber)
          .claimType(ClaimType.TRAVEL_IN_WORK)
          .organisation("Organisation")
          .jobTitle("boss")
          .address(address)
          .reason("not valid claim")
          .build();

  public static WorkplaceContactRequest validAcceptedTravelToWorkClaim =
      WorkplaceContactRequest.builder()
          .claimNumber(validClaimNumber)
          .claimType(ClaimType.TRAVEL_TO_WORK)
          .organisation("Organisation")
          .jobTitle("boss")
          .declarationVersion(1.0)
          .address(address)
          .build();

  public static WorkplaceContactRequest validAcceptedTravelInWorkClaim =
      WorkplaceContactRequest.builder()
          .claimNumber(validClaimNumber)
          .claimType(ClaimType.TRAVEL_IN_WORK)
          .organisation("Organisation")
          .jobTitle("boss")
          .declarationVersion(1.0)
          .address(address)
          .build();

  public static WorkplaceContactRequest invalidAcceptedEquipmentOrAdaptationClaim =
      WorkplaceContactRequest.builder()
          .claimNumber(validClaimNumber)
          .claimType(ClaimType.EQUIPMENT_OR_ADAPTATION)
          .organisation("Organisation")
          .jobTitle("boss")
          .address(address)
          .build();

  public static WorkplaceContactRequest invalidRejectEquipmentOrAdaptationClaim =
      WorkplaceContactRequest.builder()
          .claimNumber(validClaimNumber)
          .claimType(ClaimType.EQUIPMENT_OR_ADAPTATION)
          .organisation("Organisation")
          .jobTitle("boss")
          .address(address)
          .reason("not valid claim")
          .build();

  public static WorkplaceContactRequest invalidAcceptedAdaptationToVehicleClaim =
      WorkplaceContactRequest.builder()
          .claimNumber(validClaimNumber)
          .claimType(ClaimType.ADAPTATION_TO_VEHICLE)
          .organisation("Organisation")
          .jobTitle("boss")
          .address(address)
          .build();

  public static WorkplaceContactRequest invalidRejectAdaptationToVehicleClaim =
      WorkplaceContactRequest.builder()
          .claimNumber(validClaimNumber)
          .claimType(ClaimType.ADAPTATION_TO_VEHICLE)
          .organisation("Organisation")
          .jobTitle("boss")
          .address(address)
          .reason("not valid claim")
          .build();
}
