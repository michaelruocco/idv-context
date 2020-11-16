package uk.co.idv.context.config.repository.mongo;

import com.mongodb.client.MongoDatabase;
import lombok.Builder;
import uk.co.idv.context.adapter.repository.ContextConverter;
import uk.co.idv.context.adapter.repository.MongoContextRepository;
import uk.co.idv.context.config.repository.ContextRepositoryConfig;
import uk.co.idv.context.usecases.context.ContextRepository;
import uk.co.mruoc.json.JsonConverter;

@Builder
public class MongoContextRepositoryConfig implements ContextRepositoryConfig {

    public static final String CONTEXT_TABLE_NAME = "context";

    private final JsonConverter jsonConverter;
    private final MongoDatabase database;

    @Override
    public ContextRepository contextRepository() {
        return MongoContextRepository.builder()
                .contextConverter(new ContextConverter(jsonConverter))
                .collection(database.getCollection(CONTEXT_TABLE_NAME))
                .build();
    }

}
