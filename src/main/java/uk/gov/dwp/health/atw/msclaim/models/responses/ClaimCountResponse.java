package uk.gov.dwp.health.atw.msclaim.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClaimCountResponse {

  @JsonProperty(value = "claimType")
  @NonNull
  @NotNull
  private String claimType;

  @JsonProperty(value = "count")
  @NonNull
  @NotNull
  private long count;

}
