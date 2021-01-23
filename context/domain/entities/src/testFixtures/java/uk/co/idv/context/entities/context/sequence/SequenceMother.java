package uk.co.idv.context.entities.context.sequence;

import uk.co.idv.context.entities.context.method.Methods;
import uk.co.idv.context.entities.context.sequence.nextmethods.InOrderNextMethodsPolicy;
import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.method.fake.FakeMethodMother;

public interface SequenceMother {

    static Sequence fakeOnly() {
        return builder().build();
    }

    static Sequence withMethods(Method... methods) {
        return withMethods(new Methods(methods));
    }

    static Sequence withMethods(Methods methods) {
        return builder().methods(methods).build();
    }

    static Sequence.SequenceBuilder builder() {
        return Sequence.builder()
                .name("default-sequence")
                .nextMethodsPolicy(new InOrderNextMethodsPolicy())
                .methods(new Methods(FakeMethodMother.build()));
    }

}
