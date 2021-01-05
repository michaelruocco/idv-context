package uk.co.idv.context.entities.context.method;

import org.junit.jupiter.api.Test;
import uk.co.idv.method.entities.method.Method;
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

}
