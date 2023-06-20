package uk.gov.dwp.health.atw.msclaim.models.exceptions;

import lombok.NonNull;
import org.springframework.http.HttpStatus;

public class WrongClaimOrBadRequestException extends ClaimException {

  public WrongClaimOrBadRequestException(@NonNull String message) {
    super(HttpStatus.BAD_REQUEST, message);
  }
}
