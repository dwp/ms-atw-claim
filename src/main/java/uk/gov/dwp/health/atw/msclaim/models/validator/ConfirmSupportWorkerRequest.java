package uk.gov.dwp.health.atw.msclaim.models.validator;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Constraint(validatedBy = SupportWorkerRequestValidator.class)
@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Documented
public @interface ConfirmSupportWorkerRequest {
  String message() default "Error with Support Worker Request";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
