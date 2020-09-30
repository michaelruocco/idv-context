package uk.co.idv.context.adapter.repository;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import org.junit.jupiter.api.Test;
import uk.co.idv.context.adapter.dynamo.TimeToLiveCalculator;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.context.ContextMother;
import uk.co.mruoc.json.JsonConverter;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ContextItemConverterTest {

    private final JsonConverter jsonConverter = mock(JsonConverter.class);
    private final TimeToLiveCalculator timeToLiveCalculator = mock(TimeToLiveCalculator.class);

    private final ContextItemConverter itemConverter = ContextItemConverter.builder()
            .jsonConverter(jsonConverter)
            .timeToLiveCalculator(timeToLiveCalculator)
            .build();

    @Test
    void shouldConvertIdToPrimaryKey() {
        UUID id = UUID.randomUUID();

        PrimaryKey key = itemConverter.toPrimaryKey(id);

        PrimaryKey expectedKey = new PrimaryKey("id", id.toString());
        assertThat(key).isEqualTo(expectedKey);
    }

    @Test
    void shouldConvertItemToContext() {
        String json = "{}";
        Item item = new Item().withJSON("body", json);
        Context expectedContext = ContextMother.build();
        given(jsonConverter.toObject(json, Context.class)).willReturn(expectedContext);

        Context context = itemConverter.toContext(item);

        assertThat(context).isEqualTo(expectedContext);
    }

    @Test
    void shouldConvertContextToItem() {
        Context context = ContextMother.build();
        String json = "{}";
        given(jsonConverter.toJson(context)).willReturn(json);
        long timeToLive = 100;
        given(timeToLiveCalculator.calculate()).willReturn(timeToLive);

        Item item = itemConverter.toItem(context);

        Item expectedItem = new Item()
                .withPrimaryKey("id", context.getId().toString())
                .with("ttl", timeToLive)
                .withJSON("body", json);
        assertThat(item).isEqualTo(expectedItem);
    }

}
