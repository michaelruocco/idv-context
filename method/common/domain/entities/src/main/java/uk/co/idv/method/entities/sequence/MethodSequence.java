package uk.co.idv.method.entities.sequence;

import uk.co.idv.method.entities.method.Method;

import java.util.Optional;

public interface MethodSequence {

    boolean isEligible();

    boolean isComplete();

    Optional<Method> getNext();
    Optional<Method> getNext(String name);

}
