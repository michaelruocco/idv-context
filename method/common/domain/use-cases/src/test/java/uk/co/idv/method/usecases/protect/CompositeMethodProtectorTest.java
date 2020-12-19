package uk.co.idv.method.usecases.protect;

import org.junit.jupiter.api.Test;
import uk.co.idv.method.entities.method.Method;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class CompositeMethodProtectorTest {

    private final MethodProtector methodProtector = mock(MethodProtector.class);

    private final CompositeMethodProtector compositeProtector = new CompositeMethodProtector(methodProtector);

    @Test
    void shouldThrowExceptionIfNoProtectorsSupportingMethodConfigured() {
        String name = "fake-method";
        Method method = mock(Method.class);
        given(method.getName()).willReturn(name);
        given(methodProtector.supports(name)).willReturn(false);

        Throwable error = catchThrowable(() -> compositeProtector.apply(method));

        assertThat(error)
                .isInstanceOf(MethodProtectionNotSupportedException.class)
                .hasMessage(name);
    }

    @Test
    void shouldReturnProtectedMethodReturnFromSupportedMethodProtector() {
        String name = "fake-method";
        Method method = mock(Method.class);
        given(method.getName()).willReturn(name);
        given(methodProtector.supports(name)).willReturn(true);
        Method expectedMethod = mock(Method.class);
        given(methodProtector.apply(method)).willReturn(expectedMethod);

        Method protectedMethod = compositeProtector.apply(method);

        assertThat(protectedMethod).isEqualTo(expectedMethod);
    }

}
