package uk.co.idv.method.adapter.json.fake;

import com.fasterxml.jackson.databind.Module;
import uk.co.idv.method.adapter.json.method.MethodMapping;
import uk.co.idv.method.adapter.json.fake.policy.FakeMethodPolicyModule;
import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.method.fake.FakeMethod;
import uk.co.idv.method.entities.method.fake.policy.FakeMethodPolicy;
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
