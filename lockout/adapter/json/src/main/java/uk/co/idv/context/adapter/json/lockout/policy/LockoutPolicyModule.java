package uk.co.idv.context.adapter.json.lockout.policy;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.context.adapter.json.lockout.policy.recordattempt.RecordAttemptPolicyModule;
import uk.co.idv.context.adapter.json.lockout.policy.state.LockoutStateCalculatorModule;
import uk.co.idv.context.adapter.json.policy.key.PolicyKeyModule;
import uk.co.idv.context.entities.lockout.policy.LockoutPolicy;

import java.util.Arrays;

public class LockoutPolicyModule extends SimpleModule {

    public LockoutPolicyModule() {
        super("lockout-policy-module", Version.unknownVersion());
        setUpHardLockoutPolicy();
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Arrays.asList(
                new PolicyKeyModule(),
                new RecordAttemptPolicyModule(),
                new LockoutStateCalculatorModule()
        );
    }

    private void setUpHardLockoutPolicy() {
        setMixInAnnotation(LockoutPolicy.class, LockoutPolicyMixin.class);
        addDeserializer(LockoutPolicy.class, new LockoutPolicyDeserializer());
    }

}
