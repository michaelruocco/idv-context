package uk.co.idv.lockout.adapter.repository.policy;

import uk.co.idv.lockout.entities.policy.LockoutPolicy;
import uk.co.idv.policy.adapter.repository.PolicyConverter;
import uk.co.mruoc.json.JsonConverter;

import static uk.co.idv.lockout.adapter.repository.policy.LockoutPolicyCollection.ID_FIELD_NAME;


public class LockoutPolicyConverter extends PolicyConverter<LockoutPolicy> {

    public LockoutPolicyConverter(JsonConverter converter) {
        super(ID_FIELD_NAME, LockoutPolicy.class, converter);
    }

}
