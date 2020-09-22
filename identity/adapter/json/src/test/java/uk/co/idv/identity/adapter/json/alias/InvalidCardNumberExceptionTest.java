package uk.co.idv.identity.adapter.json.alias;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.adapter.json.alias.cardnumber.InvalidCardTypeException;

import static org.assertj.core.api.Assertions.assertThat;

class InvalidCardNumberExceptionTest {

    @Test
    void shouldReturnMessage() {
        String message = "my-message";

        Throwable error = new InvalidCardTypeException(message);

        assertThat(error.getMessage()).isEqualTo(message);
    }

}
