package uk.co.idv.context.entities.context.method.query;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.method.Method;

import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static uk.co.idv.context.entities.context.method.MockMethodMother.mockMethod;

class QueryDecoratorTest {

    private final MethodQuery<Method> parent = mock(MethodQuery.class);

    private final MethodQuery<Method> decorator = new QueryDecorator<>(parent);

    @Test
    void shouldReturnParentQuery() {
        assertThat(decorator.getParent()).contains(parent);
    }

    @Test
    void shouldReturnTypeFromQuery() {
        Class<Method> expectedType = Method.class;
        given(parent.getType()).willReturn(expectedType);

        Class<Method> type = decorator.getType();

        assertThat(type).isEqualTo(expectedType);
    }

    @Test
    void shouldReturnMethodFromParentQuery() {
        Method expectedMethod = mockMethod();
        Stream<Method> stream = Stream.of(expectedMethod);
        given(parent.apply(stream)).willReturn(Optional.of(expectedMethod));

        Optional<Method> method = decorator.apply(stream);

        assertThat(method).contains(expectedMethod);
    }

}
