package uk.co.idv.lockout.adapter.repository;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.ReplaceOptions;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.bson.conversions.Bson;
import uk.co.idv.identity.entities.alias.IdvId;
import uk.co.idv.lockout.entities.attempt.Attempts;
import uk.co.idv.lockout.usecases.attempt.AttemptRepository;
import uk.co.mruoc.json.JsonConverter;

import java.time.Instant;
import java.util.Collection;
import java.util.Optional;

import static uk.co.mruoc.duration.logger.MongoMdcDurationLoggerUtils.logDuration;

@RequiredArgsConstructor
public class MongoAttemptRepository implements AttemptRepository {

    private final MongoCollection<Document> collection;
    private final MongoAttemptConverter attemptConverter;

    public MongoAttemptRepository(MongoDatabase database, JsonConverter jsonConverter) {
        this(MongoAttemptCollection.get(database), new MongoAttemptConverter(jsonConverter));
    }

    @Override
    public void save(Attempts attempts) {
        Instant start = Instant.now();
        try {
            Bson query = toFindByIdvIdQuery(attempts.getIdvId());
            ReplaceOptions options = new ReplaceOptions().upsert(true);
            Document document = attemptConverter.toDocument(attempts);
            collection.replaceOne(query, document, options);
        } finally {
            logDuration("save-attempts", start);
        }
    }

    @Override
    public Optional<Attempts> load(IdvId idvId) {
        Instant start = Instant.now();
        try {
            Bson query = toFindByIdvIdQuery(idvId);
            FindIterable<Document> documents = collection.find(query);
            return Optional.ofNullable(documents.first()).map(this::toAttempts);
        } finally {
            logDuration("load-attempts-by-idv-id", start);
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
            logDuration("delete-attempts-by-idv-ids", start);
        }
    }

    private Bson toFindByIdvIdQuery(IdvId idvId) {
        return attemptConverter.toFindByIdvIdQuery(idvId);
    }

    private Attempts toAttempts(Document document) {
        return attemptConverter.toAttempts(document);
    }

}
