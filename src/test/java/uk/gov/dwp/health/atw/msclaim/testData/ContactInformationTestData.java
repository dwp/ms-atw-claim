package uk.gov.dwp.health.atw.msclaim.testData;

import static uk.gov.dwp.health.atw.msclaim.models.enums.ContactInformationStatus.AWAITING_UPLOAD;
import static uk.gov.dwp.health.atw.msclaim.models.enums.ContactInformationStatus.COMPLETED_DRS_UPLOAD;
import static uk.gov.dwp.health.atw.msclaim.models.enums.ContactInformationStatus.FAILED_DRS_UPLOAD;
import static uk.gov.dwp.health.atw.msclaim.models.enums.ContactInformationStatus.UPLOADED_TO_DOCUMENT_BATCH;
import static uk.gov.dwp.health.atw.msclaim.models.enums.ContactInformationStatus.PROCESSING_UPLOAD;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.NINO;
import static uk.gov.dwp.health.atw.msclaim.testData.TestData.UUID;

import java.time.LocalDate;
import uk.gov.dwp.health.atw.msclaim.models.Address;
import uk.gov.dwp.health.atw.msclaim.models.ContactInformation;
import uk.gov.dwp.health.atw.msclaim.models.RequestIdRequest;
import uk.gov.dwp.health.atw.msclaim.models.requests.ContactInformationRequest;
import uk.gov.dwp.health.atw.msclaim.models.requests.ContactRetrievalRequest;

public class ContactInformationTestData {

  public static final String ACCESS_TO_WORK_NUMBER = "ATW12006521";
  private static final String FORENAME = "Martha";
  private static final String SURNAME = "Ledgrave";
  public static final LocalDate DATE_OF_BIRTH = LocalDate.of(1994, 11, 22);
  private static final String EMAIL_ADDRESS = "martha.ledgrave@email.com";
  private static final String HOME_NUMBER = "01245667788";
  private static final String MOBILE_NUMBER = "07627847834";
  public static final Double DECLARATION_VERSION = 2.3;

  public static final Address oldAddress = Address.builder()
      .address1("THE COTTAGE 1")
      .address3("WHITLEY BAY")
      .postcode("NE26 4RS")
      .build();

  public static final Address newAddress = Address.builder()
      .address1("15 Redburry Grove")
      .address2("Bramhope")
      .address3("Leeds")
      .address4("West Yorkshire")
      .postcode("NE26 4RS")
      .build();

  public static final ContactInformation currentContactInformation =
      ContactInformation.builder()
          .forename(FORENAME)
          .surname(SURNAME)
          .dateOfBirth(DATE_OF_BIRTH)
          .emailAddress(EMAIL_ADDRESS)
          .homeNumber(HOME_NUMBER)
          .mobileNumber(MOBILE_NUMBER)
          .address(oldAddress)
          .build();

  public static final ContactInformation newContactInformation =
      ContactInformation.builder()
          .forename(FORENAME)
          .surname(SURNAME)
          .dateOfBirth(DATE_OF_BIRTH)
          .emailAddress(EMAIL_ADDRESS)
          .homeNumber(HOME_NUMBER)
          .mobileNumber(MOBILE_NUMBER)
          .address(newAddress)
          .build();

  public static final ContactInformation newContactInformationWithNullEmail =
      ContactInformation.builder()
          .forename(FORENAME)
          .surname(SURNAME)
          .dateOfBirth(DATE_OF_BIRTH)
          .emailAddress(null)
          .homeNumber(HOME_NUMBER)
          .mobileNumber(MOBILE_NUMBER)
          .address(newAddress)
          .build();

  public static final ContactInformationRequest updateContactInformationRequest =
      ContactInformationRequest.builder()
          .accessToWorkNumber(ACCESS_TO_WORK_NUMBER)
          .nino(NINO)
          .currentContactInformation(currentContactInformation)
          .newContactInformation(newContactInformation)
          .declarationVersion(DECLARATION_VERSION)
          .build();

  public static final ContactInformationRequest submittedContactInformationRequest =
      ContactInformationRequest.builder()
          .id(UUID)
          .nino(NINO)
          .accessToWorkNumber(ACCESS_TO_WORK_NUMBER)
          .currentContactInformation(currentContactInformation)
          .newContactInformation(newContactInformation)
          .declarationVersion(DECLARATION_VERSION)
          .contactInformationStatus(AWAITING_UPLOAD)
          .build();

