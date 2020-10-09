package uk.co.idv.context.entities.context;

import org.junit.jupiter.api.Test;
import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.method.fake.FakeMethodMother;
import uk.co.idv.method.entities.sequence.MethodSequence;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class HasNextMethodTest {

    private static final String METHOD_NAME = "method-name";

    private final HasNextMethod hasNextMethod = new HasNextMethod(METHOD_NAME);

    @Test
    void shouldReturnMethodName() {
        assertThat(hasNextMethod.getMethodName()).isEqualTo(METHOD_NAME);
    }

    @Test
    void shouldReturnFalseIfNotNextMethod() {
        MethodSequence sequence = mock(MethodSequence.class);
        given(sequence.getNext(METHOD_NAME)).willReturn(Optional.empty());

        boolean next = hasNextMethod.test(sequence);

        assertThat(next).isFalse();
    }

    @Test
    void shouldReturnTrueIfNextMethod() {
        MethodSequence sequence = mock(MethodSequence.class);
        Method method = FakeMethodMother.build();
        given(sequence.getNext(METHOD_NAME)).willReturn(Optional.of(method));

        boolean next = hasNextMethod.test(sequence);

        assertThat(next).isTrue();
    }

}
