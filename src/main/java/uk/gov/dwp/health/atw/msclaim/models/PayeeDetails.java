package uk.gov.dwp.health.atw.msclaim.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

@Data
@Jacksonized
@Builder
public class PayeeDetails {

  @JsonProperty(value = "fullName")
  @NotNull
  @NonNull
  String fullName;

  @JsonProperty(value = "emailAddress")
  String emailAddress;
}
