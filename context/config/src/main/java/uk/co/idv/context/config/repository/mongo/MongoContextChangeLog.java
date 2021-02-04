package uk.co.idv.context.config.repository.mongo;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import org.bson.conversions.Bson;

import static java.util.concurrent.TimeUnit.SECONDS;

@ChangeLog
public class MongoContextChangeLog {

    private static final String COLLECTION_NAME = "context";

    @ChangeSet(order = "001", id = "create-context-collection", author = "system")
    public void createCollection(MongoDatabase database) {
        database.createCollection(COLLECTION_NAME);
        database.getCollection(COLLECTION_NAME).createIndex(indexKeys(), indexOptions());
    }

    private Bson indexKeys() {
        return Indexes.ascending("ttl");
    }

    private IndexOptions indexOptions() {
        return new IndexOptions().expireAfter(60L, SECONDS);
    }

}
