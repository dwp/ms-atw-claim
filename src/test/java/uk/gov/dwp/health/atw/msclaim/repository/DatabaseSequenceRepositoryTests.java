package uk.gov.dwp.health.atw.msclaim.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.data.mongodb.core.query.Criteria.where;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import uk.gov.dwp.health.atw.msclaim.models.DatabaseSequence;
import uk.gov.dwp.health.atw.msclaim.repositories.DatabaseSequenceRepository;

@SpringBootTest(classes = DatabaseSequenceRepository.class)
public class DatabaseSequenceRepositoryTests {

  @MockBean
  MongoOperations mongoOperations;

  @Autowired
  DatabaseSequenceRepository databaseSequenceRepository;

  final String sequenceName = "test_sequence";
  final Query query = new Query(where("_id").is(sequenceName));
  final Update update = new Update().inc("seq", 1);

  @Test
  @DisplayName("getNextValueInSequence should return 2 if sequence found")
  public void getNextSequence() {

    when(mongoOperations.findAndModify(
        eq(query),
        eq(update),
        any(FindAndModifyOptions.class),
        eq(DatabaseSequence.class))).thenReturn(new DatabaseSequence("uuid", 2L));

    assertEquals(2L, databaseSequenceRepository.getNextValueInSequence(sequenceName));
  }

  @Test
  @DisplayName("getNextValueInSequence should return 1 if no sequence found")
  public void get1WhenNoSequenceFound() {
    when(mongoOperations.findAndModify(
        eq(query),
        eq(update),
        any(FindAndModifyOptions.class),
        eq(DatabaseSequence.class))).thenReturn(null);

    assertEquals(1L, databaseSequenceRepository.getNextValueInSequence("new_sequence"));
  }
}
