package uk.co.idv.context.entities.context.sequence;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.method.DefaultMethods;
import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.method.fake.FakeMethodMother;

import java.time.Duration;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class SequenceTest {

    @Test
    void shouldReturnName() {
        String name = "my-name";

        Sequence sequence = Sequence.builder()
                .name(name)
                .build();

        assertThat(sequence.getName()).isEqualTo(name);
    }

    @Test
    void shouldReturnMethods() {
        DefaultMethods methods = mock(DefaultMethods.class);

        Sequence sequence = Sequence.builder()
                .methods(methods)
                .build();

        assertThat(sequence.getMethods()).isEqualTo(methods);
    }

    @Test
    void shouldReturnEligibleFromMethods() {
        DefaultMethods methods = mock(DefaultMethods.class);
        given(methods.isEligible()).willReturn(true);

        Sequence sequence = Sequence.builder()
                .methods(methods)
                .build();

        assertThat(sequence.isEligible()).isTrue();
    }

    @Test
    void shouldReturnCompleteFromMethods() {
        DefaultMethods methods = mock(DefaultMethods.class);
        given(methods.isComplete()).willReturn(true);

        Sequence sequence = Sequence.builder()
                .methods(methods)
                .build();

        assertThat(sequence.isComplete()).isTrue();
    }

    @Test
    void shouldReturnSuccessfulFromMethods() {
        DefaultMethods methods = mock(DefaultMethods.class);
        given(methods.isSuccessful()).willReturn(true);

        Sequence sequence = Sequence.builder()
                .methods(methods)
                .build();

        assertThat(sequence.isSuccessful()).isTrue();
    }

    @Test
    void shouldReturnDurationFromMethods() {
        DefaultMethods methods = mock(DefaultMethods.class);
        Duration duration = Duration.ofMinutes(5);
        given(methods.getDuration()).willReturn(duration);

        Sequence sequence = Sequence.builder()
                .methods(methods)
                .build();

        assertThat(sequence.getDuration()).isEqualTo(duration);
    }

    @Test
    void shouldReturnNextMethod() {
        DefaultMethods methods = mock(DefaultMethods.class);
        Method method = FakeMethodMother.build();
        given(methods.getNext(method.getName())).willReturn(Optional.of(method));
        Sequence sequence = SequenceMother.withMethods(methods);

        Optional<Method> next = sequence.getMethodIfNext(method.getName());

        assertThat(next).contains(method);
    }

    @Test
    void shouldReturnWithUpdatedMethods() {
        DefaultMethods methods = mock(DefaultMethods.class);
        DefaultMethods updatedMethods = mock(DefaultMethods.class);
        Sequence sequence = SequenceMother.withMethods(methods);

        Sequence updatedSequence = sequence.withMethods(updatedMethods);

        assertThat(updatedSequence)
                .usingRecursiveComparison()
                .ignoringFields("methods")
                .isEqualTo(sequence);
        assertThat(updatedSequence.getMethods()).isEqualTo(updatedMethods);
    }

}
