package uk.co.idv.lockout.adapter.json.error;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import uk.co.idv.identity.adapter.json.error.ErrorModule;
import uk.co.idv.lockout.adapter.json.policy.state.LockoutStateModule;

import java.util.Arrays;

public class LockoutErrorModule extends SimpleModule {

    public LockoutErrorModule() {
        super("lockout-error-module", Version.unknownVersion());
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Arrays.asList(
                new Jdk8Module(),
                new ErrorModule(),
                new LockoutStateModule()
        );
    }

}
