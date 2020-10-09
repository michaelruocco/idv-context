package uk.co.idv.context.entities.context;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.sequence.MethodSequence;

import java.util.function.Predicate;

@Data
@RequiredArgsConstructor
public class HasEligibleMethod implements Predicate<MethodSequence> {

    private final String methodName;

    @Override
    public boolean test(MethodSequence sequence) {
        if (sequence.isEligible()) {
            return sequence.getNext(methodName)
                    .map(Method::isEligible)
                    .orElse(false);
        }
        return false;
    }

}
