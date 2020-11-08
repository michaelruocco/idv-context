package uk.co.idv.identity.config.repository.dynamo;

import com.amazonaws.services.dynamodbv2.document.Table;
import uk.co.idv.context.adapter.dynamo.DynamoTables;
import uk.co.idv.identity.adapter.repository.DynamoIdentityConverter;
import uk.co.idv.identity.config.repository.IdentityRepositoryConfig;
import uk.co.idv.identity.adapter.repository.DynamoIdentityRepository;
import uk.co.idv.identity.usecases.identity.IdentityRepository;
import uk.co.mruoc.json.JsonConverter;

public class DynamoIdentityRepositoryConfig implements IdentityRepositoryConfig {

    public static final String IDENTITY_TABLE_NAME = "identity";

    private final IdentityRepository repository;

    public DynamoIdentityRepositoryConfig(JsonConverter jsonConverter, DynamoTables tables) {
        repository = buildIdentityRepository(jsonConverter, tables);
    }

    @Override
    public IdentityRepository identityRepository() {
        return repository;
    }

    private static IdentityRepository buildIdentityRepository(JsonConverter jsonConverter, DynamoTables tables) {
        Table table = tables.getTable(IDENTITY_TABLE_NAME);
        DynamoIdentityConverter identityConverter = new DynamoIdentityConverter(table, jsonConverter);
        return DynamoIdentityRepository.builder()
                .converter(identityConverter)
                .dynamoDb(tables.getDynamoDb())
                .table(table)
                .build();
    }

}
