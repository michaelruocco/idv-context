package uk.co.idv.lockout.config.repository.mongo;

import com.mongodb.client.MongoDatabase;
import lombok.Builder;
import uk.co.idv.lockout.config.repository.AttemptRepositoryConfig;
import uk.co.idv.lockout.usecases.attempt.AttemptRepository;
import uk.co.idv.ockout.adapter.repository.AttemptConverter;
import uk.co.idv.ockout.adapter.repository.MongoAttemptRepository;
import uk.co.mruoc.json.JsonConverter;

@Builder
public class MongoAttemptRepositoryConfig implements AttemptRepositoryConfig {

    public static final String ATTEMPT_TABLE_NAME = "attempt";

    private final JsonConverter jsonConverter;
    private final MongoDatabase database;

    @Override
    public AttemptRepository attemptRepository() {
        return MongoAttemptRepository.builder()
                .attemptConverter(new AttemptConverter(jsonConverter))
                .collection(database.getCollection(ATTEMPT_TABLE_NAME))
                .build();
    }

}
