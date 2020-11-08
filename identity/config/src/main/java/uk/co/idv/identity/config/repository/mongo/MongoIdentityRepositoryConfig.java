package uk.co.idv.identity.config.repository.mongo;

import com.mongodb.client.MongoDatabase;
import uk.co.idv.identity.adapter.repository.AliasConverter;
import uk.co.idv.identity.adapter.repository.AliasesConverter;
import uk.co.idv.identity.adapter.repository.MongoIdentityConverter;
import uk.co.idv.identity.adapter.repository.MongoIdentityRepository;
import uk.co.idv.identity.config.repository.IdentityRepositoryConfig;
import uk.co.idv.identity.usecases.identity.IdentityRepository;
import uk.co.mruoc.json.JsonConverter;

public class MongoIdentityRepositoryConfig implements IdentityRepositoryConfig {

    public static final String IDENTITY_TABLE_NAME = "identity";

    private final IdentityRepository repository;

    public MongoIdentityRepositoryConfig(JsonConverter jsonConverter, MongoDatabase database) {
        repository = buildIdentityRepository(jsonConverter, database);
    }

    @Override
    public IdentityRepository identityRepository() {
        return repository;
    }

    private static IdentityRepository buildIdentityRepository(JsonConverter jsonConverter, MongoDatabase database) {
        return MongoIdentityRepository.builder()
                .aliasesConverter(new AliasesConverter(new AliasConverter()))
                .identityConverter(new MongoIdentityConverter(jsonConverter))
                .collection(database.getCollection(IDENTITY_TABLE_NAME))
                .build();
    }

}
