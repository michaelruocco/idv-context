package uk.co.idv.app.manual.adapter.channel;

import uk.co.idv.context.config.ContextPoliciesProvider;
import uk.co.idv.lockout.config.LockoutPoliciesProvider;

public interface ChannelAdapter {

    ContextPoliciesProvider contextPoliciesProvider();

    LockoutPoliciesProvider lockoutPoliciesProvider();

}
