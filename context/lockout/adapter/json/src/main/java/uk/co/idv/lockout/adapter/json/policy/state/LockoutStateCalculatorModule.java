package uk.co.idv.lockout.adapter.json.policy.state;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.lockout.adapter.json.policy.includeattempt.IncludeAttemptsPolicyModule;
import uk.co.idv.lockout.adapter.json.policy.state.hard.HardLockoutStateCalculatorDeserializer;
import uk.co.idv.lockout.adapter.json.policy.state.nonlocking.NonLockingStateCalculatorDeserializer;
import uk.co.idv.lockout.adapter.json.policy.state.soft.RecurringSoftLockoutStateCalculatorDeserializer;
import uk.co.idv.lockout.adapter.json.policy.state.soft.SoftLockIntervalDeserializer;
import uk.co.idv.lockout.adapter.json.policy.state.soft.SoftLockIntervalsDeserializer;
import uk.co.idv.lockout.adapter.json.policy.state.soft.SoftLockIntervalsSerializer;
import uk.co.idv.lockout.adapter.json.policy.state.soft.SoftLockoutStateCalculatorDeserializer;
import uk.co.idv.lockout.entities.policy.LockoutStateCalculator;
import uk.co.idv.lockout.entities.policy.hard.HardLockoutStateCalculator;
import uk.co.idv.lockout.entities.policy.nonlocking.NonLockingStateCalculator;
import uk.co.idv.lockout.entities.policy.soft.RecurringSoftLockoutStateCalculator;
import uk.co.idv.lockout.entities.policy.soft.SoftLockInterval;
import uk.co.idv.lockout.entities.policy.soft.SoftLockIntervals;
import uk.co.idv.lockout.entities.policy.soft.SoftLockoutStateCalculator;

import java.time.Clock;
import java.util.Collections;

public class LockoutStateCalculatorModule extends SimpleModule {

    private final transient Clock clock;

    public LockoutStateCalculatorModule(Clock clock) {
        super("lockout-state-calculator-module", Version.unknownVersion());
        this.clock = clock;

        setUpDefaults();

        setUpHardLockoutStateCalculator();
        setUpNonLockingStateCalculator();
        setUpSoftLockoutStateCalculator();
        setUpRecurringSoftLockoutStateCalculator();
        setUpSoftLockIntervals();
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Collections.singleton(new IncludeAttemptsPolicyModule(clock));
    }

    private void setUpDefaults() {
        addDeserializer(LockoutStateCalculator.class, new LockoutStateCalculatorDeserializer());
    }

    private void setUpHardLockoutStateCalculator() {
        addDeserializer(HardLockoutStateCalculator.class, new HardLockoutStateCalculatorDeserializer());
    }

    private void setUpNonLockingStateCalculator() {
        addDeserializer(NonLockingStateCalculator.class, new NonLockingStateCalculatorDeserializer());
    }

    private void setUpSoftLockoutStateCalculator() {
        addDeserializer(SoftLockoutStateCalculator.class, new SoftLockoutStateCalculatorDeserializer());
    }

    private void setUpRecurringSoftLockoutStateCalculator() {
        addDeserializer(RecurringSoftLockoutStateCalculator.class, new RecurringSoftLockoutStateCalculatorDeserializer());
    }

    private void setUpSoftLockIntervals() {
        addSerializer(SoftLockIntervals.class, new SoftLockIntervalsSerializer());

        addDeserializer(SoftLockInterval.class, new SoftLockIntervalDeserializer());
        addDeserializer(SoftLockIntervals.class, new SoftLockIntervalsDeserializer());
    }

}
