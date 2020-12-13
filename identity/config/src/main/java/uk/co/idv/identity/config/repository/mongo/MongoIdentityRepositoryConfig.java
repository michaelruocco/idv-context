package uk.co.idv.identity.config.repository.mongo;

import com.mongodb.client.MongoDatabase;
import lombok.Builder;
import uk.co.idv.identity.adapter.repository.MongoIdentityRepository;
import uk.co.idv.identity.adapter.repository.converter.IdentityDocumentsConverter;
import uk.co.idv.identity.adapter.repository.query.AliasQueryBuilder;
import uk.co.idv.identity.adapter.repository.document.IdentityDocument;
import uk.co.idv.identity.config.repository.IdentityRepositoryConfig;
import uk.co.idv.identity.entities.alias.AliasFactory;
import uk.co.idv.identity.usecases.identity.IdentityRepository;

@Builder
public class MongoIdentityRepositoryConfig implements IdentityRepositoryConfig {

    public static final String IDENTITY_TABLE_NAME = "identity";

    private final AliasFactory aliasFactory;
    private final MongoDatabase database;

    @Override
    public IdentityRepository identityRepository() {
        return MongoIdentityRepository.builder()
                .queryBuilder(new AliasQueryBuilder())
                .identityConverter(IdentityDocumentsConverter.build(aliasFactory))
                .collection(database.getCollection(IDENTITY_TABLE_NAME, IdentityDocument.class))
                .build();
    }

}
