package uk.co.idv.lockout.adapter.json;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.lockout.adapter.json.policy.LockoutPolicyModule;
import uk.co.idv.lockout.adapter.json.policy.state.LockoutStateModule;
import uk.co.idv.lockout.entities.ExternalLockoutRequest;

import java.time.Clock;
import java.util.Arrays;

public class LockoutParentModule extends SimpleModule {

    private final transient Clock clock;

    public LockoutParentModule(Clock clock) {
        super("lockout-parent-module", Version.unknownVersion());
        this.clock = clock;
        addDeserializer(ExternalLockoutRequest.class, new ExternalLockoutRequestDeserializer());
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Arrays.asList(
                new LockoutPolicyModule(clock),
                new LockoutStateModule()
        );
    }

}
