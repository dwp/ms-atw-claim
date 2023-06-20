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
public class Address {

  @JsonProperty(value = "address1")
  String address1;

  @JsonProperty(value = "address2")
  String address2;

  @JsonProperty(value = "address3")
  String address3;

  @JsonProperty(value = "address4")
  String address4;

  @JsonProperty(value = "postcode")
  String postcode;
}
