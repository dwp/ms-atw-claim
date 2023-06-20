package uk.gov.dwp.health.atw.msclaim.models.enums;

public enum ClaimStatus {
  AWAITING_COUNTER_SIGN,
  COUNTER_SIGN_REJECTED,
  AWAITING_DRS_UPLOAD,
  UPLOADED_TO_DOCUMENT_BATCH,
  DRS_ERROR,
  AWAITING_AGENT_APPROVAL,
  REPLACED_BY_NEW_CLAIM
}