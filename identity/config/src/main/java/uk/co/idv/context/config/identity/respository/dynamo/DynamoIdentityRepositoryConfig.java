package uk.co.idv.context.config.identity.respository.dynamo;

import com.amazonaws.services.dynamodbv2.document.Table;
import uk.co.idv.context.config.identity.respository.IdentityRepositoryConfig;
import uk.co.idv.context.adapter.repository.DynamoIdentityRepository;
import uk.co.idv.context.adapter.repository.IdentityConverter;
import uk.co.idv.context.usecases.identity.IdentityRepository;
import uk.co.mruoc.json.JsonConverter;

import static uk.co.idv.context.config.identity.respository.dynamo.IdentityTableNames.IDENTITY_TABLE_NAME;

public class DynamoIdentityRepositoryConfig implements IdentityRepositoryConfig {

    private final IdentityRepository repository;

    public DynamoIdentityRepositoryConfig(JsonConverter jsonConverter, DynamoTables tables) {
        repository = buildIdentityRepository(jsonConverter, tables);
    }

    @Override
    public IdentityRepository identityRepository() {
        return repository;
    }

    private static IdentityRepository buildIdentityRepository(JsonConverter jsonConverter, DynamoTables tables) {
        Table table = tables.getEnvironmentTable(IDENTITY_TABLE_NAME);
        IdentityConverter identityConverter = new IdentityConverter(table, jsonConverter);
        return new DynamoIdentityRepository(identityConverter, tables.getDynamoDb());
    }

}
