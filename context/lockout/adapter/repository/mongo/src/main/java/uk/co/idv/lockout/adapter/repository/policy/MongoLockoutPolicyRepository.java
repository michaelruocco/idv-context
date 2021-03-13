package uk.co.idv.lockout.adapter.repository.policy;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import uk.co.idv.lockout.entities.policy.LockoutPolicy;
import uk.co.idv.lockout.usecases.policy.LockoutPolicyRepository;
import uk.co.idv.policy.adapter.repository.MongoPolicyRepository;
import uk.co.mruoc.json.JsonConverter;

@Slf4j
public class MongoLockoutPolicyRepository extends MongoPolicyRepository<LockoutPolicy> implements LockoutPolicyRepository {

    public MongoLockoutPolicyRepository(MongoDatabase database, JsonConverter jsonConverter) {
        super(LockoutPolicyCollection.get(database), new LockoutPolicyConverter(jsonConverter));
    }

    public MongoLockoutPolicyRepository(MongoCollection<Document> collection, LockoutPolicyConverter policyConverter) {
        super(collection, policyConverter);
    }

}
