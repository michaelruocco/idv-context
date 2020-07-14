package uk.co.idv.config.repository.dynamo;

import uk.co.idv.config.repository.RepositoryConfig;
import uk.co.idv.context.adapter.repository.DynamoIdentityRepository;
import uk.co.idv.context.adapter.repository.IdentityConverter;
import uk.co.idv.context.usecases.identity.IdentityRepository;
import uk.co.mruoc.json.JsonConverter;

public class DynamoRepositoryConfig implements RepositoryConfig {

    private final IdentityRepository repository;

    public DynamoRepositoryConfig(JsonConverter jsonConverter, DynamoTables tables) {
        repository = buildIdentityRepository(jsonConverter, tables);
    }

    @Override
    public IdentityRepository identityRepository() {
        return repository;
    }

    private static IdentityRepository buildIdentityRepository(JsonConverter jsonConverter, DynamoTables tables) {
        IdentityConverter identityConverter = new IdentityConverter(tables.getIdentityTable(), jsonConverter);
        return new DynamoIdentityRepository(identityConverter, tables.getDynamoDb());
    }

}
