package uk.co.idv.context.config.repository.mongo;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import org.bson.conversions.Bson;

import static java.util.concurrent.TimeUnit.SECONDS;
import static uk.co.idv.context.adapter.repository.ContextCollection.NAME;
import static uk.co.idv.context.adapter.repository.ContextCollection.TTL_INDEX_NAME;

@ChangeLog
public class MongoContextChangeLog {

    @ChangeSet(order = "001", id = "create-context-collection", author = "system")
    public void createCollection(MongoDatabase database) {
        database.createCollection(NAME);
        database.getCollection(NAME).createIndex(indexKeys(), indexOptions());
    }

    private Bson indexKeys() {
        return Indexes.ascending(TTL_INDEX_NAME);
    }

    private IndexOptions indexOptions() {
        return new IndexOptions().expireAfter(60L, SECONDS);
    }

}
