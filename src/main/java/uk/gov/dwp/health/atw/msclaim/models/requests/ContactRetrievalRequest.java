package uk.gov.dwp.health.atw.msclaim.models.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

@Data
@Jacksonized
@Builder
public class ContactRetrievalRequest {

  @JsonProperty(value = "id")
  @NotNull
  @NonNull
  String id;

  @JsonProperty(value = "accessToWorkNumber")
  @NotNull
  @NonNull
  String accessToWorkNumber;
}
