package uk.co.idv.lockout.adapter.json.policy.includeattempt;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import uk.co.idv.lockout.entities.policy.includeattempt.IncludeAllAttemptsPolicy;
import uk.co.idv.lockout.entities.policy.includeattempt.IncludeAttemptsPolicy;
import uk.co.idv.lockout.entities.policy.includeattempt.IncludeAttemptsWithinDurationPolicy;

import java.time.Clock;
import java.util.Collections;

public class IncludeAttemptsPolicyModule extends SimpleModule {

    public IncludeAttemptsPolicyModule(Clock clock) {
        super("include-attempts-policy-module", Version.unknownVersion());

        addDeserializer(IncludeAttemptsPolicy.class, new IncludeAttemptsPolicyDeserializer());
        addDeserializer(IncludeAllAttemptsPolicy.class, new IncludeAllAttemptsPolicyDeserializer());
        addDeserializer(IncludeAttemptsWithinDurationPolicy.class, new IncludeAttemptsWithinDurationPolicyDeserializer(clock));
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Collections.singleton(new JavaTimeModule());
    }

}
