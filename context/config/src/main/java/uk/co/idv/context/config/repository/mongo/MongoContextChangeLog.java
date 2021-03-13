package uk.co.idv.context.config.repository.mongo;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.mongodb.client.MongoDatabase;
import uk.co.idv.context.adapter.repository.ContextCollection;
import uk.co.idv.context.adapter.repository.policy.ContextPolicyCollection;

@ChangeLog
public class MongoContextChangeLog {

    @ChangeSet(order = "001", id = "create-context-collection", author = "system")
    public void createContextCollection(MongoDatabase database) {
        ContextCollection.create(database);
    }

    @ChangeSet(order = "002", id = "create-context-policy-collection", author = "system")
    public void createContextPolicyCollection(MongoDatabase database) {
        ContextPolicyCollection.create(database);
    }

}
