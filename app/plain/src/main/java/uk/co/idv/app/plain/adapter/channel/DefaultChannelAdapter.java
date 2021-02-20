package uk.co.idv.app.plain.adapter.channel;

import lombok.RequiredArgsConstructor;
import uk.co.idv.context.config.ContextPoliciesProvider;
import uk.co.idv.context.entities.policy.ContextPolicy;
import uk.co.idv.lockout.config.LockoutPoliciesProvider;
import uk.co.idv.lockout.entities.policy.LockoutPolicy;
import uk.co.idv.policy.entities.policy.Policies;
import uk.co.mruoc.json.JsonConverter;

@RequiredArgsConstructor
public class DefaultChannelAdapter implements ChannelAdapter {

    private final JsonConverter jsonConverter;

    @Override
    public Policies<ContextPolicy> getContextPolicies() {
        return new ContextPoliciesProvider(jsonConverter).getPolicies();
    }

    @Override
    public Policies<LockoutPolicy> getLockoutPolicies() {
        return new LockoutPoliciesProvider(jsonConverter).getPolicies();
    }

}
