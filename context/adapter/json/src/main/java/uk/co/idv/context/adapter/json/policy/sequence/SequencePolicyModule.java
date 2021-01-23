package uk.co.idv.context.adapter.json.policy.sequence;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.context.adapter.json.policy.method.MethodPolicyModule;
import uk.co.idv.context.adapter.json.policy.sequence.nextmethods.NextMethodsPolicyModule;
import uk.co.idv.context.entities.policy.sequence.SequencePolicies;
import uk.co.idv.context.entities.policy.sequence.SequencePolicy;
import uk.co.idv.method.adapter.json.method.MethodMapping;
import uk.co.idv.method.adapter.json.method.MethodMappings;

import java.util.Arrays;

public class SequencePolicyModule extends SimpleModule {

    private final transient MethodMappings mappings;

    public SequencePolicyModule(MethodMapping... mappings) {
        this(new MethodMappings(mappings));
    }

    public SequencePolicyModule(MethodMappings mappings) {
        super("sequence-policy-module", Version.unknownVersion());
        this.mappings = mappings;

        setMixInAnnotation(SequencePolicy.class, SequencePolicyMixin.class);
        setMixInAnnotation(SequencePolicies.class, SequencePoliciesMixin.class);

        addDeserializer(SequencePolicy.class, new SequencePolicyDeserializer());
        addDeserializer(SequencePolicies.class, new SequencePoliciesDeserializer());
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Arrays.asList(
                new MethodPolicyModule(mappings),
                new NextMethodsPolicyModule()
        );
    }

}
