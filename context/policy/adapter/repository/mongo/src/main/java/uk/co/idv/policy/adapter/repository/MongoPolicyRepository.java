package uk.co.idv.policy.adapter.repository;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.ReplaceOptions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.conversions.Bson;
import uk.co.idv.policy.entities.policy.Policies;
import uk.co.idv.policy.entities.policy.Policy;
import uk.co.idv.policy.usecases.policy.PolicyRepository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import static uk.co.mruoc.duration.logger.MongoMdcDurationLoggerUtils.logDuration;

@RequiredArgsConstructor
@Slf4j
public class MongoPolicyRepository<T extends Policy> implements PolicyRepository<T> {

    private final MongoCollection<Document> collection;
    private final PolicyConverter<T> policyConverter;

    @Override
    public void save(T policy) {
        Instant start = Instant.now();
        try {
            Bson query = toFindByIdQuery(policy.getId());
            ReplaceOptions options = new ReplaceOptions().upsert(true);
            Document document = toDocument(policy);
            collection.replaceOne(query, document, options);
        } finally {
            logDuration("save-policy", start);
        }
    }

    @Override
    public Optional<T> load(UUID id) {
        Instant start = Instant.now();
        try {
            FindIterable<Document> documents = collection.find(toFindByIdQuery(id));
            return Optional.ofNullable(documents.first()).map(this::toPolicy);
        } finally {
            logDuration("load-policy", start);
        }
    }

    @Override
    public Policies<T> loadAll() {
        Instant start = Instant.now();
        try {
            return toPolicies(collection.find());
        } finally {
            logDuration("load-all-policies", start);
        }
    }

    @Override
    public void delete(UUID id) {
        Instant start = Instant.now();
        try {
            collection.deleteOne(toFindByIdQuery(id));
        } finally {
            logDuration("delete-policy", start);
        }
    }

    private Bson toFindByIdQuery(UUID id) {
        return policyConverter.toFindByIdQuery(id);
    }

    private Policies<T> toPolicies(FindIterable<Document> documents) {
        Collection<T> policies = new ArrayList<>();
        try (MongoCursor<Document> cursor = documents.iterator()) {
            while (cursor.hasNext()) {
                policies.add(toPolicy(cursor.next()));
            }
        }
        return new Policies<>(policies);
    }

    private T toPolicy(Document document) {
        return policyConverter.toPolicy(document);
    }

    private Document toDocument(T policy) {
        return policyConverter.toDocument(policy);
    }

}
