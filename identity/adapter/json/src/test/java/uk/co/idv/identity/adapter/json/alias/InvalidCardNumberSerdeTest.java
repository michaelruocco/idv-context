package uk.co.idv.identity.adapter.json.alias;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.identity.adapter.json.alias.cardnumber.CardNumberModule;
import uk.co.idv.identity.adapter.json.alias.cardnumber.InvalidCardTypeException;
import uk.co.idv.identity.entities.alias.CardNumber;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class InvalidCardNumberSerdeTest {

    private static final ObjectMapper MAPPER = new ObjectMapper().registerModule(new CardNumberModule());
    private static final String JSON = InvalidCardNumberJsonMother.invalid();

    @Test
    void shouldDeserialize() {
        Throwable error = catchThrowable(() -> MAPPER.readValue(JSON, CardNumber.class));

        assertThat(error)
                .isInstanceOf(InvalidCardTypeException.class)
                .hasMessage("invalid-card-number");
    }

}
