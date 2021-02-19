package uk.co.idv.context.adapter.repository;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.ReplaceOptions;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.conversions.Bson;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.usecases.context.ContextRepository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static uk.co.mruoc.duration.logger.MongoMdcDurationLoggerUtils.logDuration;

@Builder
@Slf4j
//TODO unit tests
public class MongoContextRepository implements ContextRepository {

    private final MongoCollection<Document> collection;
    private final ContextConverter contextConverter;

    @Override
    public void save(Context context) {
        Instant start = Instant.now();
        try {
            Bson query = toFindByIdQuery(context.getId());
            ReplaceOptions options = new ReplaceOptions().upsert(true);
            Document document = toDocument(context);
            collection.replaceOne(query, document, options);
        } finally {
            logDuration("save-context", start);
        }
    }

    @Override
    public Optional<Context> load(UUID id) {
        Instant start = Instant.now();
        try {
            FindIterable<Document> documents = collection.find(toFindByIdQuery(id));
            return Optional.ofNullable(documents.first()).map(this::toContext);
        } finally {
            logDuration("load-context", start);
        }
    }

    private Bson toFindByIdQuery(UUID id) {
        return contextConverter.toFindByIdQuery(id);
    }

    private Context toContext(Document document) {
        return contextConverter.toContext(document);
    }

    private Document toDocument(Context context) {
        return contextConverter.toDocument(context);
    }

}
