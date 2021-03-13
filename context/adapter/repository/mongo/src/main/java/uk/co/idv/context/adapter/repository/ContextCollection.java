package uk.co.idv.context.adapter.repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import org.bson.Document;
import org.bson.conversions.Bson;

import static java.util.concurrent.TimeUnit.SECONDS;

public class ContextCollection {

    public static final String ID_FIELD_NAME = "_id";
    public static final String TTL_INDEX_NAME = "ttl";

    private static final String NAME = "context";

    private ContextCollection() {
        // utility class
    }

    public static void create(MongoDatabase database) {
        database.createCollection(NAME);
        database.getCollection(NAME).createIndex(indexKeys(), indexOptions());
    }

    public static MongoCollection<Document> get(MongoDatabase database) {
        return database.getCollection(NAME);
    }

    private static Bson indexKeys() {
        return Indexes.ascending(TTL_INDEX_NAME);
    }

    private static IndexOptions indexOptions() {
        return new IndexOptions().expireAfter(60L, SECONDS);
    }

}
