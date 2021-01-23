package uk.co.idv.context.entities.context.sequence;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.method.Methods;
import uk.co.idv.context.entities.context.method.MethodsMother;
import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.method.MethodVerifications;
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
        Method eligible1 = FakeMethodMother.eligible();
        Method eligible2 = FakeMethodMother.eligible();

        Sequence sequence = Sequence.builder()
                .methods(MethodsMother.with(eligible1, eligible2))
                .build();

        assertThat(sequence.isEligible()).isTrue();
    }

    @Test
    void shouldReturnIneligibleIfAtLeastOnMethodIsIneligible() {
        Method eligible = FakeMethodMother.eligible();
        Method ineligible = FakeMethodMother.ineligible();

        Sequence sequence = Sequence.builder()
                .methods(MethodsMother.with(eligible, ineligible))
                .build();

        assertThat(sequence.isEligible()).isFalse();
    }

    @Test
    void shouldReturnCompleteIfAllMethodsComplete() {
        MethodVerifications verifications = mock(MethodVerifications.class);
        Method complete1 = givenCompleteMethod(verifications);
        Method complete2 = givenCompleteMethod(verifications);

        Sequence sequence = Sequence.builder()
                .methods(MethodsMother.with(complete1, complete2))
                .build();

        assertThat(sequence.isComplete(verifications)).isTrue();
    }

    @Test
    void shouldReturnIncompleteIfAtLeastOnMethodIsNotComplete() {
        MethodVerifications verifications = mock(MethodVerifications.class);
        Method complete = givenCompleteMethod(verifications);
        Method incomplete = givenIncompleteMethod(verifications);

        Sequence sequence = Sequence.builder()
                .methods(MethodsMother.with(complete, incomplete))
                .build();

        assertThat(sequence.isComplete(verifications)).isFalse();
    }

    @Test
    void shouldReturnSuccessfulIfAllMethodsSuccessful() {
        MethodVerifications verifications = mock(MethodVerifications.class);
        Method successful1 = givenSuccessfulMethod(verifications);
        Method successful2 = givenSuccessfulMethod(verifications);

        Sequence sequence = Sequence.builder()
                .methods(MethodsMother.with(successful1, successful2))
                .build();

        assertThat(sequence.isSuccessful(verifications)).isTrue();
    }

    @Test
    void shouldReturnUnsuccessfulIfAtLeastOnMethodIsNotSuccessful() {
        MethodVerifications verifications = mock(MethodVerifications.class);
        Method successful = givenSuccessfulMethod(verifications);
        Method unsuccessful = givenUnsuccessfulMethod(verifications);

        Sequence sequence = Sequence.builder()
                .methods(MethodsMother.with(successful, unsuccessful))
                .build();

        assertThat(sequence.isSuccessful(verifications)).isFalse();
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
        Method complete = givenCompleteMethod(verifications);
        Method incomplete1 = givenIncompleteMethod(verifications);
        Method incomplete2 = givenIncompleteMethod(verifications);
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
        Method updatedMethod1 = givenUpdatedMethod(function, method1);
        Method updatedMethod2 = givenUpdatedMethod(function, method2);
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
        Method method1 = givenCompleteMethod(verifications);
        Method method2 = givenIncompleteMethod(verifications);
        Method method3 = givenCompleteMethod(verifications);
        Sequence sequence = Sequence.builder()
                .methods(MethodsMother.with(method1, method2, method3))
                .build();

        long completedCount = sequence.completedMethodCount(verifications);

        assertThat(completedCount).isEqualTo(2);
    }

    private static Method givenSuccessfulMethod(MethodVerifications verifications) {
        Method method = mock(Method.class);
        given(method.isSuccessful(verifications)).willReturn(true);
        return method;
    }

    private static Method givenUnsuccessfulMethod(MethodVerifications verifications) {
        Method method = mock(Method.class);
        given(method.isSuccessful(verifications)).willReturn(false);
        return method;
    }

    private static Method givenCompleteMethod(MethodVerifications verifications) {
        Method method = mock(Method.class);
        given(method.isComplete(verifications)).willReturn(true);
        return method;
    }

    private static Method givenIncompleteMethod(MethodVerifications verifications) {
        Method method = mock(Method.class);
        given(method.isComplete(verifications)).willReturn(false);
        return method;
    }

    static Method givenUpdatedMethod(UnaryOperator<Method> function, Method method) {
        Method updated = mock(Method.class);
        given(function.apply(method)).willReturn(updated);
        return updated;
    }

}
