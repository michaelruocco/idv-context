package uk.co.idv.context.adapter.json.context.method;

import com.fasterxml.jackson.databind.Module;
import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.policy.MethodPolicy;

import java.util.Collection;

public interface MethodMapping {

    String getName();

    Class<? extends Method> getMethodType();

    Class<? extends MethodPolicy> getPolicyType();

    Collection<Module> getModules();

}
