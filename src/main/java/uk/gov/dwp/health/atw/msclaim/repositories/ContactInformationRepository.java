package uk.gov.dwp.health.atw.msclaim.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import uk.gov.dwp.health.atw.msclaim.models.requests.ContactInformationRequest;

@Repository
public interface ContactInformationRepository
    extends MongoRepository<ContactInformationRequest, String> {

  ContactInformationRequest findContactInformationById(String id);
}
