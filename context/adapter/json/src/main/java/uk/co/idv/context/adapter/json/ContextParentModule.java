package uk.co.idv.context.adapter.json;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.context.adapter.json.context.ContextModule;
import uk.co.idv.context.adapter.json.policy.ContextPolicyModule;
import uk.co.idv.identity.adapter.json.IdentityParentModule;
import uk.co.idv.method.adapter.json.method.MethodMappings;

import java.util.Arrays;

public class ContextParentModule extends SimpleModule {

    private final transient MethodMappings mappings;

    public ContextParentModule(MethodMappings mappings) {
        super("context-parent-module", Version.unknownVersion());
        this.mappings = mappings;
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Arrays.asList(
                new ContextModule(mappings),
                new ContextPolicyModule(mappings),
                new IdentityParentModule()
        );
    }

}
