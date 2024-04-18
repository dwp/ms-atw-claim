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
public class TravelInWorkClaim {

  @JsonProperty(value = "dayOfTravel")
  @NotNull
  @NonNull
  int dayOfTravel;

  @JsonProperty(value = "startPostcode")
  @NotNull
  @NonNull
  String startPostcode;

  @JsonProperty(value = "endPostcode")
  @NotNull
  @NonNull
  String endPostcode;

  @JsonProperty(value = "costOfTravel")
  @NotNull
  @NonNull
  double costOfTravel;
}
