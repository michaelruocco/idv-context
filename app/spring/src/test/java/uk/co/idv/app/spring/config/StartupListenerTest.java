package uk.co.idv.app.spring.config;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.context.event.ContextRefreshedEvent;
import uk.co.idv.app.manual.adapter.channel.ChannelAdapter;
import uk.co.idv.context.entities.policy.ContextPolicy;
import uk.co.idv.context.usecases.policy.ContextPoliciesPopulator;
import uk.co.idv.lockout.entities.policy.LockoutPolicy;
import uk.co.idv.lockout.usecases.policy.LockoutPoliciesPopulator;
import uk.co.idv.policy.entities.policy.Policies;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class StartupListenerTest {

    private final LockoutPoliciesPopulator lockoutPoliciesPopulator = mock(LockoutPoliciesPopulator.class);
    private final ContextPoliciesPopulator contextPoliciesPopulator = mock(ContextPoliciesPopulator.class);
    private final ChannelAdapter channelAdapter = mock(ChannelAdapter.class);

    private final StartupListener listener = StartupListener.builder()
            .lockoutPoliciesPopulator(lockoutPoliciesPopulator)
            .contextPoliciesPopulator(contextPoliciesPopulator)
            .channelAdapter(channelAdapter)
            .build();

    @Test
    void shouldPopulateLockoutPolicies() {
        ContextRefreshedEvent event = mock(ContextRefreshedEvent.class);
        Policies<LockoutPolicy> expectedPolicies = givenExpectedLockoutPolicies();

        listener.onApplicationEvent(event);

        ArgumentCaptor<Policies<LockoutPolicy>> captor = ArgumentCaptor.forClass(Policies.class);
        verify(lockoutPoliciesPopulator).populate(captor.capture());
        assertThat(captor.getValue()).isEqualTo(expectedPolicies);
    }

    @Test
    void shouldPopulateContextPolicies() {
        ContextRefreshedEvent event = mock(ContextRefreshedEvent.class);
        Policies<ContextPolicy> expectedPolicies = givenExpectedContextPolicies();

        listener.onApplicationEvent(event);

        ArgumentCaptor<Policies<ContextPolicy>> captor = ArgumentCaptor.forClass(Policies.class);
        verify(contextPoliciesPopulator).populate(captor.capture());
        assertThat(captor.getValue()).isEqualTo(expectedPolicies);
    }

    private Policies<LockoutPolicy> givenExpectedLockoutPolicies() {
        Policies<LockoutPolicy> policies = new Policies<>();
        given(channelAdapter.getLockoutPolicies()).willReturn(policies);
        return policies;
    }

    private Policies<ContextPolicy> givenExpectedContextPolicies() {
        Policies<ContextPolicy> policies = new Policies<>();
        given(channelAdapter.getContextPolicies()).willReturn(policies);
        return policies;
    }

}
