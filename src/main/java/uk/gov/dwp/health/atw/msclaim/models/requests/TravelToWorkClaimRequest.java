package uk.gov.dwp.health.atw.msclaim.models.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.mongodb.core.mapping.Document;
import uk.gov.dwp.health.atw.msclaim.models.Evidence;
import uk.gov.dwp.health.atw.msclaim.models.TravelDetails;
import uk.gov.dwp.health.atw.msclaim.models.TravelToWork;
import uk.gov.dwp.health.atw.msclaim.models.validator.ConfirmTravelToWorkRequest;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Jacksonized
@SuperBuilder
@Document(collection = "claims")
@ConfirmTravelToWorkRequest
public class TravelToWorkClaimRequest extends ClaimRequestWithWorkplaceContact {

  @JsonProperty(value = "evidence")
  private List<Evidence> evidence;

  @JsonProperty(value = "travelDetails")
  @NotNull
  @NonNull
  private TravelDetails travelDetails;

  @JsonProperty(value = "claim")
  @NotNull
  @NonNull
  private Map<String, TravelToWork> claim;
}
