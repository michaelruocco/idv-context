package uk.co.idv.lockout.config.repository.mongo;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.mongodb.client.MongoDatabase;
import uk.co.idv.lockout.adapter.repository.MongoAttemptCollection;
import uk.co.idv.lockout.adapter.repository.policy.LockoutPolicyCollection;

@ChangeLog
public class MongoLockoutChangeLog {

    @ChangeSet(order = "001", id = "create-attempt-collection", author = "system")
    public void createAttemptCollection(MongoDatabase database) {
        MongoAttemptCollection.create(database);
    }

    @ChangeSet(order = "001", id = "create-lockout-policy-collection", author = "system")
    public void createLockoutPolicyCollection(MongoDatabase database) {
        LockoutPolicyCollection.create(database);
    }

}
