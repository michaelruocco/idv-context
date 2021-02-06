package uk.co.idv.policy.usecases.policy;

import uk.co.idv.policy.entities.policy.NoPoliciesConfiguredException;
import uk.co.idv.policy.entities.policy.PolicyRequest;

public interface NoPoliciesConfiguredHandler {

    NoPoliciesConfiguredException toException(PolicyRequest request);

}
