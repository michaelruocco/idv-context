package uk.co.idv.context.usecases.context.method;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.method.Method;
import uk.co.idv.context.entities.context.method.Methods;
import uk.co.idv.context.entities.context.method.MethodsRequest;
import uk.co.idv.context.entities.context.method.MethodsRequestMother;
import uk.co.idv.context.entities.policy.method.MethodPolicy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class MethodsBuilderTest {

    private final CompositeMethodBuilder methodBuilder = mock(CompositeMethodBuilder.class);

    private final MethodsBuilder methodsBuilder = new MethodsBuilder(methodBuilder);

    @Test
    void shouldConvertMethodPoliciesToMethods() {
        MethodPolicy policy1 = mock(MethodPolicy.class);
        MethodPolicy policy2 = mock(MethodPolicy.class);
        MethodsRequest request = MethodsRequestMother.withPolicies(policy1, policy2);
        Method expectedMethod1 = givenPolicyConvertsToMethod(request, policy1);
        Method expectedMethod2 = givenPolicyConvertsToMethod(request, policy2);

        Methods methods = methodsBuilder.build(request);

        assertThat(methods).containsExactly(
                expectedMethod1,
                expectedMethod2
        );
    }

    private Method givenPolicyConvertsToMethod(MethodsRequest request, MethodPolicy policy) {
        Method method = mock(Method.class);
        given(methodBuilder.build(request, policy)).willReturn(method);
        return method;
    }

}
