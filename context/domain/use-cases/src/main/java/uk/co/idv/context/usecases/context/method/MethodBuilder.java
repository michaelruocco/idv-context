package uk.co.idv.context.usecases.context.method;

import uk.co.idv.context.entities.context.method.Method;
import uk.co.idv.context.entities.context.method.MethodsRequest;
import uk.co.idv.context.entities.policy.method.MethodPolicy;

public interface MethodBuilder {

    boolean supports(MethodPolicy policy);

    Method build(MethodsRequest request, MethodPolicy policy);

}
