package uk.co.idv.policy.usecases.policy;

import uk.co.idv.policy.entities.policy.Policies;
import uk.co.idv.policy.entities.policy.Policy;

public interface PoliciesProvider<P extends Policy> {

    Policies<P> getPolicies();

}
