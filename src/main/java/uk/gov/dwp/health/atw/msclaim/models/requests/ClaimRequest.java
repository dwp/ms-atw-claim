package uk.gov.dwp.health.atw.msclaim.models.requests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.time.LocalDateTime;
import java.util.Map;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Transient;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import uk.gov.dwp.health.atw.msclaim.models.Claimant;
import uk.gov.dwp.health.atw.msclaim.models.Payee;
import uk.gov.dwp.health.atw.msclaim.models.enums.ClaimStatus;
import uk.gov.dwp.health.atw.msclaim.models.enums.ClaimType;
import uk.gov.dwp.health.atw.msclaim.models.validator.ConfirmPayee;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "claims")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY,
    property = "claimType", visible = true)
@JsonSubTypes({
    @JsonSubTypes.Type(value = EquipmentOrAdaptationClaimRequest.class,
        name = "EQUIPMENT_OR_ADAPTATION"),
    @JsonSubTypes.Type(value = TravelToWorkClaimRequest.class, name = "TRAVEL_TO_WORK"),
    @JsonSubTypes.Type(value = TravelInWorkClaimRequest.class, name = "TRAVEL_IN_WORK"),
    @JsonSubTypes.Type(value = SupportWorkerClaimRequest.class, name = "SUPPORT_WORKER"),
    @JsonSubTypes.Type(value = AdaptationToVehicleClaimRequest.class,
        name = "ADAPTATION_TO_VEHICLE")
})
public class ClaimRequest {

  @Transient
  public static final String SEQUENCE_NAME = "claim_sequence";

  @Version
  @JsonIgnore
  private Long version;

  @Id
  private long id;

  @Indexed
  @JsonProperty(value = "nino")
  @NotNull
  @NonNull
  @Size(min = 8, max = 9)
  private String nino;

  @JsonProperty(value = "atwNumber")
  @NotNull
  @NonNull
  private String atwNumber;

  @JsonProperty(value = "claimType")
  @NotNull
  @NonNull
  private ClaimType claimType;

  @JsonProperty(value = "cost")
  private Double cost;

  @JsonProperty(value = "hasContributions")
  @NotNull
  @NonNull
  private Boolean hasContributions;

  @JsonProperty(value = "claimant")
  @NotNull
  @NonNull
  private Claimant claimant;

  @JsonProperty(value = "payee")
  @NotNull
  @NonNull
  @ConfirmPayee
  private Payee payee;

  @JsonProperty(value = "declarationVersion")
  @NotNull
  @NonNull
  private Double declarationVersion;

  @JsonProperty(value = "journeyContext")
  @NotNull
  @NonNull
  private Map<String, Object> journeyContext;

  @CreatedDate
  private LocalDateTime createdDate;

  @LastModifiedDate
  private LocalDateTime lastModifiedDate;

  ClaimStatus claimStatus;

  @JsonProperty(value = "previousClaimId")
  private Long previousClaimId;

  @Indexed(sparse = true)
  @JsonProperty(value = "documentBatchRequestId")
  private String documentBatchRequestId;
}
