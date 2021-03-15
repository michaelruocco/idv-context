package uk.co.idv.lockout.adapter.repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoAttemptCollection {

    public static final String ID_FIELD_NAME = "_id";

    private static final String NAME = "attempt";

    private MongoAttemptCollection() {
        // utility class
    }

    public static void create(MongoDatabase database) {
        database.createCollection(NAME);
    }

    public static MongoCollection<Document> get(MongoDatabase database) {
        return database.getCollection(NAME);
    }

}
