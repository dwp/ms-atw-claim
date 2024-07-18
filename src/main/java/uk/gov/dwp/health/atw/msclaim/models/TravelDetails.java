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
public class TravelDetails {

  @JsonProperty(value = "howDidYouTravelForWork")
  @NotNull
  @NonNull
  String howDidYouTravelForWork;

  @JsonProperty(value = "journeysOrMileage")
  String journeysOrMileage;
}
