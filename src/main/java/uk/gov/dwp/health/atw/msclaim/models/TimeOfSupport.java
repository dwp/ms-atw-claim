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
public class TimeOfSupport {

  @JsonProperty(value = "hoursOfSupport")
  @NotNull
  @NonNull
  Integer hoursOfSupport;

  @JsonProperty(value = "minutesOfSupport")
  @NotNull
  @NonNull
  Integer minutesOfSupport;
}