  public static final ContactInformationRequest contactInformationRequestWithProcessingUploadStatus =
      ContactInformationRequest.builder()
          .id(UUID)
          .nino(NINO)
          .accessToWorkNumber(ACCESS_TO_WORK_NUMBER)
          .currentContactInformation(currentContactInformation)
          .newContactInformation(newContactInformation)
          .declarationVersion(DECLARATION_VERSION)
          .contactInformationStatus(PROCESSING_UPLOAD)
          .build();

  public static final ContactInformationRequest contactInformationRequestWithProcessingUploadStatus2 =
      ContactInformationRequest.builder()
          .id(UUID)
          .nino(NINO)
          .accessToWorkNumber(ACCESS_TO_WORK_NUMBER)
          .currentContactInformation(currentContactInformation)
          .newContactInformation(newContactInformation)
          .declarationVersion(DECLARATION_VERSION)
          .contactInformationStatus(PROCESSING_UPLOAD)
          .build();


  public static final ContactInformationRequest contactInformationRequestWithUploadedToDocumentBatch =
      ContactInformationRequest.builder()
          .id(UUID)
          .nino(NINO)
          .accessToWorkNumber(ACCESS_TO_WORK_NUMBER)
          .currentContactInformation(currentContactInformation)
          .newContactInformation(newContactInformation)
          .declarationVersion(DECLARATION_VERSION)
          .contactInformationStatus(UPLOADED_TO_DOCUMENT_BATCH)
          .build();

  public static final ContactInformationRequest
      contactInformationRequestWithUploadedToDocumentBatchStatus =
      ContactInformationRequest.builder()
          .id(UUID)
          .nino(NINO)
          .accessToWorkNumber(ACCESS_TO_WORK_NUMBER)
          .currentContactInformation(currentContactInformation)
          .newContactInformation(newContactInformation)
          .declarationVersion(DECLARATION_VERSION)
          .contactInformationStatus(UPLOADED_TO_DOCUMENT_BATCH)
          .build();

  public static final ContactInformationRequest submittedContactInformationRequestWithNullEmail =
      ContactInformationRequest.builder()
          .id(UUID)
          .nino(NINO)
          .accessToWorkNumber(ACCESS_TO_WORK_NUMBER)
          .currentContactInformation(currentContactInformation)
          .newContactInformation(newContactInformationWithNullEmail)
          .declarationVersion(DECLARATION_VERSION)
          .contactInformationStatus(AWAITING_UPLOAD)
          .build();
  public static final ContactInformationRequest
      contactInformationRequestWithDrsErrorStatus =
      ContactInformationRequest.builder()
          .id(UUID)
          .nino(NINO)
          .accessToWorkNumber(ACCESS_TO_WORK_NUMBER)
          .currentContactInformation(currentContactInformation)
          .newContactInformation(newContactInformation)
          .declarationVersion(DECLARATION_VERSION)
          .contactInformationStatus(FAILED_DRS_UPLOAD)
          .build();

  public static final ContactInformationRequest
      contactInformationRequestWithDrsSuccessStatus =
      ContactInformationRequest.builder()
          .id(UUID)
          .nino(NINO)
          .accessToWorkNumber(ACCESS_TO_WORK_NUMBER)
          .currentContactInformation(currentContactInformation)
          .newContactInformation(newContactInformation)
          .declarationVersion(DECLARATION_VERSION)
          .contactInformationStatus(COMPLETED_DRS_UPLOAD)
          .build();

  public static final ContactInformationRequest contactInformationRequestWithCompletedUploadStatus2 =
      ContactInformationRequest.builder()
          .id(UUID)
          .nino(NINO)
          .accessToWorkNumber(ACCESS_TO_WORK_NUMBER)
          .currentContactInformation(currentContactInformation)
          .newContactInformation(newContactInformation)
          .declarationVersion(DECLARATION_VERSION)
          .contactInformationStatus(UPLOADED_TO_DOCUMENT_BATCH)
          .build();

  public static final ContactRetrievalRequest requestedContactInfoWithAccessToWorkNumberAndId =
      ContactRetrievalRequest.builder()
          .accessToWorkNumber(ACCESS_TO_WORK_NUMBER)
          .id(UUID)
          .build();

  public static final RequestIdRequest requestIdRequestContactInformation =
      RequestIdRequest.builder()
          .requestId(UUID)
          .build();
}
