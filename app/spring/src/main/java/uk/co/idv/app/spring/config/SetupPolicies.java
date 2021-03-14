package uk.co.idv.app.spring.config;

import lombok.Builder;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import uk.co.idv.app.plain.Application;
import uk.co.idv.app.plain.adapter.channel.ChannelAdapter;

import java.util.Collection;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Builder
public class SetupPolicies implements ApplicationListener<ContextRefreshedEvent> {

    private final Application application;
    private final ChannelAdapter channelAdapter;

    private final Collection<Runnable> policyRefreshTasks;
    private final ScheduledExecutorService scheduledExecutor;
    private final int policyRefreshDelay;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        policyRefreshTasks.forEach(Runnable::run);
        application.populatePolicies(channelAdapter);
        policyRefreshTasks.forEach(this::schedule);
    }

    private void schedule(Runnable task) {
        scheduledExecutor.scheduleWithFixedDelay(task, 0, policyRefreshDelay, TimeUnit.MILLISECONDS);
    }

}
