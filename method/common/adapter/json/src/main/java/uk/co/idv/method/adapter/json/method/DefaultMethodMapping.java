package uk.co.idv.method.adapter.json.method;

import com.fasterxml.jackson.databind.Module;
import lombok.Builder;
import lombok.Data;
import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.policy.MethodPolicy;

import java.util.Collection;

@Builder
@Data
public class DefaultMethodMapping implements MethodMapping {

    private final String name;
    private final Class<? extends Method> methodType;
    private final Class<? extends MethodPolicy> policyType;
    private final Collection<Module> modules;

}
