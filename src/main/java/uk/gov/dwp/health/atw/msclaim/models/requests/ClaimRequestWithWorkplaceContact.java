package uk.gov.dwp.health.atw.msclaim.models.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import uk.gov.dwp.health.atw.msclaim.models.WorkplaceContact;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ClaimRequestWithWorkplaceContact extends ClaimRequest {
  @JsonProperty(value = "workplaceContact")
  @NotNull
  @NonNull
  private WorkplaceContact workplaceContact;
}
