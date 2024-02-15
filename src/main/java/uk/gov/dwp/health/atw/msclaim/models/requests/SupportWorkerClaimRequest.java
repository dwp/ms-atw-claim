package uk.gov.dwp.health.atw.msclaim.models.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.mongodb.core.mapping.Document;
import uk.gov.dwp.health.atw.msclaim.models.Evidence;
import uk.gov.dwp.health.atw.msclaim.models.SupportWorker;
import uk.gov.dwp.health.atw.msclaim.models.validator.ConfirmSupportWorkerRequest;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Jacksonized
@SuperBuilder
@Document(collection = "claims")
@ConfirmSupportWorkerRequest
public class SupportWorkerClaimRequest extends ClaimRequestWithWorkplaceContact {

  @JsonProperty(value = "claim")
  @NotNull
  @NonNull
  private Map<String, SupportWorker> claim;

  @JsonProperty(value = "evidence")
  @NotNull
  @NonNull
  private List<Evidence> evidence;

  @JsonProperty(value = "nameOfSupport")
  String nameOfSupport;
}
