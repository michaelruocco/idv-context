package uk.co.idv.lockout.config.repository.mongo;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.mongodb.client.MongoDatabase;
import uk.co.idv.lockout.adapter.repository.MongoAttemptCollection;

@ChangeLog
public class MongoAttemptChangeLog {

    @ChangeSet(order = "001", id = "create-attempt-collection", author = "system")
    public void createCollection(MongoDatabase database) {
        database.createCollection(MongoAttemptCollection.NAME);
    }

}
