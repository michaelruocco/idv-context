package uk.co.idv.context.entities.context.sequence.stage;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.method.Methods;
import uk.co.idv.context.entities.context.method.MethodsMother;
import uk.co.idv.context.entities.context.method.MockMethodsMother;
import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.method.MethodVerifications;
import uk.co.idv.method.entities.method.MockMethodMother;
import uk.co.idv.method.entities.method.fake.FakeMethodMother;

import java.time.Duration;
import java.util.function.UnaryOperator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class CompleteAllStageTest {

    @Test
    void shouldReturnMethods() {
        Methods expectedMethods = MethodsMother.oneFake();
        Stage stage = new CompleteAllStage(expectedMethods);

        Methods methods = stage.getMethods();

        assertThat(methods).isEqualTo(expectedMethods);
    }

    @Test
    void shouldReturnEligibleIfAllMethodsEligible() {
        Methods methods = MockMethodsMother.withEmptyIneligibleMethodNames();
        Stage stage = new CompleteAllStage(methods);

        boolean eligible = stage.isEligible();

        assertThat(eligible).isTrue();
    }

    @Test
    void shouldReturnIneligibleIfAtLeastOneMethodIsIneligible() {
        Methods methods = MockMethodsMother.withIneligibleMethodNames("fake-method");
        Stage stage = new CompleteAllStage(methods);

        boolean eligible = stage.isEligible();

        assertThat(eligible).isFalse();
    }

    @Test
    void shouldReturnSuccessfulFromMethodsAllSuccessful() {
        MethodVerifications verifications = mock(MethodVerifications.class);
        Methods methods = MockMethodsMother.withAllSuccessful(verifications);
        Stage stage = new CompleteAllStage(methods);

        boolean successful = stage.isSuccessful(verifications);

        assertThat(successful).isTrue();
    }

    @Test
    void shouldReturnCompleteFromMethodsAllComplete() {
        MethodVerifications verifications = mock(MethodVerifications.class);
        Methods methods = MockMethodsMother.withAllComplete(verifications);
        Stage stage = new CompleteAllStage(methods);

        boolean complete = stage.isComplete(verifications);

        assertThat(complete).isTrue();
    }

    @Test
    void shouldReturnTotalDurationFromMethodsAsDuration() {
        Duration expectedDuration = Duration.ofMinutes(5);
        Methods methods = MockMethodsMother.withTotalDuration(expectedDuration);
        Stage stage = new CompleteAllStage(methods);

        Duration duration = stage.getDuration();

        assertThat(duration).isEqualTo(expectedDuration);
    }

    @Test
    void shouldReturnAllIncompleteMethodsAsNextMethods() {
        MethodVerifications verifications = mock(MethodVerifications.class);
        Method incompleteMethod = MockMethodMother.incomplete(verifications);
        Methods expectedMethods = MockMethodsMother.withAllIncompleteMethods(verifications, incompleteMethod);
        Stage stage = new CompleteAllStage(expectedMethods);

        Methods nextMethods = stage.getNextMethods(verifications);

        assertThat(nextMethods).containsExactly(incompleteMethod);
    }

    @Test
    void shouldUpdateMethods() {
        UnaryOperator<Method> function = mock(UnaryOperator.class);
        Method method1 = FakeMethodMother.withName("method1");
        Method method2 = FakeMethodMother.withName("method2");
        Method updatedMethod1 = MockMethodMother.withUpdatedMethod(function, method1);
        Method updatedMethod2 = MockMethodMother.withUpdatedMethod(function, method2);
        Stage stage = new CompleteAllStage(MethodsMother.with(method1, method2));

        Stage updated = stage.updateMethods(function);

        assertThat(updated).containsExactly(updatedMethod1, updatedMethod2);
    }

    @Test
    void shouldReturnCompletedMethodCountFromMethods() {
        long expectedCompletedCount = 2;
        MethodVerifications verifications = mock(MethodVerifications.class);
        Methods methods = MockMethodsMother.withCompletedCount(verifications, expectedCompletedCount);
        Stage stage = new CompleteAllStage(methods);

        long completedCount = stage.completedMethodCount(verifications);

        assertThat(completedCount).isEqualTo(expectedCompletedCount);
    }

}
