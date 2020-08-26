package uk.co.idv.context.usecases.policy;

import uk.co.idv.context.entities.policy.PolicyRequest;

public interface NoPoliciesConfiguredHandler<T extends NoPoliciesConfiguredException> {

    T toException(PolicyRequest request);

}
