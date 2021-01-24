package uk.co.idv.context.adapter.json.policy.sequence.stage.type;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InvalidStageTypeExceptionTest {

    @Test
    void shouldReturnMessage() {
        String message = "message";

        Throwable error = new InvalidStageTypeException(message);

        assertThat(error.getMessage()).isEqualTo(message);
    }

}
