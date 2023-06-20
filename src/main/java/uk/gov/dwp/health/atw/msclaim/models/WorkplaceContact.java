package uk.gov.dwp.health.atw.msclaim.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.time.LocalDateTime;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Jacksonized
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WorkplaceContact {

  @JsonProperty(value = "emailAddress")
  String emailAddress;

  @JsonProperty(value = "fullName")
  String fullName;

  @JsonProperty(value = "organisation")
  String organisation;

  @JsonProperty(value = "jobTitle")
  String jobTitle;

  @JsonProperty(value = "address")
  Address address;

  @JsonProperty(value = "reason")
  @Size(max = 300)
  String reason;

  @JsonProperty(value = "updatedOn")
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  LocalDateTime updatedOn;

  @JsonProperty(value = "employmentStatus")
  String employmentStatus;

  @JsonProperty(value = "declarationVersion")
  private Double declarationVersion;
}


