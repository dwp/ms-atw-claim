package uk.gov.dwp.health.atw.msclaim.models.exceptions;

import lombok.NonNull;
import org.springframework.http.HttpStatus;

public class ClaimCannotBeCounterSignedException extends ClaimException {

  public ClaimCannotBeCounterSignedException(@NonNull String message) {
    super(HttpStatus.LOCKED, message);
  }
}
