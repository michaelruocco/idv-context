package uk.co.idv.method.usecases;

import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.method.MethodsRequest;
import uk.co.idv.method.entities.policy.MethodPolicy;

public interface MethodBuilder {

    boolean supports(MethodPolicy policy);

    Method build(MethodsRequest request, MethodPolicy policy);

}
