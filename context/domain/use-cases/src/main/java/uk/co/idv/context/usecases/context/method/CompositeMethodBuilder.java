package uk.co.idv.context.usecases.context.method;

import uk.co.idv.context.entities.context.method.Method;
import uk.co.idv.context.entities.policy.method.MethodPolicy;
import uk.co.idv.identity.entities.identity.Identity;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CompositeMethodBuilder {

    private final Map<String, MethodBuilder> builders = new HashMap<>();

    public CompositeMethodBuilder(MethodBuilder... builders) {
        this(Arrays.asList(builders));
    }

    public CompositeMethodBuilder(Collection<MethodBuilder> builders) {
        builders.forEach(this::add);
    }

    public Method build(Identity identity, MethodPolicy policy) {
        return findBuilder(policy.getName())
                .map(builder -> builder.build(identity, policy))
                .orElseThrow(() -> new MethodBuilderNotConfiguredException(policy.getName()));
    }

    public Optional<MethodBuilder> findBuilder(String name) {
        return Optional.ofNullable(builders.get(name));
    }

    private void add(MethodBuilder builder) {
        builders.put(builder.getName(), builder);
    }

}
