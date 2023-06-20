package uk.gov.dwp.health.atw.msclaim.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import uk.gov.dwp.health.atw.msclaim.models.enums.ClaimType;
import uk.gov.dwp.health.atw.msclaim.models.exceptions.ClaimException;
import uk.gov.dwp.health.atw.msclaim.models.exceptions.WrongClaimOrBadRequestException;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ClaimReferenceRequest {

  @JsonProperty(value = "claimReference")
  @NotNull
  @NonNull
  String claimReference;

  public String getClaimType() throws ClaimException {
    return ClaimType.valueOfLabel(splitClaimReference()[0]).toString();
  }

  public String getClaimNumber() throws ClaimException {
    return splitClaimReference()[1].trim();
  }

  private String[] splitClaimReference() throws ClaimException {
    String[] claimRefSplit = claimReference.split("(?<=\\D)(?=\\d)");
    if (claimRefSplit.length != 2) {
      throw new WrongClaimOrBadRequestException(
          "Invalid claim reference format. Expected format is for example SW001");
    }

    return claimRefSplit;
  }
}
