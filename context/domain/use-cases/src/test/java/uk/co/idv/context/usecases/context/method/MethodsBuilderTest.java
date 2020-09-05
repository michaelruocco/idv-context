package uk.co.idv.context.usecases.context.method;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.method.Method;
import uk.co.idv.context.entities.context.method.Methods;
import uk.co.idv.context.entities.policy.method.MethodPolicies;
import uk.co.idv.context.entities.policy.method.MethodPolicy;
import uk.co.idv.identity.entities.identity.Identity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class MethodsBuilderTest {

    private final CompositeMethodBuilder methodBuilder = mock(CompositeMethodBuilder.class);

    private final MethodsBuilder methodsBuilder = new MethodsBuilder(methodBuilder);

    @Test
    void shouldConvertMethodPoliciesToMethods() {
        Identity identity = mock(Identity.class);
        MethodPolicy policy1 = mock(MethodPolicy.class);
        MethodPolicy policy2 = mock(MethodPolicy.class);
        Method expectedMethod1 = givenPolicyConvertsToMethod(identity, policy1);
        Method expectedMethod2 = givenPolicyConvertsToMethod(identity, policy2);

        Methods methods = methodsBuilder.build(identity, new MethodPolicies(policy1, policy2));

        assertThat(methods).containsExactly(
                expectedMethod1,
                expectedMethod2
        );
    }

    private Method givenPolicyConvertsToMethod(Identity identity, MethodPolicy policy) {
        Method method = mock(Method.class);
        given(methodBuilder.build(identity, policy)).willReturn(method);
        return method;
    }

}
