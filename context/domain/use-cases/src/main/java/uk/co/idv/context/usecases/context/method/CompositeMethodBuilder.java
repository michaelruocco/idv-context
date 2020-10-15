package uk.co.idv.context.usecases.context.method;

import lombok.RequiredArgsConstructor;
import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.method.MethodsRequest;
import uk.co.idv.method.entities.policy.MethodPolicy;
import uk.co.idv.method.usecases.MethodBuilder;
import uk.co.idv.method.usecases.MethodBuilders;

import java.util.Optional;

@RequiredArgsConstructor
public class CompositeMethodBuilder {

    private final MethodBuilders builders;

    public CompositeMethodBuilder(MethodBuilder... builders) {
        this(new MethodBuilders(builders));
    }

    public Method build(MethodsRequest request, MethodPolicy policy) {
        return findBuilder(policy)
                .map(builder -> builder.build(request, policy))
                .orElseThrow(() -> new MethodBuilderNotConfiguredException(policy.getName()));
    }

    public Optional<MethodBuilder> findBuilder(MethodPolicy policy) {
        return builders.findBuilderSupporting(policy);
    }

}
