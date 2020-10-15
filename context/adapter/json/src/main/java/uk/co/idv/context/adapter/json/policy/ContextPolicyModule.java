package uk.co.idv.context.adapter.json.policy;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.context.adapter.json.policy.sequence.SequencePolicyModule;
import uk.co.idv.context.entities.policy.ContextPolicy;
import uk.co.idv.method.adapter.json.method.MethodMapping;
import uk.co.idv.method.adapter.json.method.MethodMappings;
import uk.co.idv.policy.adapter.json.PolicyModule;

import java.util.Arrays;

public class ContextPolicyModule extends SimpleModule {

    private final transient MethodMappings mappings;

    public ContextPolicyModule(MethodMapping... mappings) {
        this(new MethodMappings(mappings));
    }

    public ContextPolicyModule(MethodMappings mappings) {
        super("context-policy-module", Version.unknownVersion());
        this.mappings = mappings;

        setMixInAnnotation(ContextPolicy.class, ContextPolicyMixin.class);

        addDeserializer(ContextPolicy.class, new ContextPolicyDeserializer());
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Arrays.asList(
                new SequencePolicyModule(mappings),
                new PolicyModule()
        );
    }

}
