package uk.co.idv.context.adapter.json.lockout.policy.state;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.context.adapter.json.lockout.policy.state.hard.HardLockoutStateCalculatorDeserializer;
import uk.co.idv.context.adapter.json.lockout.policy.state.nonlocking.NonLockingStateCalculatorDeserializer;
import uk.co.idv.context.entities.lockout.policy.LockoutStateCalculator;
import uk.co.idv.context.entities.lockout.policy.hard.HardLockoutStateCalculator;
import uk.co.idv.context.entities.lockout.policy.nonlocking.NonLockingStateCalculator;

public class LockoutStateCalculatorModule extends SimpleModule {

    public LockoutStateCalculatorModule() {
        super("lockout-state-calculator-module", Version.unknownVersion());
        setUpDefaults();

        setUpHardLockoutStateCalculator();
        setUpNonLockingStateCalculator();
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

}
