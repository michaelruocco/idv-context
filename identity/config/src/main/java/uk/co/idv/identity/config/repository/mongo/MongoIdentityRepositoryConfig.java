package uk.co.idv.identity.config.repository.mongo;

import com.mongodb.client.MongoDatabase;
import lombok.RequiredArgsConstructor;
import uk.co.idv.identity.adapter.repository.MongoIdentityCollection;
import uk.co.idv.identity.adapter.repository.MongoIdentityRepository;
import uk.co.idv.identity.adapter.repository.converter.IdentityDocumentsConverter;
import uk.co.idv.identity.adapter.repository.query.AliasQueryBuilder;
import uk.co.idv.identity.config.repository.IdentityRepositoryConfig;
import uk.co.idv.identity.entities.alias.AliasFactory;
import uk.co.idv.identity.usecases.identity.IdentityRepository;

@RequiredArgsConstructor
public class MongoIdentityRepositoryConfig implements IdentityRepositoryConfig {

    private final IdentityRepository repository;

    public MongoIdentityRepositoryConfig(MongoDatabase database,
                                        AliasFactory aliasFactory) {
        this(toIdentityRepository(database, aliasFactory));
    }

    @Override
    public IdentityRepository identityRepository() {
        return repository;
    }

    public String getChangeLogPackageName() {
        return MongoIdentityChangeLog.class.getPackageName();
    }

    private static IdentityRepository toIdentityRepository(MongoDatabase database, AliasFactory aliasFactory) {
        return MongoIdentityRepository.builder()
                .queryBuilder(new AliasQueryBuilder())
                .identityConverter(IdentityDocumentsConverter.build(aliasFactory))
                .collection(MongoIdentityCollection.get(database))
                .build();
    }

}
