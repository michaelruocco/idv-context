package uk.co.idv.context.usecases.context.method;

import lombok.RequiredArgsConstructor;
import uk.co.idv.context.entities.context.method.MethodsRequest;
import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.policy.MethodPolicy;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

@RequiredArgsConstructor
public class CompositeMethodBuilder {

    private final Collection<MethodBuilder> builders;

    public CompositeMethodBuilder(MethodBuilder... builders) {
        this(Arrays.asList(builders));
    }

    public Method build(MethodsRequest request, MethodPolicy policy) {
        return findBuilder(policy)
                .map(builder -> builder.build(request, policy))
                .orElseThrow(() -> new MethodBuilderNotConfiguredException(policy.getName()));
    }

    public Optional<MethodBuilder> findBuilder(MethodPolicy policy) {
        return builders.stream()
                .filter(builder -> builder.supports(policy))
                .findFirst();
    }

}
