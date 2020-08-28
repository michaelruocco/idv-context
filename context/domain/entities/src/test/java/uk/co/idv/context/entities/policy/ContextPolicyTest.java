package uk.co.idv.context.entities.policy;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ContextPolicyTest {

    private final ContextPolicy policy = new ContextPolicy();

    @Test
    void shouldReturnRequestedData() {
        assertThat(policy.getRequestedData()).containsExactly("phone-numbers");
    }

}
