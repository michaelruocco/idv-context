package uk.co.idv.context.usecases.context.method;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.method.Method;
import uk.co.idv.context.entities.policy.method.MethodPolicy;
import uk.co.idv.identity.entities.identity.Identity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class CompositeMethodBuilderTest {

    @Test
    void shouldReturnMethodFromBuilderIfNameMatches() {
        Identity identity = mock(Identity.class);
        MethodPolicy policy = givenPolicyWithName("my-name");
        MethodBuilder builder1 = givenBuilderWithName("other-name");
        MethodBuilder builder2 = givenBuilderWithName("my-name");
        Method expectedMethod = mock(Method.class);
        given(builder2.build(identity, policy)).willReturn(expectedMethod);
        CompositeMethodBuilder compositeBuilder = new CompositeMethodBuilder(
                builder1,
                builder2
        );

        Method method = compositeBuilder.build(identity, policy);

        assertThat(method).isEqualTo(expectedMethod);
    }

    @Test
    void shouldThrowExceptionIfNoBuildersMatchingNameConfigured() {
        Identity identity = mock(Identity.class);
        MethodPolicy policy = givenPolicyWithName("not-configured-name");
        CompositeMethodBuilder compositeBuilder = new CompositeMethodBuilder();

        Throwable error = catchThrowable(() -> compositeBuilder.build(identity, policy));

        assertThat(error)
                .isInstanceOf(MethodBuilderNotConfiguredException.class)
                .hasMessage("not-configured-name");
    }

    private MethodBuilder givenBuilderWithName(String name) {
        MethodBuilder builder = mock(MethodBuilder.class);
        given(builder.getName()).willReturn(name);
        return builder;
    }

    private MethodPolicy givenPolicyWithName(String name) {
        MethodPolicy policy = mock(MethodPolicy.class);
        given(policy.getName()).willReturn(name);
        return policy;
    }

}
