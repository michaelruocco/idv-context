package uk.co.idv.method.entities.eligibility;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class IneligibleTest {

    private static final String REASON = "my-reason";

    private final Eligibility ineligible = new Ineligible(REASON);

    @Test
    void shouldNotBeEligible() {
        assertThat(ineligible.isEligible()).isFalse();
    }

    @Test
    void shouldReturnReason() {
        assertThat(ineligible.getReason()).contains(REASON);
    }

}
