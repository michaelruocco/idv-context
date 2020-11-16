package uk.co.idv.identity.adapter.repository;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.bson.conversions.Bson;
import uk.co.idv.common.mongo.MongoDurationLogger;
import uk.co.idv.identity.adapter.repository.query.AliasesQueryBuilder;
import uk.co.idv.identity.adapter.repository.converter.IdentityDocumentConverter;
import uk.co.idv.identity.adapter.repository.type.IdentityDocument;
import uk.co.idv.identity.entities.alias.Alias;
import uk.co.idv.identity.entities.alias.Aliases;
import uk.co.idv.identity.entities.alias.IdvId;
import uk.co.idv.identity.entities.identity.Identities;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.identity.usecases.identity.IdentityRepository;

import java.time.Instant;
import java.util.Optional;

@Slf4j
@Builder
public class MongoIdentityRepository implements IdentityRepository {

    private final MongoCollection<IdentityDocument> collection;
    private final AliasesQueryBuilder queryBuilder;
    private final IdentityDocumentConverter identityConverter;

    @Override
    public Optional<Identity> load(Alias alias) {
        Instant start = Instant.now();
        try {
            FindIterable<IdentityDocument> documents = collection.find(toFindByAliasQuery(alias));
            return Optional.ofNullable(documents.first()).map(this::toIdentity);
        } finally {
            MongoDurationLogger.log("load-identity-by-alias", start);
        }
    }

    @Override
    public Identities load(Aliases aliases) {
        Instant start = Instant.now();
        try {
            Bson query = toFindByAliasesQuery(aliases);
            log.debug("finding identities with query {}", query);
            FindIterable<IdentityDocument> documents = collection.find(query);
            return toIdentities(documents);
        } finally {
            MongoDurationLogger.log("load-identity-by-aliases", start);
        }
    }

    @Override
    public void create(Identity identity) {
        Instant start = Instant.now();
        try {
            collection.insertOne(toDocument(identity));
        } finally {
            MongoDurationLogger.log("create-identity", start);
        }
    }

    @Override
    public void update(Identity updated, Identity existing) {
        Instant start = Instant.now();
        try {
            collection.replaceOne(toFindByIdvIdQuery(updated.getIdvId()), toDocument(updated));
        } finally {
            MongoDurationLogger.log("update-identity", start);
        }
    }

    @Override
    public void delete(Aliases aliases) {
        Instant start = Instant.now();
        try {
            collection.deleteMany(toFindByAliasesQuery(aliases));
        } finally {
            MongoDurationLogger.log("delete-identity-by-aliases", start);
        }
    }

    private Identity toIdentity(IdentityDocument document) {
        return identityConverter.toIdentity(document);
    }

    private Identities toIdentities(FindIterable<IdentityDocument> documents) {
        return identityConverter.toIdentities(documents);
    }

    private IdentityDocument toDocument(Identity identity) {
        return identityConverter.toDocument(identity);
    }

    private Bson toFindByAliasesQuery(Aliases aliases) {
        return queryBuilder.toFindByAliasesQuery(aliases);
    }

    private Bson toFindByAliasQuery(Alias alias) {
        return queryBuilder.toFindByAliasQuery(alias);
    }

    private Bson toFindByIdvIdQuery(IdvId idvId) {
        return queryBuilder.toFindByIdvIdQuery(idvId);
    }

}
