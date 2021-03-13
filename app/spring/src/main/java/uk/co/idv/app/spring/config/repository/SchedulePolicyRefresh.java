package uk.co.idv.app.spring.config.repository;

import lombok.Builder;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import uk.co.idv.app.spring.config.StartupListenerOrder;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Builder
public class SchedulePolicyRefresh implements ApplicationListener<ContextRefreshedEvent>, Ordered {

    private final ScheduledExecutorService scheduledExecutor;
    private final int policyRefreshDelay;
    private final Runnable contextPolicyRefreshTask;
    private final Runnable lockoutPolicyRefreshTask;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        schedule(contextPolicyRefreshTask);
        schedule(lockoutPolicyRefreshTask);
    }

    @Override
    public int getOrder() {
        return StartupListenerOrder.FIRST;
    }

    private void schedule(Runnable task) {
        scheduledExecutor.scheduleWithFixedDelay(task, 0, policyRefreshDelay, TimeUnit.MILLISECONDS);
    }

}
