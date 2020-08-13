package uk.co.idv.context.adapter.json.lockout.policy;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.context.adapter.json.error.PolicyErrorModule;
import uk.co.idv.context.adapter.json.lockout.policy.recordattempt.RecordAttemptPolicyModule;
import uk.co.idv.context.adapter.json.lockout.policy.state.LockoutStateCalculatorModule;
import uk.co.idv.context.adapter.json.policy.PolicyModule;
import uk.co.idv.context.entities.lockout.policy.LockoutPolicy;

import java.util.Arrays;

public class LockoutPolicyModule extends SimpleModule {

    public LockoutPolicyModule() {
        super("lockout-policy-module", Version.unknownVersion());
        setUpLockoutPolicy();
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Arrays.asList(
                new PolicyModule(),
                new PolicyErrorModule(),
                new RecordAttemptPolicyModule(),
                new LockoutStateCalculatorModule()
        );
    }

    private void setUpLockoutPolicy() {
        setMixInAnnotation(LockoutPolicy.class, LockoutPolicyMixin.class);
        addDeserializer(LockoutPolicy.class, new LockoutPolicyDeserializer());
    }

}
