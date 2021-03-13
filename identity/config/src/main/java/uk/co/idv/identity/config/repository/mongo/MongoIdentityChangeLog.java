package uk.co.idv.identity.config.repository.mongo;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.mongodb.client.MongoDatabase;
import uk.co.idv.identity.adapter.repository.MongoIdentityCollection;

@ChangeLog
public class MongoIdentityChangeLog {

    @ChangeSet(order = "001", id = "create-identity-collection", author = "system")
    public void createCollection(MongoDatabase database) {
        MongoIdentityCollection.create(database);
    }

}
