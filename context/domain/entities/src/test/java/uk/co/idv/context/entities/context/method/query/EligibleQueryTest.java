package uk.co.idv.context.entities.context.method.query;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.method.Method;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static uk.co.idv.context.entities.context.method.MockMethodMother.givenEligibleMethod;
import static uk.co.idv.context.entities.context.method.MockMethodMother.givenIneligibleMethod;

class EligibleQueryTest {

    private final MethodQuery<Method> parent = mock(MethodQuery.class);

    private final MethodQuery<Method> decorator = new EligibleQuery<>(parent);

    @Test
    void shouldReturnMethodIfEligible() {
        Method eligible = givenEligibleMethod();
        Stream<Method> stream = Stream.of(eligible);
        given(parent.apply(stream)).willReturn(Stream.of(eligible));

        Stream<Method> method = decorator.apply(stream);

        assertThat(method).contains(eligible);
    }

    @Test
    void shouldNotReturnMethodIfIneligible() {
        Method ineligible = givenIneligibleMethod();
        Stream<Method> stream = Stream.of(ineligible);
        given(parent.apply(stream)).willReturn(Stream.of(ineligible));

        Stream<Method> method = decorator.apply(stream);

        assertThat(method).isEmpty();
    }

    @Test
    void shouldNotReturnMethodIfQueryReturnsEmpty() {
        Stream<Method> stream = Stream.of();
        given(parent.apply(stream)).willReturn(Stream.empty());

        Stream<Method> method = decorator.apply(stream);

        assertThat(method).isEmpty();
    }

}
