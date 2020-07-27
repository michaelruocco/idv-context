package uk.co.idv.context.entities.lockout.policy.soft;

import lombok.ToString;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;

@ToString
public class SoftLockIntervals {

    private final SortedMap<Integer, SoftLockInterval> intervals = new TreeMap<>();

    public SoftLockIntervals(SoftLockInterval... intervals) {
        this(Arrays.asList(intervals));
    }

    public SoftLockIntervals(Collection<SoftLockInterval> intervals) {
        intervals.forEach(this::add);
    }

    public Optional<SoftLockInterval> findInterval(int numberOfAttempts) {
        int lastIntervalAttempts = intervals.lastKey();
        if (numberOfAttempts > lastIntervalAttempts) {
            return Optional.of(intervals.get(lastIntervalAttempts));
        }
        return Optional.ofNullable(intervals.get(numberOfAttempts));
    }

    private void add(SoftLockInterval interval) {
        intervals.put(interval.getNumberOfAttempts(), interval);
    }

}
