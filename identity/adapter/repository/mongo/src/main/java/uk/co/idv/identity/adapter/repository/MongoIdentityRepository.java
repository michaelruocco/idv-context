package uk.co.idv.identity.adapter.repository;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.conversions.Bson;
import uk.co.idv.identity.entities.alias.Alias;
import uk.co.idv.identity.entities.alias.Aliases;
import uk.co.idv.identity.entities.alias.IdvId;
import uk.co.idv.identity.entities.identity.Identities;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.identity.usecases.identity.IdentityRepository;

import java.time.Instant;
import java.util.Optional;

import static uk.co.idv.common.usecases.duration.DurationCalculator.millisBetweenNowAnd;

@Slf4j
@Builder
public class MongoIdentityRepository implements IdentityRepository {

    private final MongoCollection<Document> collection;
    private final AliasesConverter aliasesConverter;
    private final MongoIdentityConverter identityConverter;

    @Override
    public Optional<Identity> load(Alias alias) {
        Instant start = Instant.now();
        try {
            FindIterable<Document> documents = collection.find(toFindByAliasQuery(alias));
            return Optional.ofNullable(documents.first()).map(this::toIdentity);
        } finally {
            log.info("took {}ms to load identities using alias {}",
                    millisBetweenNowAnd(start),
                    alias);
        }
    }

    @Override
    public Identities load(Aliases aliases) {
        Instant start = Instant.now();
        try {
            Bson query = toFindByAliasesQuery(aliases);
            log.debug("finding identities with query {}", query);
            FindIterable<Document> documents = collection.find(query);
            return toIdentities(documents);
        } finally {
            log.info("took {}ms to load identities using aliases {}",
                    millisBetweenNowAnd(start),
                    aliases);
        }
    }

    @Override
    public void create(Identity identity) {
        collection.insertOne(toDocument(identity));
    }

    @Override
    public void update(Identity updated, Identity existing) {
        collection.replaceOne(toFindByIdvIdQuery(updated.getIdvId()), toDocument(updated));
    }

    @Override
    public void delete(Aliases aliases) {
        Instant start = Instant.now();
        try {
            collection.deleteMany(toFindByAliasesQuery(aliases));
        } finally {
            log.info("took {}ms to delete {} items using aliases {} items",
                    millisBetweenNowAnd(start),
                    aliases.size(),
                    aliases);
        }
    }

    private Identity toIdentity(Document document) {
        return identityConverter.toIdentity(document);
    }

    private Identities toIdentities(FindIterable<Document> documents) {
        return identityConverter.toIdentities(documents);
    }

    private Document toDocument(Identity identity) {
        return identityConverter.toDocument(identity);
    }

    private Bson toFindByAliasesQuery(Aliases aliases) {
        return aliasesConverter.toFindByAliasesQuery(aliases);
    }

    private Bson toFindByAliasQuery(Alias alias) {
        return aliasesConverter.toFindByAliasQuery(alias);
    }

    private Bson toFindByIdvIdQuery(IdvId idvId) {
        return aliasesConverter.toFindByIdvIdQuery(idvId);
    }

}
