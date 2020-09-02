package uk.co.idv.context.adapter.repository;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.alias.IdvId;
import uk.co.idv.identity.entities.alias.IdvIdMother;
import uk.co.idv.context.entities.lockout.attempt.Attempts;
import uk.co.idv.context.entities.lockout.attempt.AttemptsMother;
import uk.co.mruoc.json.JsonConverter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class AttemptItemConverterTest {

    private final JsonConverter jsonConverter = mock(JsonConverter.class);

    private final AttemptItemConverter itemConverter = new AttemptItemConverter(jsonConverter);

    @Test
    void shouldConvertIdToPrimaryKey() {
        IdvId idvId = IdvIdMother.idvId();

        PrimaryKey key = itemConverter.toPrimaryKey(idvId);

        PrimaryKey expectedKey = new PrimaryKey("idvId", idvId.getValue());
        assertThat(key).isEqualTo(expectedKey);
    }

    @Test
    void shouldConvertItemToAttempts() {
        String json = "{}";
        Item item = new Item().withJSON("body", json);
        Attempts expectedAttempts = AttemptsMother.build();
        given(jsonConverter.toObject(json, Attempts.class)).willReturn(expectedAttempts);

        Attempts attempts = itemConverter.toAttempts(item);

        assertThat(attempts).isEqualTo(expectedAttempts);
    }

    @Test
    void shouldConvertAttemptsToItem() {
        Attempts attempts = AttemptsMother.build();
        String json = "{}";
        given(jsonConverter.toJson(attempts)).willReturn(json);

        Item item = itemConverter.toItem(attempts);

        Item expectedItem = new Item()
                .withPrimaryKey("idvId", attempts.getIdvId().getValue())
                .withJSON("body", json);
        assertThat(item).isEqualTo(expectedItem);
    }

}
