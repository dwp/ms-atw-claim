package uk.gov.dwp.health.atw.msclaim.controllers.utils;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Void> handleValidationExceptions(MethodArgumentNotValidException ex) {
    return ResponseEntity.badRequest().build();
  }
}