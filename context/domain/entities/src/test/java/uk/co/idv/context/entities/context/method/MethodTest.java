package uk.co.idv.context.entities.context.method;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.eligibility.Eligibility;
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
    void shouldReturnDurationFromConfig() {
        Duration duration = Duration.ofMinutes(5);
        MethodConfig config = mock(MethodConfig.class);
        given(config.getDuration()).willReturn(duration);

        Method method = FakeMethod.builder()
                .config(config)
                .build();

        assertThat(method.getDuration()).isEqualTo(duration);
    }

}
