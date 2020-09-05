package uk.co.idv.context.usecases.context.method;

import uk.co.idv.context.entities.context.method.Method;
import uk.co.idv.context.entities.policy.method.MethodPolicy;
import uk.co.idv.identity.entities.identity.Identity;

public interface MethodBuilder {

    String getName();

    Method build(Identity identity, MethodPolicy policy);

}
