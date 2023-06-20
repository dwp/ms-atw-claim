package uk.gov.dwp.health.atw.msclaim.repositories;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import uk.gov.dwp.health.atw.msclaim.models.DatabaseSequence;

@Repository
public class DatabaseSequenceRepository {
  private final MongoOperations mongoOperations;

  @Autowired
  public DatabaseSequenceRepository(MongoOperations mongoOperations) {
    this.mongoOperations = mongoOperations;
  }

  public long getNextValueInSequence(String seqName) {
    DatabaseSequence counter =
        mongoOperations.findAndModify(
            query(where("_id").is(seqName)),
            new Update().inc("seq", 1),
            options().returnNew(true).upsert(true),
            DatabaseSequence.class);
    return !Objects.isNull(counter) ? counter.getSeq() : 1L;

  }
}
