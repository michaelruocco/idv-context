package uk.co.idv.context.usecases.policy;

import uk.co.idv.context.entities.context.create.CreateContextRequest;
import uk.co.idv.context.entities.policy.ContextPolicy;

public class ContextPolicyService {

    public ContextPolicy load(CreateContextRequest request) {
        return new ContextPolicy();
    }

}
