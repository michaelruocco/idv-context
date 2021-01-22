package uk.co.idv.lockout.adapter.json.policy;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.policy.adapter.json.error.PolicyErrorModule;
import uk.co.idv.lockout.adapter.json.policy.recordattempt.RecordAttemptPolicyModule;
import uk.co.idv.lockout.adapter.json.policy.state.LockoutStateCalculatorModule;
import uk.co.idv.policy.adapter.json.PolicyModule;
import uk.co.idv.lockout.entities.policy.LockoutPolicy;

import java.time.Clock;
import java.util.Arrays;

public class LockoutPolicyModule extends SimpleModule {

    private final transient Clock clock;

    public LockoutPolicyModule(Clock clock) {
        super("lockout-policy-module", Version.unknownVersion());
        this.clock = clock;
        setUpLockoutPolicy();
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Arrays.asList(
                new PolicyModule(),
                new PolicyErrorModule(),
                new RecordAttemptPolicyModule(),
                new LockoutStateCalculatorModule(clock)
        );
    }

    private void setUpLockoutPolicy() {
        setMixInAnnotation(LockoutPolicy.class, LockoutPolicyMixin.class);
        addDeserializer(LockoutPolicy.class, new LockoutPolicyDeserializer());
    }

}
