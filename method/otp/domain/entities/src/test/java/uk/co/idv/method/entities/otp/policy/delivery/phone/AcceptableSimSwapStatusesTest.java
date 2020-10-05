package uk.co.idv.method.entities.otp.policy.delivery.phone;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.idv.method.entities.otp.policy.delivery.phone.AcceptableSimSwapStatuses.FAILURE;
import static uk.co.idv.method.entities.otp.policy.delivery.phone.AcceptableSimSwapStatuses.SUCCESS;
import static uk.co.idv.method.entities.otp.policy.delivery.phone.AcceptableSimSwapStatuses.TIMEOUT;
import static uk.co.idv.method.entities.otp.policy.delivery.phone.AcceptableSimSwapStatuses.UNKNOWN;


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
