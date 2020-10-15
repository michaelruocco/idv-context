package uk.co.idv.method.usecases;

import lombok.RequiredArgsConstructor;
import uk.co.idv.method.entities.policy.MethodPolicy;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

@RequiredArgsConstructor
public class MethodBuilders {

    private final Collection<MethodBuilder> builders;

    public MethodBuilders(MethodBuilder... builders) {
        this(Arrays.asList(builders));
    }

    public Optional<MethodBuilder> findBuilderSupporting(MethodPolicy policy) {
        return builders.stream()
                .filter(builder -> builder.supports(policy))
                .findFirst();
    }

}
