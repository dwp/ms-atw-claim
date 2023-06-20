package uk.gov.dwp.health.atw.msclaim.models.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@ToString
public abstract class ClaimException extends Exception {
  HttpStatus errorCode;
  String errorMessage;
}
