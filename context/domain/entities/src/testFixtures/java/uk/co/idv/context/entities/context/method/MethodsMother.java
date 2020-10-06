package uk.co.idv.context.entities.context.method;

import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.method.fake.FakeMethodMother;

public interface MethodsMother {

    static DefaultMethods fakeOnly() {
        return new DefaultMethods(FakeMethodMother.build());
    }

    static DefaultMethods withMethods(Method... methods) {
        return new DefaultMethods(methods);
    }

}
