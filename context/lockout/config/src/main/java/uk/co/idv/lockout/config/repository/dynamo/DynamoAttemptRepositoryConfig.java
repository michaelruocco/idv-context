package uk.co.idv.lockout.config.repository.dynamo;

import com.amazonaws.services.dynamodbv2.document.Table;
import lombok.Builder;
import uk.co.idv.context.adapter.dynamo.DynamoTables;
import uk.co.idv.lockout.adapter.repository.AttemptItemConverter;
import uk.co.idv.lockout.adapter.repository.DynamoAttemptRepository;
import uk.co.idv.lockout.config.repository.AttemptRepositoryConfig;
import uk.co.idv.lockout.usecases.attempt.AttemptRepository;
import uk.co.mruoc.json.JsonConverter;

@Builder
public class DynamoAttemptRepositoryConfig implements AttemptRepositoryConfig {

    public static final String ATTEMPT_TABLE_NAME = "attempt";

    private final JsonConverter jsonConverter;
    private final DynamoTables tables;

    @Override
    public AttemptRepository attemptRepository() {
        Table table = tables.getTable(ATTEMPT_TABLE_NAME);
        return DynamoAttemptRepository.builder()
                .dynamoDb(tables.getDynamoDb())
                .converter(toItemConverter(table))
                .table(table)
                .build();
    }

    private AttemptItemConverter toItemConverter(Table table) {
        return AttemptItemConverter.builder()
                .jsonConverter(jsonConverter)
                .table(table)
                .build();
    }

}
