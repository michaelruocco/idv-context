package uk.co.idv.context.usecases.context.method;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.method.MethodsRequest;
import uk.co.idv.context.entities.context.method.MethodsRequestMother;
import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.policy.MethodPolicy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class CompositeMethodBuilderTest {

    @Test
    void shouldReturnMethodFromBuilderIfNameMatches() {
        MethodsRequest request = MethodsRequestMother.build();
        MethodPolicy policy = givenPolicyWithName("my-name");
        MethodBuilder builder1 = givenBuilderNotSupporting(policy);
        MethodBuilder builder2 = givenBuilderSupporting(policy);
        Method expectedMethod = mock(Method.class);
        given(builder2.build(request, policy)).willReturn(expectedMethod);
        CompositeMethodBuilder compositeBuilder = new CompositeMethodBuilder(
                builder1,
                builder2
        );

        Method method = compositeBuilder.build(request, policy);

        assertThat(method).isEqualTo(expectedMethod);
    }

    @Test
    void shouldThrowExceptionIfNoBuildersMatchingNameConfigured() {
        MethodsRequest request = MethodsRequestMother.build();
        MethodPolicy policy = givenPolicyWithName("not-configured-name");
        CompositeMethodBuilder compositeBuilder = new CompositeMethodBuilder();

        Throwable error = catchThrowable(() -> compositeBuilder.build(request, policy));

        assertThat(error)
                .isInstanceOf(MethodBuilderNotConfiguredException.class)
                .hasMessage("not-configured-name");
    }

    private MethodPolicy givenPolicyWithName(String name) {
        MethodPolicy policy = mock(MethodPolicy.class);
        given(policy.getName()).willReturn(name);
        return policy;
    }

    private MethodBuilder givenBuilderNotSupporting(MethodPolicy policy) {
        MethodBuilder builder = mock(MethodBuilder.class);
        given(builder.supports(policy)).willReturn(false);
        return builder;
    }

    private MethodBuilder givenBuilderSupporting(MethodPolicy policy) {
        MethodBuilder builder = mock(MethodBuilder.class);
        given(builder.supports(policy)).willReturn(true);
        return builder;
    }

}
