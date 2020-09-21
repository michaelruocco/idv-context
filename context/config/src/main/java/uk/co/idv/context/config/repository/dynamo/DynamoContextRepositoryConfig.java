package uk.co.idv.context.config.repository.dynamo;

import lombok.Builder;
import uk.co.idv.context.adapter.dynamo.DynamoTables;
import uk.co.idv.context.adapter.repository.ContextItemConverter;
import uk.co.idv.context.adapter.repository.DynamoContextRepository;
import uk.co.idv.context.config.repository.ContextRepositoryConfig;
import uk.co.idv.context.usecases.context.ContextRepository;
import uk.co.mruoc.json.JsonConverter;

@Builder
public class DynamoContextRepositoryConfig implements ContextRepositoryConfig {

    public static final String CONTEXT_TABLE_NAME = "context";

    private final JsonConverter jsonConverter;
    private final DynamoTables tables;

    @Override
    public ContextRepository contextRepository() {
        return DynamoContextRepository.builder()
                .converter(new ContextItemConverter(jsonConverter))
                .table(tables.getTable(CONTEXT_TABLE_NAME))
                .build();
    }

}
