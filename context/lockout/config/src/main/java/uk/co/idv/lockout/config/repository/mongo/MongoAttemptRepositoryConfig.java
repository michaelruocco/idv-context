package uk.co.idv.lockout.config.repository.mongo;

import com.mongodb.client.MongoDatabase;
import lombok.Builder;
import uk.co.idv.lockout.adapter.repository.MongoAttemptCollection;
import uk.co.idv.lockout.config.repository.AttemptRepositoryConfig;
import uk.co.idv.lockout.usecases.attempt.AttemptRepository;
import uk.co.idv.lockout.adapter.repository.MongoAttemptConverter;
import uk.co.idv.lockout.adapter.repository.MongoAttemptRepository;
import uk.co.mruoc.json.JsonConverter;

@Builder
public class MongoAttemptRepositoryConfig implements AttemptRepositoryConfig {

    private final JsonConverter jsonConverter;
    private final MongoDatabase database;

    @Override
    public AttemptRepository attemptRepository() {
        return MongoAttemptRepository.builder()
                .attemptConverter(new MongoAttemptConverter(jsonConverter))
                .collection(database.getCollection(MongoAttemptCollection.NAME))
                .build();
    }

}
