package uk.co.idv.context.entities.context.method;

import org.junit.jupiter.api.Test;
import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.method.fake.FakeMethodMother;

import java.time.Duration;
import java.util.Optional;
import java.util.function.UnaryOperator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class MethodsTest {

    @Test
    void shouldBeIterable() {
        Method method1 = FakeMethodMother.build();
        Method method2 = FakeMethodMother.build();

        Methods methods = new Methods(method1, method2);

        assertThat(methods).containsExactly(
                method1,
                method2
        );
    }

    @Test
    void shouldReturnValues() {
        Method method1 = FakeMethodMother.build();
        Method method2 = FakeMethodMother.build();

        Methods methods = new Methods(method1, method2);

        assertThat(methods.getValues()).containsExactly(
                method1,
                method2
        );
    }

    @Test
    void shouldReturnTrueIfEmpty() {
        Methods methods = new Methods();

        boolean empty = methods.isEmpty();

        assertThat(empty).isTrue();
    }

    @Test
    void shouldReturnFalseIfNotEmpty() {
        Methods methods = new Methods(FakeMethodMother.build());

        boolean empty = methods.isEmpty();

        assertThat(empty).isFalse();
    }

    @Test
    void shouldReturnEligibleIfAllMethodsEligible() {
        Method method1 = FakeMethodMother.eligible();
        Method method2 = FakeMethodMother.eligible();

        Methods methods = new Methods(method1, method2);

        assertThat(methods.isEligible()).isTrue();
    }

    @Test
    void shouldReturnIneligibleIfAtLeastOnMethodIsIneligible() {
        Method eligible = FakeMethodMother.eligible();
        Method ineligible = FakeMethodMother.ineligible();

        Methods methods = new Methods(eligible, ineligible);

        assertThat(methods.isEligible()).isFalse();
    }

    @Test
    void shouldReturnCompleteIfAllMethodsComplete() {
        Method method1 = FakeMethodMother.complete();
        Method method2 = FakeMethodMother.complete();

        Methods methods = new Methods(method1, method2);

        assertThat(methods.isComplete()).isTrue();
    }

    @Test
    void shouldReturnIncompleteIfAtLeastOnMethodIsNotComplete() {
        Method complete = FakeMethodMother.complete();
        Method incomplete = FakeMethodMother.incomplete();

        Methods methods = new Methods(complete, incomplete);

        assertThat(methods.isComplete()).isFalse();
    }

    @Test
    void shouldReturnSuccessfulIfAllMethodsSuccessful() {
        Method method1 = FakeMethodMother.successful();
        Method method2 = FakeMethodMother.successful();

        Methods methods = new Methods(method1, method2);

        assertThat(methods.isSuccessful()).isTrue();
    }

    @Test
    void shouldReturnUnsuccessfulIfAtLeastOnMethodIsNotSuccessful() {
        Method successful = FakeMethodMother.successful();
        Method unsuccessful = FakeMethodMother.unsuccessful();

        Methods methods = new Methods(successful, unsuccessful);

        assertThat(methods.isSuccessful()).isFalse();
    }

    @Test
    void shouldReturnSumOfMethodDurations() {
        Method method1 = FakeMethodMother.withDuration(Duration.ofMinutes(2));
        Method method2 = FakeMethodMother.withDuration(Duration.ofMinutes(3));

        Methods methods = new Methods(method1, method2);

        assertThat(methods.getDuration()).isEqualTo(Duration.ofMinutes(5));
    }

    @Test
    void shouldReturnNextIncompleteMethodMatchingName() {
        Method complete = FakeMethodMother.builder()
                .name("method1")
                .overrideComplete(true)
                .build();
        Method incomplete = FakeMethodMother.builder()
                .name("method2")
                .overrideComplete(false)
                .build();
        Methods methods = new Methods(complete, incomplete);

        Optional<Method> next = methods.getNext(incomplete.getName());

        assertThat(next).contains(incomplete);
    }

    @Test
    void shouldNotReturnIsNextTrueIfMethodIsNotNext() {
        Method method1 = FakeMethodMother.builder()
                .name("method1")
                .overrideComplete(false)
                .build();
        Method method2 = FakeMethodMother.builder()
                .name("method2")
                .overrideComplete(false)
                .build();
        Methods methods = new Methods(method1, method2);

        Optional<Method> next = methods.getNext(method2.getName());

        assertThat(next).isEmpty();
    }

    @Test
    void shouldUpdateMethods() {
        UnaryOperator<Method> function = mock(UnaryOperator.class);
        Method method1 = FakeMethodMother.withName("method1");
        Method method2 = FakeMethodMother.withName("method2");
        Method updatedMethod1 = givenUpdatedMethod(function, method1);
        Method updatedMethod2 = givenUpdatedMethod(function, method2);
        Methods methods = MethodsMother.with(method1, method2);

        Methods updated = methods.update(function);

        assertThat(updated).containsExactly(updatedMethod1, updatedMethod2);
    }

    static Method givenUpdatedMethod(UnaryOperator<Method> function, Method method) {
        Method updated = mock(Method.class);
        given(function.apply(method)).willReturn(updated);
        return updated;
    }

}
