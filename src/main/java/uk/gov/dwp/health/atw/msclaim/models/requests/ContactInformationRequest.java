package uk.gov.dwp.health.atw.msclaim.models.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import uk.gov.dwp.health.atw.msclaim.models.ContactInformation;
import uk.gov.dwp.health.atw.msclaim.models.enums.ContactInformationStatus;

@Data
@NoArgsConstructor
@Jacksonized
@SuperBuilder
@Document(collection = "contact_information")
public class ContactInformationRequest {

  @Id
  String id;

  @JsonProperty(value = "accessToWorkNumber")
  @NotNull
  @NonNull
  String accessToWorkNumber;

  @JsonProperty(value = "nino")
  @NotNull
  @NonNull
  String nino;

  @JsonProperty(value = "declarationVersion")
  @NotNull
  @NonNull
  Double declarationVersion;

  @JsonProperty(value = "currentContactInformation")
  @NotNull
  @NonNull
  private ContactInformation currentContactInformation;

  @JsonProperty(value = "newContactInformation")
  @NotNull
  @NonNull
  ContactInformation newContactInformation;

  ContactInformationStatus contactInformationStatus;

  @CreatedDate
  LocalDateTime createdDate;
}
