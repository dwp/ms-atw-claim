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
public class EquipmentOrAdaptation {

  @JsonProperty(value = "description")
  @NotNull
  @NonNull
  String description;

  @JsonProperty(value = "dateOfPurchase")
  @NotNull
  @NonNull
  Date dateOfPurchase;
}
