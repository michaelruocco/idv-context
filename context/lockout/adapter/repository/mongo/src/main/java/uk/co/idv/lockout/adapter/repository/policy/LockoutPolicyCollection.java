package uk.co.idv.lockout.adapter.repository.policy;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class LockoutPolicyCollection {

    public static final String ID_FIELD_NAME = "_id";

    private static final String NAME = "lockout-policy";

    private LockoutPolicyCollection() {
        // utility class
    }

    public static void create(MongoDatabase database) {
        database.createCollection(NAME);
    }

    public static MongoCollection<Document> get(MongoDatabase database) {
        return database.getCollection(NAME);
    }

}
