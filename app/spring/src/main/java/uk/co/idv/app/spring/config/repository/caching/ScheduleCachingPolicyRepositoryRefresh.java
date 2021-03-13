package uk.co.idv.app.spring.config.repository.caching;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.PeriodicTrigger;

@Builder
@Slf4j
public class ScheduleCachingPolicyRepositoryRefresh implements ApplicationListener<ContextRefreshedEvent>, Ordered {

    private final Runnable refreshTask;
    private final ThreadPoolTaskScheduler scheduler;
    private final PeriodicTrigger trigger;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("creating scheduling refresh task {} with trigger {}", refreshTask, trigger);
        scheduler.schedule(refreshTask, trigger);
    }

    @Override
    public int getOrder() {
        return 20;
    }

}
