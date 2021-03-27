package uk.co.idv.app.spring.config.repository;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.springframework.context.event.ContextRefreshedEvent;
import uk.co.idv.app.plain.Application;
import uk.co.idv.app.plain.adapter.channel.ChannelAdapter;

import java.util.Arrays;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class MongoSetupPoliciesTest {

    private static final int POLICY_REFRESH_DELAY = 3600000;

    private final Application application = mock(Application.class);
    private final ChannelAdapter channelAdapter = mock(ChannelAdapter.class);
    private final Runnable refreshTask1 = mock(Runnable.class);
    private final Runnable refreshTask2 = mock(Runnable.class);
    private final ScheduledExecutorService executor = mock(ScheduledExecutorService.class);

    private final MongoSetupPolicies setupPolicies = MongoSetupPolicies.builder()
            .application(application)
            .channelAdapter(channelAdapter)
            .policyRefreshTasks(Arrays.asList(refreshTask1, refreshTask2))
            .policyRefreshDelay(POLICY_REFRESH_DELAY)
            .scheduledExecutor(executor)
            .build();

    @Test
    void shouldRunPolicyRefreshTasksBeforePopulatingPolicies() {
        ContextRefreshedEvent event = mock(ContextRefreshedEvent.class);

        setupPolicies.onApplicationEvent(event);

        InOrder inOrder = inOrder(refreshTask1, refreshTask2, application);
        inOrder.verify(refreshTask1).run();
        inOrder.verify(refreshTask2).run();
        ArgumentCaptor<ChannelAdapter> captor = ArgumentCaptor.forClass(ChannelAdapter.class);
        inOrder.verify(application).populatePolicies(captor.capture());
        assertThat(captor.getValue()).isEqualTo(channelAdapter);
    }

    @Test
    void shouldPassChannelAdapterToPopulatePolicies() {
        ContextRefreshedEvent event = mock(ContextRefreshedEvent.class);

        setupPolicies.onApplicationEvent(event);

        ArgumentCaptor<ChannelAdapter> captor = ArgumentCaptor.forClass(ChannelAdapter.class);
        verify(application).populatePolicies(captor.capture());
        assertThat(captor.getValue()).isEqualTo(channelAdapter);
    }

    @Test
    void shouldSchedulePolicyRefreshTasksAfterPopulatingPolicies() {
        ContextRefreshedEvent event = mock(ContextRefreshedEvent.class);

        setupPolicies.onApplicationEvent(event);

        InOrder inOrder = inOrder(application, executor);
        ArgumentCaptor<ChannelAdapter> captor = ArgumentCaptor.forClass(ChannelAdapter.class);
        inOrder.verify(application).populatePolicies(captor.capture());
        inOrder.verify(executor).scheduleWithFixedDelay(refreshTask1, 0, POLICY_REFRESH_DELAY, MILLISECONDS);
        inOrder.verify(executor).scheduleWithFixedDelay(refreshTask2, 0, POLICY_REFRESH_DELAY, MILLISECONDS);
        assertThat(captor.getValue()).isEqualTo(channelAdapter);
    }

}
