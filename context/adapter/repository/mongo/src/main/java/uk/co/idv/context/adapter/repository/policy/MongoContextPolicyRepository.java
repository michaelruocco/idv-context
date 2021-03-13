package uk.co.idv.context.adapter.repository.policy;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import uk.co.idv.context.entities.policy.ContextPolicy;
import uk.co.idv.context.usecases.policy.ContextPolicyRepository;
import uk.co.idv.policy.adapter.repository.MongoPolicyRepository;
import uk.co.mruoc.json.JsonConverter;

@Slf4j
public class MongoContextPolicyRepository extends MongoPolicyRepository<ContextPolicy> implements ContextPolicyRepository {

    public MongoContextPolicyRepository(MongoDatabase database, JsonConverter jsonConverter) {
        super(ContextPolicyCollection.get(database), new ContextPolicyConverter(jsonConverter));
    }

    public MongoContextPolicyRepository(MongoCollection<Document> collection, ContextPolicyConverter policyConverter) {
        super(collection, policyConverter);
    }

}
