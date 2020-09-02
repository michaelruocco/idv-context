package uk.co.idv.context.adapter.json.lockout.policy.state;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.identity.adapter.json.alias.AliasModule;
import uk.co.idv.context.adapter.json.lockout.attempt.AttemptModule;
import uk.co.idv.context.adapter.json.lockout.policy.state.soft.DurationSerializer;
import uk.co.idv.context.entities.lockout.policy.LockoutState;

import java.time.Duration;
import java.util.Arrays;

public class LockoutStateModule extends SimpleModule {

    public LockoutStateModule() {
        super("lockout-state-module", Version.unknownVersion());

        setMixInAnnotation(LockoutState.class, LockoutStateMixin.class);

        addSerializer(Duration.class, new DurationSerializer());
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Arrays.asList(
                new AttemptModule(),
                new AliasModule()
        );
    }

}
