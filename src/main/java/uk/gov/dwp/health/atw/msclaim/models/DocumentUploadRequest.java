package uk.gov.dwp.health.atw.msclaim.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentUploadRequest extends ClaimReferenceRequest {

  @JsonProperty(value = "requestId")
  @NotNull
  @NonNull
  String requestId;
}
