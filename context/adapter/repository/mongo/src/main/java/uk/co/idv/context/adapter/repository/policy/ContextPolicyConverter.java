package uk.co.idv.context.adapter.repository.policy;

import uk.co.idv.context.entities.policy.ContextPolicy;
import uk.co.idv.policy.adapter.repository.PolicyConverter;
import uk.co.mruoc.json.JsonConverter;

import static uk.co.idv.context.adapter.repository.policy.ContextPolicyCollection.ID_FIELD_NAME;


public class ContextPolicyConverter extends PolicyConverter<ContextPolicy> {

    public ContextPolicyConverter(JsonConverter converter) {
        super(ID_FIELD_NAME, ContextPolicy.class, converter);
    }

}
