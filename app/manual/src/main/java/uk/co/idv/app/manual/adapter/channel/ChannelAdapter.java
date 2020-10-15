package uk.co.idv.app.manual.adapter.channel;

import uk.co.idv.context.entities.policy.ContextPolicy;
import uk.co.idv.lockout.entities.policy.LockoutPolicy;
import uk.co.idv.policy.entities.policy.Policies;

public interface ChannelAdapter {

    Policies<ContextPolicy> getContextPolicies();

    Policies<LockoutPolicy> getLockoutPolicies();

}
