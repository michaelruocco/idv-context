package uk.co.idv.context.entities.context.method;

import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.method.fake.FakeMethodMother;

public interface MethodsMother {

    static Methods fakeOnly() {
        return new Methods(FakeMethodMother.build());
    }

    static Methods withMethods(Method... methods) {
        return new Methods(methods);
    }

}
