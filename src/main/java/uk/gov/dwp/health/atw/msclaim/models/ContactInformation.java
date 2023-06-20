package uk.gov.dwp.health.atw.msclaim.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Data
@Jacksonized
@SuperBuilder
@NoArgsConstructor
public class ContactInformation {

  @JsonProperty(value = "forename")
  @NotNull
  @NonNull
  String forename;

  @JsonProperty(value = "surname")
  @NotNull
  @NonNull
  String surname;

  @JsonProperty(value = "dateOfBirth")
  @JsonSerialize(using = LocalDateSerializer.class)
  @JsonDeserialize(using = LocalDateDeserializer.class)
  LocalDate dateOfBirth;

  @JsonProperty(value = "emailAddress")
  String emailAddress;

  @JsonProperty(value = "homeNumber")
  String homeNumber;

  @JsonProperty(value = "mobileNumber")
  String mobileNumber;

  @JsonProperty(value = "address")
  Address address;
}
