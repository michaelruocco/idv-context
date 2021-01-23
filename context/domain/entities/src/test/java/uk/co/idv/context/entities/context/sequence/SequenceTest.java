package uk.co.idv.context.entities.context.sequence;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.method.Methods;
import uk.co.idv.context.entities.context.method.MethodsMother;
import uk.co.idv.context.entities.context.method.MockMethodsMother;
import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.method.MethodVerifications;
import uk.co.idv.method.entities.method.MockMethodMother;
import uk.co.idv.method.entities.method.fake.FakeMethodMother;

import java.time.Duration;
import java.util.Optional;
import java.util.function.UnaryOperator;

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
        Methods methods = MethodsMother.oneFake();

        Sequence sequence = Sequence.builder()
                .methods(methods)
                .build();

        assertThat(sequence.getMethods()).isEqualTo(methods);
    }

    @Test
    void shouldReturnEligibleIfAllMethodsEligible() {
        Sequence sequence = Sequence.builder()
                .methods(MockMethodsMother.withEmptyIneligibleMethodNames())
                .build();

        boolean eligible = sequence.isEligible();

        assertThat(eligible).isTrue();
    }

    @Test
    void shouldReturnIneligibleIfAtLeastOnMethodIsIneligible() {
        Sequence sequence = Sequence.builder()
                .methods(MockMethodsMother.withIneligibleMethodNames("fake-method"))
                .build();

        boolean eligible = sequence.isEligible();

        assertThat(eligible).isFalse();
    }

    @Test
    void shouldReturnCompleteIfAllMethodsComplete() {
        MethodVerifications verifications = mock(MethodVerifications.class);
        Method complete1 = MockMethodMother.complete(verifications);
        Method complete2 = MockMethodMother.complete(verifications);

        Sequence sequence = Sequence.builder()
                .methods(MethodsMother.with(complete1, complete2))
                .build();

        assertThat(sequence.isComplete(verifications)).isTrue();
    }

    @Test
    void shouldReturnIncompleteIfAtLeastOnMethodIsNotComplete() {
        MethodVerifications verifications = mock(MethodVerifications.class);
        Method complete = MockMethodMother.complete(verifications);
        Method incomplete = MockMethodMother.incomplete(verifications);

        Sequence sequence = Sequence.builder()
                .methods(MethodsMother.with(complete, incomplete))
                .build();

        assertThat(sequence.isComplete(verifications)).isFalse();
    }

    @Test
    void shouldReturnSuccessfulMethodsAllSuccessful() {
        MethodVerifications verifications = mock(MethodVerifications.class);
        Sequence sequence = Sequence.builder()
                .methods(MockMethodsMother.withAllSuccessful(verifications))
                .build();

        boolean successful = sequence.isSuccessful(verifications);

        assertThat(successful).isTrue();
    }

    @Test
    void shouldReturnTotalDurationFromMethodsAsSequenceDuration() {
        Methods methods = mock(Methods.class);
        Duration expectedDuration = Duration.ofMinutes(5);
        given(methods.getTotalDuration()).willReturn(expectedDuration);

        Sequence sequence = Sequence.builder()
                .methods(methods)
                .build();

        assertThat(sequence.getDuration()).isEqualTo(expectedDuration);
    }

    @Test
    void shouldReturnNextIncompleteMethod() {
        MethodVerifications verifications = mock(MethodVerifications.class);
        Method complete = MockMethodMother.complete(verifications);
        Method incomplete1 = MockMethodMother.incomplete(verifications);
        Method incomplete2 = MockMethodMother.incomplete(verifications);
        Sequence sequence = Sequence.builder()
                .methods(MethodsMother.with(complete, incomplete1, incomplete2))
                .build();

        Optional<Method> next = sequence.getNextMethod(verifications);

        assertThat(next).contains(incomplete1);
    }

    @Test
    void shouldUpdateMethods() {
        UnaryOperator<Method> function = mock(UnaryOperator.class);
        Method method1 = FakeMethodMother.withName("method1");
        Method method2 = FakeMethodMother.withName("method2");
        Method updatedMethod1 = MockMethodMother.withUpdatedMethod(function, method1);
        Method updatedMethod2 = MockMethodMother.withUpdatedMethod(function, method2);
        Sequence sequence = Sequence.builder()
                .methods(MethodsMother.with(method1, method2))
                .build();

        Sequence updated = sequence.updateMethods(function);

        assertThat(updated.getName()).isEqualTo(sequence.getName());
        assertThat(updated).containsExactly(updatedMethod1, updatedMethod2);
    }

    @Test
    void shouldReturnCompletedMethodCount() {
        MethodVerifications verifications = mock(MethodVerifications.class);
        Method method1 = MockMethodMother.complete(verifications);
        Method method2 = MockMethodMother.incomplete(verifications);
        Method method3 = MockMethodMother.complete(verifications);
        Sequence sequence = Sequence.builder()
                .methods(MethodsMother.with(method1, method2, method3))
                .build();

        long completedCount = sequence.completedMethodCount(verifications);

        assertThat(completedCount).isEqualTo(2);
    }

}
