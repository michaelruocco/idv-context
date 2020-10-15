package uk.co.idv.context.adapter.json.policy.method;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import uk.co.idv.method.adapter.json.method.MethodMapping;
import uk.co.idv.method.adapter.json.method.MethodMappings;
import uk.co.idv.method.adapter.json.policy.MethodPolicyMixin;
import uk.co.idv.method.entities.policy.MethodPolicies;
import uk.co.idv.method.entities.policy.MethodPolicy;

import java.util.ArrayList;
import java.util.Collection;

public class MethodPolicyModule extends SimpleModule {

    private final transient MethodMappings mappings;

    public MethodPolicyModule(MethodMapping... mappings) {
        this(new MethodMappings(mappings));
    }

    public MethodPolicyModule(MethodMappings mappings) {
        super("method-policy-module", Version.unknownVersion());
        this.mappings = mappings;

        setMixInAnnotation(MethodPolicy.class, MethodPolicyMixin.class);
        setMixInAnnotation(MethodPolicies.class, MethodPoliciesMixin.class);

        addDeserializer(MethodPolicy.class, new MethodPolicyDeserializer(mappings));
        addDeserializer(MethodPolicies.class, new MethodPoliciesDeserializer());
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        Collection<Module> dependencies = new ArrayList<>(mappings.toModules());
        dependencies.add(new JavaTimeModule());
        return dependencies;
    }



}
