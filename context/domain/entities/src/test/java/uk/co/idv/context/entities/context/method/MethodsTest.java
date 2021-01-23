package uk.co.idv.context.entities.context.method;

import org.junit.jupiter.api.Test;
import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.method.MethodVerifications;
import uk.co.idv.method.entities.method.MockMethodMother;
import uk.co.idv.method.entities.method.fake.FakeMethodMother;

import java.time.Duration;
import java.util.Collection;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class MethodsTest {

    @Test
    void shouldBeIterable() {
        Method method1 = mock(Method.class);
        Method method2 = mock(Method.class);

        Methods methods = MethodsMother.with(method1, method2);

        assertThat(methods).contains(method1, method2);
    }

    @Test
    void shouldReturnValues() {
        Method method1 = mock(Method.class);
        Method method2 = mock(Method.class);
        Methods methods = MethodsMother.with(method1, method2);

        Collection<Method> values = methods.getValues();

        assertThat(values).contains(method1, method2);
    }

    @Test
    void shouldReturnStreamOfMethods() {
        Method method1 = mock(Method.class);
        Method method2 = mock(Method.class);
        Methods methods = MethodsMother.with(method1, method2);

        Stream<Method> stream = methods.stream();

        assertThat(stream).contains(method1, method2);
    }

    @Test
    void shouldReturnTrueIfEmpty() {
        Methods methods = MethodsMother.empty();

        boolean empty = methods.isEmpty();

        assertThat(empty).isTrue();
    }

    @Test
    void shouldReturnFalseIfNotEmpty() {
        Methods methods = MethodsMother.with(FakeMethodMother.build());

        boolean empty = methods.isEmpty();

        assertThat(empty).isFalse();
    }

    @Test
    void shouldReturnZeroTotalDurationIfEmpty() {
        Methods methods = MethodsMother.empty();

        Duration totalDuration = methods.getTotalDuration();

        assertThat(totalDuration).isEqualTo(Duration.ZERO);
    }

    @Test
    void shouldReturnSumOfMethodDurationsAsTotalDuration() {
        Method method1 = FakeMethodMother.withDuration(Duration.ofMinutes(2));
        Method method2 = FakeMethodMother.withDuration(Duration.ofMinutes(3));
        Methods methods = MethodsMother.with(method1, method2);

        Duration totalDuration = methods.getTotalDuration();

        assertThat(totalDuration).isEqualTo(Duration.ofMinutes(5));
    }

    @Test
    void shouldReturnShortestDurationZeroIfEmpty() {
        Methods methods = MethodsMother.empty();

        Duration duration = methods.getShortestDuration();

        assertThat(duration).isEqualTo(Duration.ZERO);
    }

    @Test
    void shouldReturnShortestDurationFromMethodWithShortestDuration() {
        Duration shortest = Duration.ofMinutes(5);
        Method method1 = FakeMethodMother.withDuration(shortest);
        Method method2 = FakeMethodMother.withDuration(shortest.plusMillis(1));
        Methods methods = MethodsMother.with(method1, method2);

        Duration duration = methods.getShortestDuration();

        assertThat(duration).isEqualTo(shortest);
    }

    @Test
    void shouldReturnMethodsByMethodName() {
        String methodName = "my-method";
        Method method = FakeMethodMother.withName(methodName);
        Method otherNameMethod = FakeMethodMother.withName("other-method");
        Methods methods = MethodsMother.with(method, otherNameMethod);

        Methods methodsByName = methods.getByName(methodName);

        assertThat(methodsByName).containsExactly(method);
    }

    @Test
    void shouldReturnTrueIfContainsMethodWithName() {
        String methodName = "my-method";
        Method method = FakeMethodMother.withName(methodName);
        Methods methods = MethodsMother.with(method);

        boolean containsMethod = methods.containsMethod(methodName);

        assertThat(containsMethod).isTrue();
    }

    @Test
    void shouldFalseTrueIfDoesNotContainMethodWithName() {
        String methodName = "my-method";
        Method method = FakeMethodMother.withName("other-name");
        Methods methods = MethodsMother.with(method);

        boolean containsMethod = methods.containsMethod(methodName);

        assertThat(containsMethod).isFalse();
    }

    @Test
    void shouldReturnMethodsWithUpdatesApplied() {
        Method method1 = FakeMethodMother.withName("my-method");
        Method method2 = FakeMethodMother.withName("my-other-method");
        Method updatedMethod = mock(Method.class);
        Methods methods = MethodsMother.with(method1, method2);

        Methods updatedMethods = methods.updateMethods(method -> updatedMethod);

        assertThat(updatedMethods).containsExactly(updatedMethod, updatedMethod);
    }

    @Test
    void shouldReturnAllSuccessfulIfAllMethodsSuccessful() {
        MethodVerifications verifications = mock(MethodVerifications.class);
        Method successful1 = MockMethodMother.successful(verifications);
        Method successful2 = MockMethodMother.successful(verifications);
        Methods methods = MethodsMother.with(successful1, successful2);

        boolean allSuccessful = methods.allSuccessful(verifications);

        assertThat(allSuccessful).isTrue();
    }

    @Test
    void shouldReturnUnsuccessfulIfAtLeastOneMethodIsUnsuccessful() {
        MethodVerifications verifications = mock(MethodVerifications.class);
        Method successful = MockMethodMother.successful(verifications);
        Method unsuccessful = MockMethodMother.unsuccessful(verifications);
        Methods methods = MethodsMother.with(successful, unsuccessful);

        boolean allSuccessful = methods.allSuccessful(verifications);

        assertThat(allSuccessful).isFalse();
    }

    @Test
    void shouldReturnAllCompleteIfAllMethodsComplete() {
        MethodVerifications verifications = mock(MethodVerifications.class);
        Method complete1 = MockMethodMother.complete(verifications);
        Method complete2 = MockMethodMother.complete(verifications);
        Methods methods = MethodsMother.with(complete1, complete2);

        boolean allComplete = methods.allComplete(verifications);

        assertThat(allComplete).isTrue();
    }

    @Test
    void shouldReturnIncompleteIfAtLeastOneMethodIsIncomplete() {
        MethodVerifications verifications = mock(MethodVerifications.class);
        Method complete = MockMethodMother.complete(verifications);
        Method incomplete = MockMethodMother.incomplete(verifications);
        Methods methods = MethodsMother.with(complete, incomplete);

        boolean allComplete = methods.allComplete(verifications);

        assertThat(allComplete).isFalse();
    }

    @Test
    void shouldReturnCompletedMethodCount() {
        MethodVerifications verifications = mock(MethodVerifications.class);
        Method complete1 = MockMethodMother.complete(verifications);
        Method incomplete = MockMethodMother.incomplete(verifications);
        Method complete2 = MockMethodMother.complete(verifications);
        Methods methods = MethodsMother.with(complete1, incomplete, complete2);

        long completedCount = methods.completedCount(verifications);

        assertThat(completedCount).isEqualTo(2);
    }

}
