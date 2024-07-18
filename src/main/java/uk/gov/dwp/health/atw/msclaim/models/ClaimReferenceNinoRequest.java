package uk.gov.dwp.health.atw.msclaim.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Jacksonized
@SuperBuilder
public class ClaimReferenceNinoRequest extends ClaimReferenceRequest {

  @JsonProperty(value = "nino")
  @NotNull
  @NonNull
  String nino;

}
