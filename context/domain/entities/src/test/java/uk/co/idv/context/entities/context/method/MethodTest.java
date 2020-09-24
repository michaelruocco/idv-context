package uk.co.idv.context.entities.context.method;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.eligibility.Eligibility;
import uk.co.idv.context.entities.context.eligibility.EligibilityMother;
import uk.co.idv.context.entities.policy.method.MethodConfig;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class MethodTest {

    @Test
    void shouldReturnIsEligibleTrueFromEligibility() {
        Eligibility eligibility = mock(Eligibility.class);
        given(eligibility.isEligible()).willReturn(true);

        Method method = FakeMethod.builder()
                .eligibility(eligibility)
                .build();

        assertThat(method.isEligible()).isTrue();
    }

    @Test
    void shouldReturnIsEligibleFalseFromEligibility() {
        Eligibility eligibility = mock(Eligibility.class);
        given(eligibility.isEligible()).willReturn(false);

        Method method = FakeMethod.builder()
                .eligibility(eligibility)
                .build();

        assertThat(method.isEligible()).isFalse();
    }

    @Test
    void shouldReturnDurationFromConfigIfEligible() {
        Duration duration = Duration.ofMinutes(5);
        MethodConfig config = givenConfigWith(duration);

        Method method = FakeMethod.builder()
                .config(config)
                .eligibility(EligibilityMother.eligible())
                .build();

        assertThat(method.getDuration()).isEqualTo(duration);
    }

    @Test
    void shouldReturnZeroDurationIfIneligible() {
        MethodConfig config = givenConfigWith(Duration.ofMinutes(5));

        Method method = FakeMethod.builder()
                .config(config)
                .eligibility(EligibilityMother.ineligible())
                .build();

        assertThat(method.getDuration()).isEqualTo(Duration.ZERO);
    }

    private MethodConfig givenConfigWith(Duration duration) {
        MethodConfig config = mock(MethodConfig.class);
        given(config.getDuration()).willReturn(duration);
        return config;
    }

}
