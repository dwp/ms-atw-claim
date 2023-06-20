package uk.gov.dwp.health.atw.msclaim.models.exceptions;

import org.springframework.http.HttpStatus;

public class NoClaimRecordFoundException extends ClaimException {

  public NoClaimRecordFoundException() {
    super(HttpStatus.NOT_FOUND, "No claim record found");
  }
}
