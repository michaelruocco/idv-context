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

class AllMethodsStageTypeTest {

    private final StageType policy = new AllMethodsStageType();

    @Test
    void shouldReturnType() {
        assertThat(policy.getName()).isEqualTo("all-methods");
    }

    @Test
    void shouldReturnEligibleIfAtLeastOneMethodEligible() {
        Methods methods = MockMethodsMother.withAtLeastOneEligible();

        Eligibility eligibility = policy.calculateEligibility(methods);

        assertThat(eligibility.isEligible()).isTrue();
    }

    @Test
    void shouldReturnIneligibleIfAnyMethodsAreIneligible() {
        Methods methods = MockMethodsMother.withIneligibleMethodNames("my-method");

        Eligibility eligibility = policy.calculateEligibility(methods);

        assertThat(eligibility.isEligible()).isFalse();
    }

    @Test
    void shouldReturnTotalDurationFromMethodsAsDuration() {
        Duration expectedDuration = Duration.ofMinutes(5);
        Methods methods = MockMethodsMother.withTotalDuration(expectedDuration);

        Duration duration = policy.calculateDuration(methods);

        assertThat(duration).isEqualTo(expectedDuration);
    }

    @Test
    void shouldReturnSuccessfulIfAllMethodsSuccessful() {
        MethodVerifications verifications = mock(MethodVerifications.class);
        Methods methods = MockMethodsMother.withAllSuccessful(verifications);

        boolean successful = policy.calculateSuccessful(methods, verifications);

        assertThat(successful).isTrue();
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
    void shouldReturnAllIncompleteMethodsAsNextMethods() {
        MethodVerifications verifications = mock(MethodVerifications.class);
        Method incompleteMethod = MockMethodMother.incomplete(verifications);
        Methods methods = MockMethodsMother.withAllIncompleteMethods(verifications, incompleteMethod);

        Methods nextMethods = policy.calculateNextMethods(methods, verifications);

        assertThat(nextMethods).containsExactly(incompleteMethod);
    }

}
