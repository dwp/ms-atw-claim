package uk.gov.dwp.health.atw.msclaim.controllers.utils;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import uk.gov.dwp.health.atw.msclaim.models.exceptions.ClaimCannotBeCounterSignedException;
import uk.gov.dwp.health.atw.msclaim.models.exceptions.ClaimException;
import uk.gov.dwp.health.atw.msclaim.models.exceptions.ClaimHasWrongStatusException;
import uk.gov.dwp.health.atw.msclaim.models.exceptions.NoClaimRecordFoundException;
import uk.gov.dwp.health.atw.msclaim.models.exceptions.WrongClaimOrBadRequestException;


@ControllerAdvice
public class ServiceExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(DuplicateKeyException.class)
  protected ResponseEntity<Object> handledDuplicateKeyException(DuplicateKeyException ex,
                                                                WebRequest request) {
    return handleExceptionInternal(ex, "Claim Id already exists",
        new HttpHeaders(), HttpStatus.CONFLICT, request);
  }

  @ExceptionHandler(WrongClaimOrBadRequestException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  protected ResponseEntity<Object> handleWrongClaimOrBadRequestException(
      WrongClaimOrBadRequestException ex, WebRequest request) {

    return handleExceptionInternal(ex, ex.getErrorMessage(),
        new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  @ExceptionHandler(value = {ClaimHasWrongStatusException.class, NoClaimRecordFoundException.class})
  @ResponseStatus(HttpStatus.NOT_FOUND)
  protected ResponseEntity<Object> handleClaimHasWrongStatusOrNoClaimRecordFoundException(
      ClaimException ex, WebRequest request) {

    return handleExceptionInternal(ex, ex.getErrorMessage(),
        new HttpHeaders(), ex.getErrorCode(), request);
  }

  @ExceptionHandler(ClaimCannotBeCounterSignedException.class)
  @ResponseStatus(HttpStatus.LOCKED)
  protected ResponseEntity<Object> handleClaimCannotBeCounterSignedExceptionException(
      ClaimCannotBeCounterSignedException ex, WebRequest request) {

    return handleExceptionInternal(ex, ex.getErrorMessage(),
        new HttpHeaders(), HttpStatus.LOCKED, request);
  }
}
