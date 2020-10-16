package uk.co.idv.policy.adapter.json.error;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.common.adapter.json.error.ErrorModule;

import java.util.Collections;

public class PolicyErrorModule extends SimpleModule {

    public PolicyErrorModule() {
        super("policy-error-module", Version.unknownVersion());
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Collections.singleton(new ErrorModule());
    }

}
