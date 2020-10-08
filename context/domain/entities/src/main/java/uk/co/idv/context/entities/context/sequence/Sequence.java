package uk.co.idv.context.entities.context.sequence;

import lombok.Builder;
import lombok.Data;
import lombok.With;
import uk.co.idv.context.entities.context.method.Methods;
import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.sequence.MethodSequence;

import java.time.Duration;
import java.util.Optional;
import java.util.function.UnaryOperator;

@Builder
@Data
public class Sequence implements MethodSequence {

    private final String name;

    @With
    private final Methods methods;

    @Override
    public boolean isEligible() {
        return methods.isEligible();
    }

    @Override
    public boolean isComplete() {
        return methods.isComplete();
    }

    @Override
    public Optional<Method> getNext() {
        return methods.getNext();
    }

    @Override
    public Optional<Method> getNext(String name) {
        return methods.getNext(name);
    }

    public boolean isSuccessful() {
        return methods.isSuccessful();
    }

    public Duration getDuration() {
        return methods.getDuration();
    }

    public Sequence updateMethods(UnaryOperator<Method> function) {
        return withMethods(methods.update(function));
    }

}
