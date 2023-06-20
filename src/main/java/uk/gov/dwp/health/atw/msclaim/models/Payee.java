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
public class Payee {

  @JsonProperty(value = "details")
  @NotNull
  @NonNull
  PayeeDetails details;

  @JsonProperty(value = "address")
  Address address;

  @JsonProperty(value = "bankDetails")
  BankDetails bankDetails;

  @JsonProperty(value = "newPayee")
  @NotNull
  @NonNull
  boolean newPayee;

}
