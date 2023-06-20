package uk.gov.dwp.health.atw.msclaim.models.exceptions;

import lombok.NonNull;
import org.springframework.http.HttpStatus;

public class ClaimHasWrongStatusException extends ClaimException {

  public ClaimHasWrongStatusException(@NonNull String message) {
    super(HttpStatus.NOT_FOUND, message);
  }
}
