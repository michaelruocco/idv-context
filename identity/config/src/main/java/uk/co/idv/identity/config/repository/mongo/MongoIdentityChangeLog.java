package uk.co.idv.identity.config.repository.mongo;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import org.bson.conversions.Bson;

import static uk.co.idv.identity.adapter.repository.MongoIdentityCollection.NAME;
import static uk.co.idv.identity.adapter.repository.MongoIdentityCollection.ALIASES_TYPE_INDEX_NAME;
import static uk.co.idv.identity.adapter.repository.MongoIdentityCollection.ALIASES_VALUE_INDEX_NAME;


@ChangeLog
public class MongoIdentityChangeLog {

    @ChangeSet(order = "001", id = "create-identity-collection", author = "system")
    public void createCollection(MongoDatabase database) {
        database.createCollection(NAME);
        database.getCollection(NAME).createIndex(indexKeys(), indexOptions());
    }

    private Bson indexKeys() {
        return Indexes.ascending(
                ALIASES_VALUE_INDEX_NAME,
                ALIASES_TYPE_INDEX_NAME
        );
    }

    private IndexOptions indexOptions() {
        return new IndexOptions().unique(true);
    }

}
