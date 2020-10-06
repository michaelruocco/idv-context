package uk.co.idv.method.entities.otp.simswap.eligibility;

import org.junit.jupiter.api.Test;
import uk.co.idv.method.entities.otp.policy.delivery.phone.SimSwapConfig;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class SimSwapEligibilityTest {

    @Test
    void shouldReturnEligibleIfStatusIsAcceptable() {
        String status = "success";

        SimSwapEligibility eligibility = SimSwapEligibility.builder()
                .status(status)
                .config(givenSimSwapConfigAcceptingStatus(status))
                .build();

        assertThat(eligibility.isEligible()).isTrue();
    }

    @Test
    void shouldReturnEmptyReasonIfEligible() {
        String status = "success";

        SimSwapEligibility eligibility = SimSwapEligibility.builder()
                .status(status)
                .config(givenSimSwapConfigAcceptingStatus(status))
                .build();

        assertThat(eligibility.getReason()).isEmpty();
    }

    @Test
    void shouldReturnIneligibleIfStatusIsNotAcceptable() {
        String status = "failure";

        SimSwapEligibility eligibility = SimSwapEligibility.builder()
                .status(status)
                .config(givenSimSwapConfigRejectingStatus(status))
                .build();

        assertThat(eligibility.isEligible()).isFalse();
    }

    @Test
    void shouldReturnReasonIfNotEligible() {
        String status = "failure";

        SimSwapEligibility eligibility = SimSwapEligibility.builder()
                .status(status)
                .config(givenSimSwapConfigRejectingStatus(status))
                .build();

        String expectedMessage = String.format("sim swap status %s not acceptable", status);
        assertThat(eligibility.getReason()).contains(expectedMessage);
    }

    private SimSwapConfig givenSimSwapConfigAcceptingStatus(String status) {
        SimSwapConfig config = mock(SimSwapConfig.class);
        given(config.isAcceptable(status)).willReturn(true);
        return config;
    }

    private SimSwapConfig givenSimSwapConfigRejectingStatus(String status) {
        SimSwapConfig config = mock(SimSwapConfig.class);
        given(config.isAcceptable(status)).willReturn(false);
        return config;
    }

}
