package uk.co.idv.context.adapter.json.lockout;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.context.adapter.json.lockout.policy.LockoutPolicyModule;
import uk.co.idv.context.adapter.json.lockout.policy.state.LockoutStateModule;
import uk.co.idv.context.entities.lockout.ExternalLockoutRequest;
import uk.co.idv.context.entities.lockout.policy.RecordAttemptRequest;

import java.util.Arrays;

public class LockoutParentModule extends SimpleModule {

    public LockoutParentModule() {
        super("lockout-parent-module", Version.unknownVersion());

        addDeserializer(ExternalLockoutRequest.class, new ExternalLockoutRequestDeserializer());
        addDeserializer(RecordAttemptRequest.class, new RecordAttemptRequestDeserializer());
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Arrays.asList(
                new LockoutPolicyModule(),
                new LockoutStateModule()
        );
    }

}
