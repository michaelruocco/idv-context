package uk.co.idv.context.entities.policy.method.otp.delivery.phone.simswap;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.idv.context.entities.policy.method.otp.delivery.phone.simswap.AcceptableSimSwapStatuses.SUCCESS;
import static uk.co.idv.context.entities.policy.method.otp.delivery.phone.simswap.AcceptableSimSwapStatuses.FAILURE;
import static uk.co.idv.context.entities.policy.method.otp.delivery.phone.simswap.AcceptableSimSwapStatuses.UNKNOWN;
import static uk.co.idv.context.entities.policy.method.otp.delivery.phone.simswap.AcceptableSimSwapStatuses.TIMEOUT;


class AcceptableSimSwapStatusesTest {

    private final AcceptableSimSwapStatuses statuses = new AcceptableSimSwapStatuses(
            SUCCESS,
            TIMEOUT,
            UNKNOWN
    );

    @Test
    void shouldReturnStatuses() {
        assertThat(statuses).containsExactly(
                SUCCESS,
                TIMEOUT,
                UNKNOWN
        );
    }

    @Test
    void shouldReturnStatusAcceptableIfPresent() {
        boolean acceptable = statuses.isAcceptable(SUCCESS);

        assertThat(acceptable).isTrue();
    }

    @Test
    void shouldReturnStatusNotAcceptableIfNotPresent() {
        boolean acceptable = statuses.isAcceptable(FAILURE);

        assertThat(acceptable).isFalse();
    }

}
