package uk.co.idv.context.entities.context.method;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.eligibility.EligibilityMother;
import uk.co.idv.context.entities.context.method.fake.FakeMethod;
import uk.co.idv.context.entities.context.method.fake.FakeMethodMother;

import java.time.Duration;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
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
    void shouldStreamMethodsThatMatchType() {
        Method method = mock(Method.class);
        FakeMethod fake = mock(FakeMethod.class);
        Methods methods = new Methods(method, fake);

        Stream<FakeMethod> fakeMethods = methods.streamAsType(FakeMethod.class);

        assertThat(fakeMethods).containsExactly(fake);
    }

    @Test
    void shouldReturnNextIncompleteMethod() {
        Method complete = FakeMethodMother.complete();
        Method incomplete = FakeMethodMother.incomplete();
        Methods methods = new Methods(complete, incomplete);

        Optional<Method> nextMethod = methods.getNext();

        assertThat(nextMethod).contains(incomplete);
    }

    @Test
    void shouldNotReturnNextMethodIfAllComplete() {
        Method method1 = FakeMethodMother.complete();
        Method method2 = FakeMethodMother.complete();
        Methods methods = new Methods(method1, method2);

        Optional<Method> nextMethod = methods.getNext();

        assertThat(nextMethod).isEmpty();
    }

    @Test
    void shouldReturnNextIncompleteMethodIfNameMatches() {
        Method complete = FakeMethodMother.complete();
        Method incomplete = FakeMethodMother.incomplete();
        Methods methods = new Methods(complete, incomplete);

        Optional<Method> nextMethod = methods.getNext(incomplete.getName());

        assertThat(nextMethod).contains(incomplete);
    }

    @Test
    void shouldNotReturnNextIncompleteMethodIfNameDoesNotMatch() {
        Method complete = FakeMethodMother.complete();
        Method incomplete = FakeMethodMother.incomplete();
        Methods methods = new Methods(complete, incomplete);

        Optional<Method> nextMethod = methods.getNext("other-name");

        assertThat(nextMethod).isEmpty();
    }

    @Test
    void shouldNotReturnIneligibleMethods() {
        Method method = FakeMethodMother.ineligible();
        Methods methods = new Methods(method);

        Methods eligibleIncomplete = methods.getEligibleIncomplete();

        assertThat(eligibleIncomplete).isEmpty();
    }

    @Test
    void shouldNotReturnEligibleCompleteMethods() {
        Method method = FakeMethodMother.builder()
                .eligibility(EligibilityMother.eligible())
                .complete(true)
                .build();
        Methods methods = new Methods(method);

        Methods eligibleIncomplete = methods.getEligibleIncomplete();

        assertThat(eligibleIncomplete).isEmpty();
    }

    @Test
    void shouldNotReturnEligibleIncompleteMethods() {
        Method method = FakeMethodMother.builder()
                .eligibility(EligibilityMother.eligible())
                .complete(false)
                .build();
        Methods methods = new Methods(method);

        Methods eligibleIncomplete = methods.getEligibleIncomplete();

        assertThat(eligibleIncomplete).containsExactly(method);
    }

}
