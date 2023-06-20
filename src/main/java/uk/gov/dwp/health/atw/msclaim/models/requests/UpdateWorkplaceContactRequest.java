package uk.gov.dwp.health.atw.msclaim.models.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import uk.gov.dwp.health.atw.msclaim.models.ClaimReferenceNinoRequest;
import uk.gov.dwp.health.atw.msclaim.models.WorkplaceContact;

@EqualsAndHashCode
@Data
@AllArgsConstructor
@NoArgsConstructor
@Jacksonized
@SuperBuilder
public class UpdateWorkplaceContactRequest extends ClaimReferenceNinoRequest {

  @JsonProperty(value = "workplaceContact")
  @NotNull
  @NonNull
  private WorkplaceContact workplaceContact;
}
