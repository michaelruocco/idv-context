package uk.co.idv.context.entities.context.eligibility;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EligibleTest {

    private final Eligibility eligible = new Eligible();

    @Test
    void shouldBeEligible() {
        assertThat(eligible.isEligible()).isTrue();
    }

    @Test
    void reasonShouldBeEmpty() {
        assertThat(eligible.getReason()).isEmpty();
    }

}
