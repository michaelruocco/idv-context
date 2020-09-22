package uk.co.idv.identity.adapter.json.alias;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import uk.co.idv.identity.adapter.json.alias.cardnumber.CardNumberModule;
import uk.co.idv.identity.entities.alias.CardNumber;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class CardNumberSerdeTest {

    private static final ObjectMapper MAPPER = new ObjectMapper().registerModule(new CardNumberModule());

    @ParameterizedTest(name = "should serialize card number {1}")
    @ArgumentsSource(CardNumberArgumentsProvider.class)
    void shouldSerialize(String expectedJson, CardNumber cardNumber) throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(cardNumber);

        assertThatJson(json).isEqualTo(expectedJson);
    }

    @ParameterizedTest(name = "should deserialize card number {1}")
    @ArgumentsSource(CardNumberArgumentsProvider.class)
    void shouldDeserialize(String json, CardNumber expectedCardNumber) throws JsonProcessingException {
        CardNumber cardNumber = MAPPER.readValue(json, CardNumber.class);

        assertThat(cardNumber).isEqualTo(expectedCardNumber);
    }

}
