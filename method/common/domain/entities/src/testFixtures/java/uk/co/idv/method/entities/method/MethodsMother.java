package uk.co.idv.method.entities.method;

import uk.co.idv.method.entities.method.fake.FakeMethodMother;

public interface MethodsMother {

    static Methods empty() {
        return new Methods();
    }

    static Methods oneFake() {
        return with(FakeMethodMother.build());
    }

    static Methods with(Method... methods) {
        return new Methods(methods);
    }

}
