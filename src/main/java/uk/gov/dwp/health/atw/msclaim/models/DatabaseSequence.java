package uk.gov.dwp.health.atw.msclaim.models;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "database_sequences")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DatabaseSequence {

  @Id
  private String id;

  @NotNull
  @NonNull
  private long seq;

}
