package uk.gov.dwp.health.atw.msclaim.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Data
@NoArgsConstructor
@Jacksonized
@SuperBuilder
public class NinoRequest {

  @JsonProperty(value = "nino")
  @NotNull
  @NonNull
  String nino;

}
