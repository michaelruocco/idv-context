package uk.co.idv.method.usecases;

import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.method.MethodsRequest;
import uk.co.idv.method.entities.method.fake.FakeMethodMother;
import uk.co.idv.method.entities.method.fake.policy.FakeMethodPolicy;
import uk.co.idv.method.entities.policy.MethodPolicy;


public class FakeMethodBuilder implements MethodBuilder {

    @Override
    public boolean supports(MethodPolicy policy) {
        return policy instanceof FakeMethodPolicy;
    }

    @Override
    public Method build(MethodsRequest request, MethodPolicy policy) {
        FakeMethodPolicy fakePolicy = (FakeMethodPolicy) policy;
        return FakeMethodMother.withConfig(fakePolicy.getConfig());
    }

}
