package uk.co.idv.policy.usecases.policy.load;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class PolicyNotFoundExceptionTest {

    @Test
    void shouldReturnMessage() {
        UUID id = UUID.randomUUID();

        Throwable error = new PolicyNotFoundException(id);

        assertThat(error.getMessage()).isEqualTo(id.toString());
    }

}
