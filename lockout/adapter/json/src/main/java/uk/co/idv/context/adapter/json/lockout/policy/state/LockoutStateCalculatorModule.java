package uk.co.idv.context.adapter.json.lockout.policy.state;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.context.adapter.json.lockout.policy.state.hard.HardLockoutStateCalculatorDeserializer;
import uk.co.idv.context.adapter.json.lockout.policy.state.nonlocking.NonLockingStateCalculatorDeserializer;
import uk.co.idv.context.adapter.json.lockout.policy.state.soft.DurationSerializer;
import uk.co.idv.context.adapter.json.lockout.policy.state.soft.RecurringSoftLockoutStateCalculatorDeserializer;
import uk.co.idv.context.adapter.json.lockout.policy.state.soft.SoftLockIntervalDeserializer;
import uk.co.idv.context.adapter.json.lockout.policy.state.soft.SoftLockIntervalsDeserializer;
import uk.co.idv.context.adapter.json.lockout.policy.state.soft.SoftLockIntervalsSerializer;
import uk.co.idv.context.adapter.json.lockout.policy.state.soft.SoftLockoutStateCalculatorDeserializer;
import uk.co.idv.context.entities.lockout.policy.LockoutStateCalculator;
import uk.co.idv.context.entities.lockout.policy.hard.HardLockoutStateCalculator;
import uk.co.idv.context.entities.lockout.policy.nonlocking.NonLockingStateCalculator;
import uk.co.idv.context.entities.lockout.policy.soft.RecurringSoftLockoutStateCalculator;
import uk.co.idv.context.entities.lockout.policy.soft.SoftLockInterval;
import uk.co.idv.context.entities.lockout.policy.soft.SoftLockIntervals;
import uk.co.idv.context.entities.lockout.policy.soft.SoftLockoutStateCalculator;

import java.time.Duration;

public class LockoutStateCalculatorModule extends SimpleModule {

    public LockoutStateCalculatorModule() {
        super("lockout-state-calculator-module", Version.unknownVersion());
        setUpDefaults();

        setUpHardLockoutStateCalculator();
        setUpNonLockingStateCalculator();
        setUpSoftLockoutStateCalculator();
        setUpRecurringSoftLockoutStateCalculator();
        setUpSoftLockIntervals();
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
        addSerializer(Duration.class, new DurationSerializer());
        addSerializer(SoftLockIntervals.class, new SoftLockIntervalsSerializer());

        addDeserializer(SoftLockInterval.class, new SoftLockIntervalDeserializer());
        addDeserializer(SoftLockIntervals.class, new SoftLockIntervalsDeserializer());
    }

}
