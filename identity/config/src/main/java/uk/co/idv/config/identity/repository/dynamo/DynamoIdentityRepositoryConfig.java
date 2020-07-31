package uk.co.idv.config.identity.repository.dynamo;

import uk.co.idv.config.identity.repository.IdentityRepositoryConfig;
import uk.co.idv.context.adapter.repository.DynamoIdentityRepository;
import uk.co.idv.context.adapter.repository.IdentityConverter;
import uk.co.idv.context.usecases.identity.IdentityRepository;
import uk.co.mruoc.json.JsonConverter;

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
        IdentityConverter identityConverter = new IdentityConverter(tables.getIdentityTable(), jsonConverter);
        return new DynamoIdentityRepository(identityConverter, tables.getDynamoDb());
    }

}
