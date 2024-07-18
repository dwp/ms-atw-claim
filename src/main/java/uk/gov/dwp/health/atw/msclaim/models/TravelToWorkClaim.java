package uk.gov.dwp.health.atw.msclaim.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

@Data
@Jacksonized
@Builder
public class TravelToWorkClaim {

  @JsonProperty(value = "dayOfTravel")
  @NotNull
  @NonNull
  int dayOfTravel;

  @JsonProperty(value = "totalTravel")
  @NotNull
  @NonNull
  double totalTravel;
}
