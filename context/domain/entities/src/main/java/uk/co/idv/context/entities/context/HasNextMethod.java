package uk.co.idv.context.entities.context;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import uk.co.idv.method.entities.sequence.MethodSequence;

import java.util.function.Predicate;

@Data
@RequiredArgsConstructor
public class HasNextMethod implements Predicate<MethodSequence> {

    private final String methodName;
    private boolean hasNext;

    @Override
    public boolean test(MethodSequence sequence) {
        return sequence.getNext(methodName).isPresent();
    }

}
