package uk.gov.dwp.health.atw.msclaim.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

@Data
@Jacksonized
@Builder
public class TravelToWork {

  @JsonProperty(value = "monthYear")
  @NotNull
  @NonNull
  MonthYear monthYear;

  @JsonProperty(value = "claim")
  @NotNull
  @NonNull
  List<TravelToWorkClaim> claim;
}
