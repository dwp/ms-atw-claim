package uk.gov.dwp.health.atw.msclaim.models.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;
import uk.gov.dwp.health.atw.msclaim.models.Address;
import uk.gov.dwp.health.atw.msclaim.models.enums.ClaimType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Jacksonized
@Builder
public class WorkplaceContactRequest {

  @NonNull
  @NotNull
  @JsonProperty(value = "claimNumber")
  private long claimNumber;

  @NonNull
  @NotNull
  @JsonProperty(value = "claimType")
  private ClaimType claimType;

  @JsonProperty(value = "organisation")
  @NonNull
  @NotNull
  String organisation;

  @JsonProperty(value = "jobTitle")
  @NonNull
  @NotNull
  String jobTitle;

  @JsonProperty(value = "address")
  @NonNull
  @NotNull
  Address address;

  @JsonProperty(value = "reason")
  @Size(max = 300)
  String reason;

  @JsonProperty(value = "declarationVersion")
  private Double declarationVersion;
}
