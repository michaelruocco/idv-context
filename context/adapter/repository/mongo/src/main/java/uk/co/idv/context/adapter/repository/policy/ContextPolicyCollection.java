package uk.co.idv.context.adapter.repository.policy;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class ContextPolicyCollection {

    public static final String ID_FIELD_NAME = "_id";

    private static final String NAME = "context-policy";

    private ContextPolicyCollection() {
        // utility class
    }

    public static void create(MongoDatabase database) {
        database.createCollection(NAME);
    }

    public static MongoCollection<Document> get(MongoDatabase database) {
        return database.getCollection(NAME);
    }

}
