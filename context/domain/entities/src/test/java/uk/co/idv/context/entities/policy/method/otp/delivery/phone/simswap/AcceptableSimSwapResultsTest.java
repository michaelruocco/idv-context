package uk.co.idv.context.entities.policy.method.otp.delivery.phone.simswap;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.idv.context.entities.policy.method.otp.delivery.phone.simswap.AcceptableSimSwapResults.SUCCESS;
import static uk.co.idv.context.entities.policy.method.otp.delivery.phone.simswap.AcceptableSimSwapResults.FAILURE;
import static uk.co.idv.context.entities.policy.method.otp.delivery.phone.simswap.AcceptableSimSwapResults.UNKNOWN;
import static uk.co.idv.context.entities.policy.method.otp.delivery.phone.simswap.AcceptableSimSwapResults.TIMEOUT;


class AcceptableSimSwapResultsTest {

    private final AcceptableSimSwapResults results = new AcceptableSimSwapResults(
            SUCCESS,
            TIMEOUT,
            UNKNOWN
    );

    @Test
    void shouldReturnResults() {
        assertThat(results).containsExactly(
                SUCCESS,
                TIMEOUT,
                UNKNOWN
        );
    }

    @Test
    void shouldReturnResultAcceptableIfPresent() {
        boolean acceptable = results.isAcceptable(SUCCESS);

        assertThat(acceptable).isTrue();
    }

    @Test
    void shouldReturnResultNotAcceptableIfNotPresent() {
        boolean acceptable = results.isAcceptable(FAILURE);

        assertThat(acceptable).isFalse();
    }

}
