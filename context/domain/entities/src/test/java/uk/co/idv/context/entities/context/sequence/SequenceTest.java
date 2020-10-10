package uk.co.idv.context.entities.context.sequence;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.method.Methods;
import uk.co.idv.method.entities.method.Method;
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
        Methods methods = new Methods();

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
                .methods(new Methods(eligible1, eligible2))
                .build();

        assertThat(sequence.isEligible()).isTrue();
    }

    @Test
    void shouldReturnIneligibleIfAtLeastOnMethodIsIneligible() {
        Method eligible = FakeMethodMother.eligible();
        Method ineligible = FakeMethodMother.ineligible();

        Sequence sequence = Sequence.builder()
                .methods(new Methods(eligible, ineligible))
                .build();

        assertThat(sequence.isEligible()).isFalse();
    }

    @Test
    void shouldReturnCompleteIfAllMethodsComplete() {
        Method complete1 = FakeMethodMother.complete();
        Method complete2 = FakeMethodMother.complete();

        Sequence sequence = Sequence.builder()
                .methods(new Methods(complete1, complete2))
                .build();

        assertThat(sequence.isComplete()).isTrue();
    }

    @Test
    void shouldReturnIncompleteIfAtLeastOnMethodIsNotComplete() {
        Method complete = FakeMethodMother.complete();
        Method incomplete = FakeMethodMother.incomplete();

        Sequence sequence = Sequence.builder()
                .methods(new Methods(complete, incomplete))
                .build();

        assertThat(sequence.isComplete()).isFalse();
    }

    @Test
    void shouldReturnSuccessfulIfAllMethodsSuccessful() {
        Method successful1 = FakeMethodMother.successful();
        Method successful2 = FakeMethodMother.successful();

        Sequence sequence = Sequence.builder()
                .methods(new Methods(successful1, successful2))
                .build();

        assertThat(sequence.isSuccessful()).isTrue();
    }

    @Test
    void shouldReturnUnsuccessfulIfAtLeastOnMethodIsNotSuccessful() {
        Method successful = FakeMethodMother.successful();
        Method unsuccessful = FakeMethodMother.unsuccessful();

        Sequence sequence = Sequence.builder()
                .methods(new Methods(successful, unsuccessful))
                .build();

        assertThat(sequence.isSuccessful()).isFalse();
    }

    @Test
    void shouldReturnSumOfMethodDurations() {
        Method method1 = FakeMethodMother.withDuration(Duration.ofMinutes(2));
        Method method2 = FakeMethodMother.withDuration(Duration.ofMinutes(3));

        Sequence sequence = Sequence.builder()
                .methods(new Methods(method1, method2))
                .build();

        assertThat(sequence.getDuration()).isEqualTo(Duration.ofMinutes(5));
    }

    @Test
    void shouldReturnNextIncompleteMethod() {
        Method complete = FakeMethodMother.complete();
        Method incomplete1 = FakeMethodMother.incomplete();
        Method incomplete2 = FakeMethodMother.incomplete();
        Sequence sequence = Sequence.builder()
                .methods(new Methods(complete, incomplete1, incomplete2))
                .build();

        Optional<Method> next = sequence.getNext();

        assertThat(next).contains(incomplete1);
    }

    @Test
    void shouldReturnNextIncompleteMethodIfNameMatches() {
        Method complete = FakeMethodMother.complete();
        Method incomplete = FakeMethodMother.builder()
                .name("method1")
                .overrideComplete(false)
                .build();
        Sequence sequence = Sequence.builder()
                .methods(new Methods(complete, incomplete))
                .build();

        Optional<Method> next = sequence.getNext(incomplete.getName());

        assertThat(next).contains(incomplete);
    }

    @Test
    void shouldNotReturnNextIncompleteMethodIfNameDoesNotMatch() {
        Method complete = FakeMethodMother.complete();
        Method incomplete = FakeMethodMother.builder()
                .name("method1")
                .overrideComplete(false)
                .build();
        Sequence sequence = Sequence.builder()
                .methods(new Methods(complete, incomplete))
                .build();

        Optional<Method> next = sequence.getNext("other-name");

        assertThat(next).isEmpty();
    }

    @Test
    void shouldUpdateMethods() {
        UnaryOperator<Method> function = mock(UnaryOperator.class);
        Method method1 = FakeMethodMother.withName("method1");
        Method method2 = FakeMethodMother.withName("method2");
        Method updatedMethod1 = givenUpdatedMethod(function, method1);
        Method updatedMethod2 = givenUpdatedMethod(function, method2);
        Sequence sequence = Sequence.builder()
                .methods(new Methods(method1, method2))
                .build();

        Sequence updated = sequence.updateMethods(function);

        assertThat(updated.getName()).isEqualTo(sequence.getName());
        assertThat(updated).containsExactly(updatedMethod1, updatedMethod2);
    }

    @Test
    void shouldReturnCompletedCount() {
        Method method1 = FakeMethodMother.complete();
        Method method2 = FakeMethodMother.complete();
        Method method3 = FakeMethodMother.incomplete();
        Sequence sequence = Sequence.builder()
                .methods(new Methods(method1, method2, method3))
                .build();

        long completedCount = sequence.getCompletedCount();

        assertThat(completedCount).isEqualTo(2);
    }

    static Method givenUpdatedMethod(UnaryOperator<Method> function, Method method) {
        Method updated = mock(Method.class);
        given(function.apply(method)).willReturn(updated);
        return updated;
    }

}
