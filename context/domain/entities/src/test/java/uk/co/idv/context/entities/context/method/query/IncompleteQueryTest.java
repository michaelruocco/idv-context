package uk.co.idv.context.entities.context.method.query;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.method.Method;

import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static uk.co.idv.context.entities.context.method.MockMethodMother.givenCompleteMethod;
import static uk.co.idv.context.entities.context.method.MockMethodMother.givenIncompleteMethod;

class IncompleteQueryTest {

    private final MethodQuery<Method> parent = mock(MethodQuery.class);

    private final MethodQuery<Method> decorator = new IncompleteQuery<>(parent);

    @Test
    void shouldReturnMethodIfIncomplete() {
        Method incomplete = givenIncompleteMethod();
        Stream<Method> stream = Stream.of(incomplete);
        given(parent.apply(stream)).willReturn(Optional.of(incomplete));

        Optional<Method> method = decorator.apply(stream);

        assertThat(method).contains(incomplete);
    }

    @Test
    void shouldNotReturnMethodIfComplete() {
        Method complete = givenCompleteMethod();
        Stream<Method> stream = Stream.of(complete);
        given(parent.apply(stream)).willReturn(Optional.of(complete));

        Optional<Method> method = decorator.apply(stream);

        assertThat(method).isEmpty();
    }

    @Test
    void shouldNotReturnMethodIfQueryReturnsEmpty() {
        Stream<Method> stream = Stream.of();
        given(parent.apply(stream)).willReturn(Optional.empty());

        Optional<Method> method = decorator.apply(stream);

        assertThat(method).isEmpty();
    }

}
