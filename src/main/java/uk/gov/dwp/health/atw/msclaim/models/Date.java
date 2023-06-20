package uk.gov.dwp.health.atw.msclaim.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

@Data
@Jacksonized
@Builder
public class Date {

  @JsonProperty(value = "dd")
  @NotNull
  @NonNull
  String dd;

  @JsonProperty(value = "mm")
  @NotNull
  @NonNull
  String mm;

  @JsonProperty(value = "yyyy")
  @NotNull
  @NonNull
  String yyyy;
}
