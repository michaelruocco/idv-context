package uk.co.idv.context.entities.context.sequence.stage;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.method.Methods;
import uk.co.idv.context.entities.policy.sequence.stage.StageType;
import uk.co.idv.method.entities.eligibility.Eligibility;
import uk.co.idv.method.entities.eligibility.EligibilityMother;
import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.method.MethodVerifications;
import uk.co.idv.method.entities.method.fake.FakeMethodMother;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.function.UnaryOperator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class StageTest {

    private final StageType typePolicy = mock(StageType.class);
    private final Methods methods = mock(Methods.class);

    private final Stage stage = Stage.builder()
            .type(typePolicy)
            .methods(methods)
            .build();

    @Test
    void shouldReturnMethods() {
        assertThat(stage.getMethods()).isEqualTo(methods);
    }

    @Test
    void shouldReturnCompletionPolicy() {
        assertThat(stage.getType()).isEqualTo(typePolicy);
    }

    @Test
    void shouldBeIterable() {
        Method method = FakeMethodMother.build();
        given(methods.iterator()).willReturn(Collections.singleton(method).iterator());

        assertThat(stage).containsExactly(method);
    }

    @Test
    void shouldReturnEligibilityFromPolicy() {
        Eligibility expectedEligibility = EligibilityMother.eligible();
        given(typePolicy.calculateEligibility(methods)).willReturn(expectedEligibility);

        Eligibility eligibility = stage.getEligibility();

        assertThat(eligibility).isEqualTo(expectedEligibility);
    }

    @Test
    void shouldReturnIsEligibleFromEligibilityFromPolicy() {
        Eligibility expectedEligibility = EligibilityMother.eligible();
        given(typePolicy.calculateEligibility(methods)).willReturn(expectedEligibility);

        boolean eligible = stage.isEligible();

        assertThat(eligible).isTrue();
    }

    @Test
    void shouldReturnDurationFromPolicy() {
        Duration expectedDuration = Duration.ofMinutes(5);
        given(typePolicy.calculateDuration(methods)).willReturn(expectedDuration);

        Duration duration = stage.getDuration();

        assertThat(duration).isEqualTo(expectedDuration);
    }

    @Test
    void shouldReturnContainsMethodFromMethods() {
        String methodName = "fake-method";
        given(methods.containsMethod(methodName)).willReturn(true);

        boolean containsMethod = stage.containsMethod(methodName);

        assertThat(containsMethod).isTrue();
    }

    @Test
    void shouldIneligibleMethodNamesFromMethods() {
        Collection<String> expectedNames = Arrays.asList("name1", "name2");
        given(methods.getIneligibleNames()).willReturn(expectedNames);

        Collection<String> names = stage.getIneligibleMethodNames();

        assertThat(names).isEqualTo(expectedNames);
    }

    @Test
    void shouldReturnUpdatedMethods() {
        UnaryOperator<Method> function = mock(UnaryOperator.class);
        Methods expectedMethods = mock(Methods.class);
        given(methods.updateMethods(function)).willReturn(expectedMethods);

        Stage updatedStage = stage.updateMethods(function);

        assertThat(updatedStage.getType()).isEqualTo(typePolicy);
        assertThat(updatedStage.getMethods()).isEqualTo(expectedMethods);
    }

    @Test
    void shouldReturnNextMethodsFromPolicy() {
        Methods expectedMethods = mock(Methods.class);
        MethodVerifications verifications = mock(MethodVerifications.class);
        given(typePolicy.calculateNextMethods(methods, verifications)).willReturn(expectedMethods);

        Methods nextMethods = stage.getNextMethods(verifications);

        assertThat(nextMethods).isEqualTo(expectedMethods);
    }

    @Test
    void shouldReturnSuccessfulFromPolicy() {
        MethodVerifications verifications = mock(MethodVerifications.class);
        given(typePolicy.calculateSuccessful(methods, verifications)).willReturn(true);

        boolean successful = stage.isSuccessful(verifications);

        assertThat(successful).isTrue();
    }

    @Test
    void shouldReturnCompleteFromPolicy() {
        MethodVerifications verifications = mock(MethodVerifications.class);
        given(typePolicy.calculateComplete(methods, verifications)).willReturn(true);

        boolean complete = stage.isComplete(verifications);

        assertThat(complete).isTrue();
    }

    @Test
    void shouldCompleteMethodCountFromMethods() {
        long expectedCount = 2;
        MethodVerifications verifications = mock(MethodVerifications.class);
        given(methods.completedCount(verifications)).willReturn(expectedCount);

        long count = stage.completedMethodCount(verifications);

        assertThat(count).isEqualTo(expectedCount);
    }

}
