package uk.co.idv.context.entities.policy.sequence.stage;

import org.junit.jupiter.api.Test;
import uk.co.idv.method.entities.method.Methods;
import uk.co.idv.method.entities.method.MockMethodsMother;
import uk.co.idv.method.entities.eligibility.Eligibility;
import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.method.MethodVerifications;
import uk.co.idv.method.entities.method.MockMethodMother;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class AnyMethodStageTypeTest {

    private final StageType policy = new AnyMethodStageType();

    @Test
    void shouldReturnType() {
        assertThat(policy.getName()).isEqualTo("any-method");
    }

    @Test
    void shouldReturnEligibleIfAtLeastOneMethodEligible() {
        Methods methods = MockMethodsMother.withAtLeastOneEligible();

        Eligibility eligibility = policy.calculateEligibility(methods);

        assertThat(eligibility.isEligible()).isTrue();
    }

    @Test
    void shouldReturnIneligibleIfAllMethodsAreIneligible() {
        Methods methods = MockMethodsMother.withAllIneligible();

        Eligibility eligibility = policy.calculateEligibility(methods);

        assertThat(eligibility.isEligible()).isFalse();
    }

    @Test
    void shouldReturnLongestDurationFromMethodsAsDuration() {
        Duration expectedDuration = Duration.ofMinutes(5);
        Methods methods = MockMethodsMother.withLongestDuration(expectedDuration);

        Duration duration = policy.calculateDuration(methods);

        assertThat(duration).isEqualTo(expectedDuration);
    }

    @Test
    void shouldReturnSuccessfulIfAtLeastOneMethodSuccessful() {
        MethodVerifications verifications = mock(MethodVerifications.class);
        Methods methods = MockMethodsMother.withContainsSuccessful(verifications);

        boolean successful = policy.calculateSuccessful(methods, verifications);

        assertThat(successful).isTrue();
    }

    @Test
    void shouldReturnCompleteIfAtLeastOneMethodSuccessful() {
        MethodVerifications verifications = mock(MethodVerifications.class);
        Methods methods = MockMethodsMother.withContainsSuccessful(verifications);

        boolean complete = policy.calculateComplete(methods, verifications);

        assertThat(complete).isTrue();
    }

    @Test
    void shouldReturnCompleteIfAllMethodsComplete() {
        MethodVerifications verifications = mock(MethodVerifications.class);
        Methods methods = MockMethodsMother.withAllComplete(verifications);

        boolean complete = policy.calculateComplete(methods, verifications);

        assertThat(complete).isTrue();
    }

    @Test
    void shouldNotReturnCompleteIfNoSuccessfulMethodsAndAtLeastOneMethodNotComplete() {
        MethodVerifications verifications = mock(MethodVerifications.class);
        Methods methods = MockMethodsMother.withoutIncompleteMethods(verifications);

        boolean complete = policy.calculateComplete(methods, verifications);

        assertThat(complete).isFalse();
    }

    @Test
    void shouldReturnEmptyNextMethodsIfContainsSuccessfulMethod() {
        MethodVerifications verifications = mock(MethodVerifications.class);
        Methods methods = MockMethodsMother.withContainsSuccessful(verifications);

        Methods nextMethods = policy.calculateNextMethods(methods, verifications);

        assertThat(nextMethods).isEmpty();
    }

    @Test
    void shouldReturnAllIncompleteMethodsAsNextMethods() {
        MethodVerifications verifications = mock(MethodVerifications.class);
        Method incompleteMethod = MockMethodMother.incomplete(verifications);
        Methods methods = MockMethodsMother.withAllIncompleteMethods(verifications, incompleteMethod);

        Methods nextMethods = policy.calculateNextMethods(methods, verifications);

        assertThat(nextMethods).containsExactly(incompleteMethod);
    }

}
