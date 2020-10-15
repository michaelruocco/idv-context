package uk.co.idv.app.spring.config;

import lombok.Builder;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import uk.co.idv.app.manual.adapter.channel.ChannelAdapter;
import uk.co.idv.context.usecases.policy.ContextPoliciesPopulator;
import uk.co.idv.lockout.usecases.policy.LockoutPoliciesPopulator;

@Builder
public class StartupListener implements ApplicationListener<ContextRefreshedEvent> {

    private final LockoutPoliciesPopulator lockoutPoliciesPopulator;
    private final ContextPoliciesPopulator contextPoliciesPopulator;
    private final ChannelAdapter channelAdapter;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        lockoutPoliciesPopulator.populate(channelAdapter.getLockoutPolicies());
        contextPoliciesPopulator.populate(channelAdapter.getContextPolicies());
    }

}
