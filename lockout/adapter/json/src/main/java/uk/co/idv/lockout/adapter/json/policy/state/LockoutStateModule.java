package uk.co.idv.lockout.adapter.json.policy.state;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.identity.adapter.json.alias.AliasModule;
import uk.co.idv.lockout.adapter.json.attempt.AttemptModule;
import uk.co.idv.lockout.entities.policy.LockoutState;

import java.util.Arrays;

public class LockoutStateModule extends SimpleModule {

    public LockoutStateModule() {
        super("lockout-state-module", Version.unknownVersion());

        setMixInAnnotation(LockoutState.class, LockoutStateMixin.class);
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Arrays.asList(
                new AttemptModule(),
                new AliasModule()
        );
    }

}
