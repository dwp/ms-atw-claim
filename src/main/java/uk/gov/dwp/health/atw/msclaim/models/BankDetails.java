package uk.gov.dwp.health.atw.msclaim.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

@Data
@Jacksonized
@Builder
public class BankDetails {

  @JsonProperty(value = "accountHolderName")
  String accountHolderName;

  @JsonProperty(value = "sortCode")
  String sortCode;

  @JsonProperty(value = "accountNumber")
  String accountNumber;

  @JsonProperty(value = "rollNumber")
  String rollNumber;
}
