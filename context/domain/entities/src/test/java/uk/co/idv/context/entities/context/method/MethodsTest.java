package uk.co.idv.context.entities.context.method;

import org.junit.jupiter.api.Test;
import uk.co.idv.method.entities.eligibility.EligibilityMother;
import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.method.fake.FakeMethod;
import uk.co.idv.method.entities.method.fake.FakeMethodMother;

import java.time.Duration;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class MethodsTest {

    @Test
    void shouldBeIterable() {
        Method method1 = FakeMethodMother.build();
        Method method2 = FakeMethodMother.build();

        Methods methods = new DefaultMethods(method1, method2);

        assertThat(methods).containsExactly(
                method1,
                method2
        );
    }

    @Test
    void shouldReturnValues() {
        Method method1 = FakeMethodMother.build();
        Method method2 = FakeMethodMother.build();

        Methods methods = new DefaultMethods(method1, method2);

        assertThat(methods.getValues()).containsExactly(
                method1,
                method2
        );
    }

    @Test
    void shouldReturnTrueIfEmpty() {
        Methods methods = new DefaultMethods();

        boolean empty = methods.isEmpty();

        assertThat(empty).isTrue();
    }

    @Test
    void shouldReturnFalseIfNotEmpty() {
        Methods methods = new DefaultMethods(FakeMethodMother.build());

        boolean empty = methods.isEmpty();

        assertThat(empty).isFalse();
    }

    @Test
    void shouldReturnEligibleIfAllMethodsEligible() {
        Method method1 = FakeMethodMother.eligible();
        Method method2 = FakeMethodMother.eligible();

        Methods methods = new DefaultMethods(method1, method2);

        assertThat(methods.isEligible()).isTrue();
    }

    @Test
    void shouldReturnIneligibleIfAtLeastOnMethodIsIneligible() {
        Method eligible = FakeMethodMother.eligible();
        Method ineligible = FakeMethodMother.ineligible();

        Methods methods = new DefaultMethods(eligible, ineligible);

        assertThat(methods.isEligible()).isFalse();
    }

    @Test
    void shouldReturnCompleteIfAllMethodsComplete() {
        Method method1 = FakeMethodMother.complete();
        Method method2 = FakeMethodMother.complete();

        Methods methods = new DefaultMethods(method1, method2);

        assertThat(methods.isComplete()).isTrue();
    }

    @Test
    void shouldReturnIncompleteIfAtLeastOnMethodIsNotComplete() {
        Method complete = FakeMethodMother.complete();
        Method incomplete = FakeMethodMother.incomplete();

        Methods methods = new DefaultMethods(complete, incomplete);

        assertThat(methods.isComplete()).isFalse();
    }

    @Test
    void shouldReturnSuccessfulIfAllMethodsSuccessful() {
        Method method1 = FakeMethodMother.successful();
        Method method2 = FakeMethodMother.successful();

        Methods methods = new DefaultMethods(method1, method2);

        assertThat(methods.isSuccessful()).isTrue();
    }

    @Test
    void shouldReturnUnsuccessfulIfAtLeastOnMethodIsNotSuccessful() {
        Method successful = FakeMethodMother.successful();
        Method unsuccessful = FakeMethodMother.unsuccessful();

        Methods methods = new DefaultMethods(successful, unsuccessful);

        assertThat(methods.isSuccessful()).isFalse();
    }

    @Test
    void shouldReturnSumOfMethodDurations() {
        Method method1 = FakeMethodMother.withDuration(Duration.ofMinutes(2));
        Method method2 = FakeMethodMother.withDuration(Duration.ofMinutes(3));

        Methods methods = new DefaultMethods(method1, method2);

        assertThat(methods.getDuration()).isEqualTo(Duration.ofMinutes(5));
    }

    @Test
    void shouldStreamMethodsThatMatchType() {
        Method method = mock(Method.class);
        FakeMethod fake = mock(FakeMethod.class);
        Methods methods = new DefaultMethods(method, fake);

        Stream<FakeMethod> fakeMethods = methods.streamAsType(FakeMethod.class);

        assertThat(fakeMethods).containsExactly(fake);
    }

    @Test
    void shouldReturnIsNextTrueIfMethodIsNext() {
        Method complete = FakeMethodMother.builder()
                .name("method1")
                .complete(true)
                .build();
        Method incomplete = FakeMethodMother.builder()
                .name("method2")
                .complete(false)
                .build();
        Methods methods = new DefaultMethods(complete, incomplete);

        boolean next = methods.isNext(incomplete.getName());

        assertThat(next).isTrue();
    }

    @Test
    void shouldNotReturnIsNextTrueIfMethodIsNotNext() {
        Method method1 = FakeMethodMother.builder()
                .name("method1")
                .complete(false)
                .build();
        Method method2 = FakeMethodMother.builder()
                .name("method2")
                .complete(false)
                .build();
        Methods methods = new DefaultMethods(method1, method2);

        boolean next = methods.isNext(method2.getName());

        assertThat(next).isFalse();
    }

    @Test
    void shouldNotReturnIneligibleMethods() {
        Method method = FakeMethodMother.ineligible();
        Methods methods = new DefaultMethods(method);

        Methods eligibleIncomplete = methods.getEligibleIncomplete();

        assertThat(eligibleIncomplete).isEmpty();
    }

    @Test
    void shouldNotReturnEligibleCompleteMethods() {
        Method method = FakeMethodMother.builder()
                .eligibility(EligibilityMother.eligible())
                .complete(true)
                .build();
        Methods methods = new DefaultMethods(method);

        Methods eligibleIncomplete = methods.getEligibleIncomplete();

        assertThat(eligibleIncomplete).isEmpty();
    }

    @Test
    void shouldNotReturnEligibleIncompleteMethods() {
        Method method = FakeMethodMother.builder()
                .eligibility(EligibilityMother.eligible())
                .complete(false)
                .build();
        Methods methods = new DefaultMethods(method);

        Methods eligibleIncomplete = methods.getEligibleIncomplete();

        assertThat(eligibleIncomplete).containsExactly(method);
    }

}
