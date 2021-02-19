package uk.co.idv.method.entities.method;

import uk.co.idv.method.entities.method.fake.FakeMethodMother;

public interface MethodsMother {

    static DefaultMethods empty() {
        return new DefaultMethods();
    }

    static Methods oneFake() {
        return with(FakeMethodMother.build());
    }

    static DefaultMethods with(Method... methods) {
        return new DefaultMethods(methods);
    }

}
