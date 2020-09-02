package uk.co.idv.context.entities.policy;

import lombok.RequiredArgsConstructor;
import uk.co.idv.context.entities.policy.method.MethodPolicies;
import uk.co.idv.identity.entities.identity.RequestedData;

@RequiredArgsConstructor
public class ContextPolicy {

    private final MethodPolicies methodPolicies;

    public RequestedData getRequestedData() {
        return methodPolicies.getRequestedData();
    }

}
