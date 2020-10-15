package uk.co.idv.app.manual.adapter.channel;

import lombok.RequiredArgsConstructor;
import uk.co.idv.context.config.ContextPoliciesProvider;
import uk.co.idv.lockout.config.LockoutPoliciesProvider;
import uk.co.mruoc.json.JsonConverter;

@RequiredArgsConstructor
public class DefaultChannelAdapter implements ChannelAdapter {

    private final JsonConverter jsonConverter;

    @Override
    public ContextPoliciesProvider contextPoliciesProvider() {
        return new ContextPoliciesProvider(jsonConverter);
    }

    @Override
    public LockoutPoliciesProvider lockoutPoliciesProvider() {
        return new LockoutPoliciesProvider(jsonConverter);
    }

}
