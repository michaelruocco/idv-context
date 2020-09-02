package uk.co.idv.lockout.entities.policy.soft;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class SoftLockIntervalsTest {

    private final SoftLockInterval twoAttempts = SoftLockIntervalMother.build(2);
    private final SoftLockInterval fourAttempts = SoftLockIntervalMother.build(4);

    private final SoftLockIntervals intervals = new SoftLockIntervals(twoAttempts, fourAttempts);

    @Test
    void shouldReturnDurationFromIntervalWithHighestNumberOfAttemptsIfNumberOfAttemptsGreaterThanHighest() {
        int numberOfAttempts = 5;

        Optional<SoftLockInterval> interval = intervals.findInterval(numberOfAttempts);

        assertThat(interval).contains(fourAttempts);
    }

    @Test
    void shouldReturnEmptyIntervalIfNumberOfAttemptsDoesNotMatchIntervalAndIsNotGreaterThanHighest() {
        int numberOfAttempts = 3;

        Optional<SoftLockInterval> interval = intervals.findInterval(numberOfAttempts);

        assertThat(interval).isEmpty();
    }

    @Test
    void shouldReturnIntervalMatchingNumberOfAttempts() {
        int numberOfAttempts = 2;

        Optional<SoftLockInterval> interval = intervals.findInterval(numberOfAttempts);

        assertThat(interval).contains(twoAttempts);
    }

    @Test
    void shouldReturnAllIntervalValues() {
        Collection<SoftLockInterval> values = intervals.values();

        assertThat(values).containsExactly(twoAttempts, fourAttempts);
    }

}
