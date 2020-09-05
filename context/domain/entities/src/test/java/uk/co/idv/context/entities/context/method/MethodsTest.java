package uk.co.idv.context.entities.context.method;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class MethodsTest {

    @Test
    void shouldReturnMethods() {
        Method method1 = mock(Method.class);
        Method method2 = mock(Method.class);

        Methods methods = new Methods(method1, method2);

        assertThat(methods).containsExactly(
                method1,
                method2
        );
    }

}
