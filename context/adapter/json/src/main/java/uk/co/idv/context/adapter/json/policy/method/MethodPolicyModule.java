package uk.co.idv.context.adapter.json.policy.method;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import uk.co.idv.context.entities.policy.method.MethodPolicies;
import uk.co.idv.method.adapter.json.MethodMapping;
import uk.co.idv.method.entities.policy.MethodPolicy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class MethodPolicyModule extends SimpleModule {

    private final transient Collection<MethodMapping> mappings;

    public MethodPolicyModule(MethodMapping... mappings) {
        this(Arrays.asList(mappings));
    }

    public MethodPolicyModule(Collection<MethodMapping> mappings) {
        super("method-policy-module", Version.unknownVersion());
        this.mappings = mappings;

        setMixInAnnotation(MethodPolicy.class, MethodPolicyMixin.class);
        setMixInAnnotation(MethodPolicies.class, MethodPoliciesMixin.class);

        addDeserializer(MethodPolicy.class, new MethodPolicyDeserializer(mappings));
        addDeserializer(MethodPolicies.class, new MethodPoliciesDeserializer());
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        Collection<Module> dependencies = new ArrayList<>(getMethodPolicyModules());
        dependencies.add(new JavaTimeModule());
        return dependencies;
    }

    public Collection<Module> getMethodPolicyModules() {
        return mappings.stream()
                .map(MethodMapping::getModules)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

}
