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
public class SupportWorkerClaim {

  @JsonProperty(value = "dayOfSupport")
  @NotNull
  @NonNull
  Integer dayOfSupport;

  @JsonProperty(value = "timeOfSupport")
  TimeOfSupport timeOfSupport;

  /**
   * This field should no longer be used. However, support is still required to account
   * for cases where a support worker claim was created before the TimeOfSupport field was
   * implemented. Support for the hoursOfSupport field will be removed in the future.
   *
   * @Deprecated
   */
  @Deprecated(since = "1.7.0")
  @JsonProperty(value = "hoursOfSupport")
  Double hoursOfSupport;

  /**
   * This field should no longer be used. However, support is still required to account
   * for cases where a support worker claim was created before the nameOfSupport field was
   * moved to the SupportWorker object. This is due to changes leading to nameOfSupport only being
   * input once per claim. Support for the nameOfSupport field on SupportWorkerClaim will be
   * removed in the future.
   *
   * @Deprecated
   */
  @Deprecated(since = "1.15.0")
  @JsonProperty(value = "nameOfSupport")
  String nameOfSupport;
}
