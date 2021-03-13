package uk.co.idv.app.spring.config.repository.caching;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.PeriodicTrigger;
import uk.co.idv.policy.usecases.policy.CachingRepository;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

//@Configuration
@Slf4j
@Profile("!stubbed")
public class CachingPolicyRepositoryConfig {

    @Bean
    public ThreadPoolTaskScheduler cachingPolicyRepositoryRefreshScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(4);
        scheduler.setThreadNamePrefix("mongoCacheRefreshScheduler");
        return scheduler;
    }

    @Bean
    public PeriodicTrigger cachingPolicyRepositoryRefreshTrigger() {
        PeriodicTrigger trigger = new PeriodicTrigger(60000, TimeUnit.MILLISECONDS);
        trigger.setFixedRate(true);
        trigger.setInitialDelay(1000);
        return trigger;
    }

    @Bean
    public ScheduleCachingPolicyRepositoryRefresh scheduleCachingPolicyRepositoryRefresh(ThreadPoolTaskScheduler scheduler,
                                                                                         PeriodicTrigger trigger,
                                                                                         Collection<CachingRepository> cachingRepositories) {
        return ScheduleCachingPolicyRepositoryRefresh.builder()
                .scheduler(scheduler)
                .trigger(trigger)
                .refreshTask(new CachingPolicyRepositoryRefreshTask(cachingRepositories))
                .build();
    }

}
