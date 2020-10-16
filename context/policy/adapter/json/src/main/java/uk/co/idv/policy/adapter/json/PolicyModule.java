package uk.co.idv.policy.adapter.json;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.policy.adapter.json.key.PolicyKeyModule;
import uk.co.idv.policy.entities.policy.Policies;

import java.util.Collections;

public class PolicyModule extends SimpleModule {

    public PolicyModule() {
        super("policy-module", Version.unknownVersion());
        setMixInAnnotation(Policies.class, PoliciesMixin.class);
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Collections.singleton(new PolicyKeyModule());
    }

}
