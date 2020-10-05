package uk.co.idv.context.adapter.json.context.method.fake;

import com.fasterxml.jackson.databind.Module;
import uk.co.idv.context.adapter.json.context.method.MethodMapping;
import uk.co.idv.context.adapter.json.policy.method.fake.FakeMethodPolicyModule;
import uk.co.idv.method.entities.method.FakeMethod;
import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.policy.FakeMethodPolicy;
import uk.co.idv.method.entities.policy.MethodPolicy;

import java.util.Arrays;
import java.util.Collection;

public class FakeMethodMapping implements MethodMapping {

    @Override
    public String getName() {
        return "fake-method";
    }

    @Override
    public Class<? extends Method> getMethodType() {
        return FakeMethod.class;
    }

    @Override
    public Class<? extends MethodPolicy> getPolicyType() {
        return FakeMethodPolicy.class;
    }

    public Collection<Module> getModules() {
        return Arrays.asList(new FakeMethodModule(), new FakeMethodPolicyModule());
    }

}
