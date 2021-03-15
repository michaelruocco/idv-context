package uk.co.idv.lockout.adapter.repository.policy;

import uk.co.idv.lockout.entities.policy.LockoutPolicy;
import uk.co.idv.policy.adapter.repository.MongoPolicyConverter;
import uk.co.mruoc.json.JsonConverter;

import static uk.co.idv.lockout.adapter.repository.policy.LockoutPolicyCollection.ID_FIELD_NAME;


public class MongoLockoutPolicyConverter extends MongoPolicyConverter<LockoutPolicy> {

    public MongoLockoutPolicyConverter(JsonConverter converter) {
        super(ID_FIELD_NAME, LockoutPolicy.class, converter);
    }

}
