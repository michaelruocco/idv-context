package uk.co.idv.method.usecases;

import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.method.fake.FakeMethod;
import uk.co.idv.method.usecases.protect.MethodProtectionNotSupportedException;
import uk.co.idv.method.usecases.protect.MethodProtector;

public class FakeMethodMasker implements MethodProtector {

    @Override
    public boolean supports(String name) {
        return true;
    }

    @Override
    public Method apply(Method method) {
        if (method instanceof FakeMethod) {
            var fakeMethod = (FakeMethod) method;
            return fakeMethod.withName(fakeMethod.getName() + "-masked");
        }
        throw new MethodProtectionNotSupportedException(method.getName());
    }

}
