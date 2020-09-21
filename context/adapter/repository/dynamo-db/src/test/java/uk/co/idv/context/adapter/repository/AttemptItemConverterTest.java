package uk.co.idv.context.adapter.repository;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.context.ContextMother;
import uk.co.mruoc.json.JsonConverter;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class AttemptItemConverterTest {

    private final JsonConverter jsonConverter = mock(JsonConverter.class);

    private final ContextItemConverter itemConverter = new ContextItemConverter(jsonConverter);

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

        Item item = itemConverter.toItem(context);

        Item expectedItem = new Item()
                .withPrimaryKey("id", context.getId().toString())
                .withJSON("body", json);
        assertThat(item).isEqualTo(expectedItem);
    }

}
