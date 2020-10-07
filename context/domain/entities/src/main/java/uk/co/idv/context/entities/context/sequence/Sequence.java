package uk.co.idv.context.entities.context.sequence;

import lombok.Builder;
import lombok.Data;
import lombok.With;
import uk.co.idv.context.entities.context.method.Methods;
import uk.co.idv.method.entities.method.Method;

import java.time.Duration;
import java.util.Optional;
import java.util.function.UnaryOperator;

@Builder
@Data
public class Sequence {

    private final String name;

    @With
    private final Methods methods;

    public boolean isEligible() {
        return methods.isEligible();
    }

    public boolean isComplete() {
        return methods.isComplete();
    }

    public boolean isSuccessful() {
        return methods.isSuccessful();
    }

    public Duration getDuration() {
        return methods.getDuration();
    }

    public Optional<Method> getMethodIfNext(String name) {
        return methods.getNext(name);
    }

    public Sequence apply(UnaryOperator<Method> function) {
        return withMethods(methods.apply(function));
    }

}
