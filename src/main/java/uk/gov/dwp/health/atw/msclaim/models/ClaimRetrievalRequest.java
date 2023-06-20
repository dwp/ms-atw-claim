package uk.gov.dwp.health.atw.msclaim.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;
import uk.gov.dwp.health.atw.msclaim.models.enums.ClaimType;

@Data
@Jacksonized
@Builder
public class ClaimRetrievalRequest {

  @JsonProperty(value = "nino")
  @NotNull
  @NonNull
  String nino;

  @JsonProperty(value = "claimType")
  @NotNull
  @NonNull
  private ClaimType claimType;
}
