package uk.co.idv.context.adapter.repository;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.IdvIdMother;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.entities.identity.IdentityMother;
import uk.co.mruoc.json.JsonConverter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class IdentityItemConverterTest {

    private final JsonConverter jsonConverter = mock(JsonConverter.class);

    private final IdentityItemConverter itemConverter = new IdentityItemConverter(jsonConverter);

    @Test
    void shouldConvertAliasToPrimaryKey() {
        Alias alias = IdvIdMother.idvId();

        PrimaryKey key = itemConverter.toPrimaryKey(alias);

        PrimaryKey expectedKey = new PrimaryKey("alias", alias.format());
        assertThat(key).isEqualTo(expectedKey);
    }

    @Test
    void shouldConvertItemToIdentity() {
        String json = "{}";
        Item item = new Item().withJSON("body", json);
        Identity expectedIdentity = IdentityMother.example();
        given(jsonConverter.toObject(json, Identity.class)).willReturn(expectedIdentity);

        Identity identity = itemConverter.toIdentity(item);

        assertThat(identity).isEqualTo(expectedIdentity);
    }

    @Test
    void shouldConvertAliasAndIdentityToItem() {
        Alias alias = IdvIdMother.idvId();
        Identity identity = IdentityMother.withAliases(alias);
        String json = "{}";
        given(jsonConverter.toJson(identity)).willReturn(json);

        Item item = itemConverter.toItem(identity, alias);

        Item expectedItem = new Item()
                .withPrimaryKey("alias", alias.format())
                .with("idvId", identity.getIdvIdValue().toString())
                .withJSON("body", json);
        assertThat(item).isEqualTo(expectedItem);
    }

}
