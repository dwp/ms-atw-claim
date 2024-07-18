package uk.gov.dwp.health.atw.msclaim.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import uk.gov.dwp.health.atw.msclaim.models.enums.ClaimType;

@Getter
@SuperBuilder
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
public class ClaimResponse {

  @JsonProperty(value = "claimNumber")
  @NonNull
  @NotNull
  private long claimNumber;

  @JsonProperty(value = "claimType")
  @NotNull
  @NonNull
  private ClaimType claimType;

  public String getClaimReference() {
    return claimType.label + claimNumber;
  }
}
