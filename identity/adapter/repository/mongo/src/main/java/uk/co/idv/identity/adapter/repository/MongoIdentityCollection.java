package uk.co.idv.identity.adapter.repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import org.bson.conversions.Bson;
import uk.co.idv.identity.adapter.repository.document.IdentityDocument;

public class MongoIdentityCollection {

    public static final String ALIASES_VALUE_INDEX_NAME = "aliases.value";
    public static final String ALIASES_TYPE_INDEX_NAME = "aliases.type";

    private static final String NAME = "identity";

    private MongoIdentityCollection() {
        // constants class
    }

    public static void create(MongoDatabase database) {
        database.createCollection(NAME);
        database.getCollection(NAME).createIndex(indexKeys(), indexOptions());
    }

    public static MongoCollection<IdentityDocument> get(MongoDatabase database) {
        return database.getCollection(NAME, IdentityDocument.class);
    }

    private static Bson indexKeys() {
        return Indexes.ascending(
                ALIASES_VALUE_INDEX_NAME,
                ALIASES_TYPE_INDEX_NAME
        );
    }

    private static IndexOptions indexOptions() {
        return new IndexOptions().unique(true);
    }

}
