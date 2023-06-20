package uk.gov.dwp.health.atw.msclaim.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static uk.gov.dwp.health.atw.msclaim.testData.ContactInformationTestData.requestIdRequestContactInformation;
import static uk.gov.dwp.health.atw.msclaim.testData.ContactInformationTestData.submittedContactInformationRequest;
import static uk.gov.dwp.health.atw.msclaim.testData.ContactInformationTestData.updateContactInformationRequest;
import static uk.gov.dwp.health.atw.msclaim.utils.TestUtils.asJsonString;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import uk.gov.dwp.health.atw.msclaim.controllers.utils.ServiceExceptionHandler;
import uk.gov.dwp.health.atw.msclaim.models.RequestIdRequest;
import uk.gov.dwp.health.atw.msclaim.models.exceptions.ClaimHasWrongStatusException;
import uk.gov.dwp.health.atw.msclaim.models.exceptions.NoClaimRecordFoundException;
import uk.gov.dwp.health.atw.msclaim.models.requests.ContactInformationRequest;
import uk.gov.dwp.health.atw.msclaim.services.ContactInformationService;

@SpringBootTest(classes = ContactInformationController.class)
@EnableWebMvc
@AutoConfigureMockMvc
@ImportAutoConfiguration(ServiceExceptionHandler.class)
class ContactInformationControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ContactInformationService contactInformationService;

  @Test
  @DisplayName("update contact information successful")
  void updateContactInformation() throws Exception {

    when(contactInformationService.updateContactInformation(any(ContactInformationRequest.class)))
        .thenReturn(submittedContactInformationRequest);

    mockMvc.perform(post("/update-contact-information")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(updateContactInformationRequest)))
        .andExpect(status().isCreated())
        .andExpect(content().json(asJsonString(submittedContactInformationRequest)));
  }

  @Test
  @DisplayName("retrieve contact information with accessToWorkNumber and id successful")
  void retrieveContactInformationWithAccessToWorkNumberAndId() throws Exception {

    when(contactInformationService.retrieveContactInformation(any(RequestIdRequest.class)))
        .thenReturn(submittedContactInformationRequest);

    mockMvc.perform(post("/retrieve-contact-information")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(requestIdRequestContactInformation)))
        .andExpect(status().isOk())
        .andExpect(content().json(asJsonString(submittedContactInformationRequest)));
  }

  @Test
  @DisplayName("retrieve contact information returns empty list")
  void retrieveContactInformationReturnsEmptyList() throws Exception {

    when(contactInformationService.retrieveContactInformation(any(RequestIdRequest.class)))
        .thenReturn(null);

    mockMvc.perform(post("/retrieve-contact-information")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(requestIdRequestContactInformation)))
        .andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("/contact/change-status/processing-upload successful")
  void changeStatusToProcessingUpload() throws Exception {
    doNothing().when(contactInformationService)
        .changeStatusToProcessingUpload(any(RequestIdRequest.class));

    mockMvc.perform(put("/contact/change-status/processing-upload")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(requestIdRequestContactInformation)))
        .andExpect(status().isNoContent());
  }

  @Test
  @DisplayName("/contact/change-status/processing-upload failed due contact information not found")
  void changeStatusToProcessingUploadWithContactInformationNotFound() throws Exception {
    doThrow(new NoClaimRecordFoundException()).when(contactInformationService)
        .changeStatusToProcessingUpload(any(RequestIdRequest.class));

    mockMvc.perform(put("/contact/change-status/processing-upload")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(requestIdRequestContactInformation)))
        .andExpect(status().isNotFound())
        .andExpect(content().string("No claim record found"));
  }

  @Test
  @DisplayName("/contact/change-status/processing-upload failed due to contact information status not being AWAITING_UPLOAD")
  void changeStatusToProcessingUploadNotBeingAwaitingUpload() throws Exception {
    doThrow(new ClaimHasWrongStatusException(
        "Cannot change the contact information status to PROCESSING_UPLOAD")).when(
        contactInformationService).changeStatusToProcessingUpload(any(RequestIdRequest.class));

    mockMvc.perform(put("/contact/change-status/processing-upload")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(requestIdRequestContactInformation)))
        .andExpect(status().isNotFound())
        .andExpect(
            content().string("Cannot change the contact information status to PROCESSING_UPLOAD"));
  }

  @Test
  @DisplayName("/contact/change-status/uploaded-to-document-batch successful")
  void changeStatusToUploadedToDocumentBatch() throws Exception {

    doNothing().when(contactInformationService)
        .changeStatusToUploadedToDocumentBatch(any(RequestIdRequest.class));

    mockMvc.perform(put("/contact/change-status/uploaded-to-document-batch")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(requestIdRequestContactInformation)))
        .andExpect(status().isNoContent());
  }

  @Test
  @DisplayName("/contact/change-status/uploaded-to-document-batch failed due claim number not found")
  void changeStatusToUploadedToDocumentBatchWithContactInformationRecordNotFound() throws Exception {
    doThrow(new NoClaimRecordFoundException()).when(contactInformationService)
        .changeStatusToUploadedToDocumentBatch(any(RequestIdRequest.class));

    mockMvc.perform(put("/contact/change-status/uploaded-to-document-batch")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(requestIdRequestContactInformation)))
        .andExpect(status().isNotFound())
        .andExpect(content().string("No claim record found"));
  }

  @Test
  @DisplayName("/contact/change-status/uploaded-to-document-batch ailed due to contact information status not being PROCESSING_UPLOAD")
  void changeStatusToUploadedToDocumentBatchWithContactInformationRecordNotBeingProcessingUpload()
      throws Exception {
    doThrow(new ClaimHasWrongStatusException(
        "Cannot change the contact information status to UPLOADED_TO_DOCUMENT_BATCH")).when(
        contactInformationService).changeStatusToUploadedToDocumentBatch(any(RequestIdRequest.class));

    mockMvc.perform(put("/contact/change-status/uploaded-to-document-batch")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(requestIdRequestContactInformation)))
        .andExpect(status().isNotFound())
        .andExpect(content().string(
            "Cannot change the contact information status to UPLOADED_TO_DOCUMENT_BATCH"));
  }
  
  @Test
  @DisplayName("/contact/change-status/completed-upload successful")
  void changeStatusToCompletedUpload() throws Exception {

    doNothing().when(contactInformationService)
        .changeStatusCompletedUploaded(any(RequestIdRequest.class));

    mockMvc.perform(put("/contact/change-status/completed-upload")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(requestIdRequestContactInformation)))
        .andExpect(status().isNoContent());
  }

  @Test
  @DisplayName("/contact/change-status/completed-upload failed due claim number not found")
  void changeStatusToCompletedUploadWithContactInformationRecordNotFound() throws Exception {
    doThrow(new NoClaimRecordFoundException()).when(contactInformationService)
        .changeStatusCompletedUploaded(any(RequestIdRequest.class));

    mockMvc.perform(put("/contact/change-status/completed-upload")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(requestIdRequestContactInformation)))
        .andExpect(status().isNotFound())
        .andExpect(content().string("No claim record found"));
  }

  @Test
  @DisplayName("/contact/change-status/completed-upload failed due to contact information status not being UPLOADED_TO_DOCUMENT_BATCH")
  void changeStatusToCompletedUploadWithContactInformationRecordNotBeingProcessingUpload()
      throws Exception {
    doThrow(new ClaimHasWrongStatusException(
        "Cannot change the contact information status to UPLOADED_TO_DOCUMENT_BATCH")).when(
        contactInformationService).changeStatusCompletedUploaded(any(RequestIdRequest.class));

    mockMvc.perform(put("/contact/change-status/completed-upload")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(requestIdRequestContactInformation)))
        .andExpect(status().isNotFound())
        .andExpect(content().string(
            "Cannot change the contact information status to UPLOADED_TO_DOCUMENT_BATCH"));
  }

  @Test
  @DisplayName("/contact/change-status/failed-drs-upload successful")
  void changeStatusFailedDrsUploads() throws Exception {

    doNothing().when(contactInformationService)
        .changeStatusFailedDrsUploads(any(RequestIdRequest.class));

    mockMvc.perform(put("/contact/change-status/failed-drs-upload")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(requestIdRequestContactInformation)))
        .andExpect(status().isNoContent());
  }

  @Test
  @DisplayName("/contact/change-status/completed-upload failed due claim number not found")
  void changeStatusFailedDrsUploadWithContactInformationRecordNotFound() throws Exception {
    doThrow(new NoClaimRecordFoundException()).when(contactInformationService)
        .changeStatusFailedDrsUploads(any(RequestIdRequest.class));

    mockMvc.perform(put("/contact/change-status/failed-drs-upload")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(requestIdRequestContactInformation)))
        .andExpect(status().isNotFound())
        .andExpect(content().string("No claim record found"));
  }

  @Test
  @DisplayName("/contact/change-status/failed-drs-upload failed due to contact information status not being UPLOADED_TO_DOCUMENT_BATCH")
  void changeStatusToFailedDrsUploadWithContactInformationRecordNotBeingProcessingUpload()
      throws Exception {
    doThrow(new ClaimHasWrongStatusException(
        "Cannot change the contact information status to UPLOADED_TO_DOCUMENT_BATCH")).when(
        contactInformationService).changeStatusFailedDrsUploads(any(RequestIdRequest.class));

    mockMvc.perform(put("/contact/change-status/failed-drs-upload")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(requestIdRequestContactInformation)))
        .andExpect(status().isNotFound())
        .andExpect(content().string(
            "Cannot change the contact information status to UPLOADED_TO_DOCUMENT_BATCH"));
  }
}