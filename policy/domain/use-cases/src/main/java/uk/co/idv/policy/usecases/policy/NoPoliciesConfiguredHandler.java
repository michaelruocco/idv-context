package uk.co.idv.policy.usecases.policy;

import uk.co.idv.policy.entities.policy.PolicyRequest;

public interface NoPoliciesConfiguredHandler<T extends NoPoliciesConfiguredException> {

    T toException(PolicyRequest request);

}
