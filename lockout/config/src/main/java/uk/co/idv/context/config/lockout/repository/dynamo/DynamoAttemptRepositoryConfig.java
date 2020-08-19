package uk.co.idv.context.config.lockout.repository.dynamo;

import lombok.Builder;
import uk.co.idv.context.adapter.dynamo.DynamoTables;
import uk.co.idv.context.adapter.repository.AttemptItemConverter;
import uk.co.idv.context.adapter.repository.DynamoAttemptRepository;
import uk.co.idv.context.config.lockout.repository.AttemptRepositoryConfig;
import uk.co.idv.context.usecases.lockout.attempt.AttemptRepository;
import uk.co.mruoc.json.JsonConverter;

import static uk.co.idv.context.config.lockout.repository.dynamo.LockoutTableNames.ATTEMPT_TABLE_NAME;

@Builder
public class DynamoAttemptRepositoryConfig implements AttemptRepositoryConfig {

    private final JsonConverter jsonConverter;
    private final DynamoTables tables;

    @Override
    public AttemptRepository attemptRepository() {
        return DynamoAttemptRepository.builder()
                .converter(new AttemptItemConverter(jsonConverter))
                .table(tables.getEnvironmentTable(ATTEMPT_TABLE_NAME))
                .build();
    }

}
