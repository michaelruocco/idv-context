package uk.co.idv.app.spring.config.repository.caching;

import org.junit.jupiter.api.Test;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.PeriodicTrigger;
import uk.co.idv.policy.usecases.policy.CachingRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ScheduleCachingPolicyRepositoryRefreshTest {

    private final Runnable refreshTask = mock(CachingRepository.class);
    private final ThreadPoolTaskScheduler scheduler = mock(ThreadPoolTaskScheduler.class);
    private final PeriodicTrigger trigger = mock(PeriodicTrigger.class);

    private final ScheduleCachingPolicyRepositoryRefresh refresh = ScheduleCachingPolicyRepositoryRefresh.builder()
            .refreshTask(refreshTask)
            .scheduler(scheduler)
            .trigger(trigger)
            .build();

    @Test
    void shouldReturnOrderOfTwenty() {
        assertThat(refresh.getOrder()).isEqualTo(20);
    }

    @Test
    void shouldScheduleRefreshTask() {
        ContextRefreshedEvent event = mock(ContextRefreshedEvent.class);

        refresh.onApplicationEvent(event);

        verify(scheduler).schedule(refreshTask, trigger);
    }

}
