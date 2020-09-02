package uk.co.idv.identity.adapter.json.error;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;

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
