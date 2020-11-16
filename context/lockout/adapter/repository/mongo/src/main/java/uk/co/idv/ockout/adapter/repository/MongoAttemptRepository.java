package uk.co.idv.ockout.adapter.repository;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.ReplaceOptions;
import lombok.Builder;
import org.bson.Document;
import org.bson.conversions.Bson;
import uk.co.idv.common.mongo.MongoDurationLogger;
import uk.co.idv.identity.entities.alias.IdvId;
import uk.co.idv.lockout.entities.attempt.Attempts;
import uk.co.idv.lockout.usecases.attempt.AttemptRepository;

import java.time.Instant;
import java.util.Collection;
import java.util.Optional;

@Builder
public class MongoAttemptRepository implements AttemptRepository {

    private final MongoCollection<Document> collection;
    private final AttemptConverter attemptConverter;

    @Override
    public void save(Attempts attempts) {
        Instant start = Instant.now();
        try {
            Bson query = toFindByIdvIdQuery(attempts.getIdvId());
            ReplaceOptions options = new ReplaceOptions().upsert(true);
            Document document = attemptConverter.toDocument(attempts);
            collection.replaceOne(query, document, options);
        } finally {
            MongoDurationLogger.log("save-attempts", start);
        }
    }

    @Override
    public Optional<Attempts> load(IdvId idvId) {
        Instant start = Instant.now();
        try {
            FindIterable<Document> documents = collection.find(toFindByIdvIdQuery(idvId));
            return Optional.ofNullable(documents.first()).map(this::toAttempts);
        } finally {
            MongoDurationLogger.log("load-attempts-by-idv-id", start);
        }
    }

    @Override
    public void delete(Collection<IdvId> idvIds) {
        if (idvIds.isEmpty()) {
            return;
        }
        performDelete(idvIds);
    }

    private void performDelete(Collection<IdvId> idvIds) {
        Instant start = Instant.now();
        try {
            collection.deleteMany(attemptConverter.toFindByIdvIdsQuery(idvIds));
        } finally {
            MongoDurationLogger.log("delete-attempts-by-idv-ids", start);
        }
    }

    private Bson toFindByIdvIdQuery(IdvId idvId) {
        return attemptConverter.toFindByIdvIdQuery(idvId);
    }

    private Attempts toAttempts(Document document) {
        return attemptConverter.toAttempts(document);
    }

}
