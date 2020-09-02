package uk.co.idv.lockout.adapter.json;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.lockout.adapter.json.policy.LockoutPolicyModule;
import uk.co.idv.lockout.adapter.json.policy.state.LockoutStateModule;
import uk.co.idv.lockout.entities.ExternalLockoutRequest;
import uk.co.idv.lockout.entities.policy.RecordAttemptRequest;

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
