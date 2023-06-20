package uk.gov.dwp.health.atw.msclaim.models.validator;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = EquipmentOrAdaptationRequestValidator.class)
@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Documented
public @interface ConfirmEquipmentOrAdaptationRequest {
  String message() default "Error with Support Worker Request";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
