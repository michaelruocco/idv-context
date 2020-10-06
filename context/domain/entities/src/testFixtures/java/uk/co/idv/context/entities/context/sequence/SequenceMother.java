package uk.co.idv.context.entities.context.sequence;

import uk.co.idv.context.entities.context.method.DefaultMethods;
import uk.co.idv.context.entities.context.method.MethodsMother;
import uk.co.idv.method.entities.method.Method;

public interface SequenceMother {

    static Sequence fakeOnly() {
        return builder().build();
    }

    static Sequence withMethods(Method... methods) {
        return withMethods(MethodsMother.withMethods(methods));
    }

    static Sequence withMethods(DefaultMethods methods) {
        return builder().methods(methods).build();
    }

    static Sequence.SequenceBuilder builder() {
        return Sequence.builder()
                .name("default-sequence")
                .methods(MethodsMother.fakeOnly());
    }

}
