package uk.co.idv.lockout.adapter.repository;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableWriteItems;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.alias.IdvId;
import uk.co.idv.identity.entities.alias.IdvIdMother;
import uk.co.idv.lockout.entities.attempt.Attempts;
import uk.co.idv.lockout.entities.attempt.AttemptsMother;
import uk.co.mruoc.json.JsonConverter;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class AttemptItemConverterTest {

    private static final String TABLE_NAME = "table-name";

    private final JsonConverter jsonConverter = mock(JsonConverter.class);
    private final Table table = mock(Table.class);

    private final AttemptItemConverter itemConverter = AttemptItemConverter.builder()
            .jsonConverter(jsonConverter)
            .table(table)
            .build();

    @BeforeEach
    void setUp() {
        given(table.getTableName()).willReturn(TABLE_NAME);
    }

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

    @Test
    void shouldConvertIdvIdsToTableWriteItemsForDelete() {
        IdvId idvId1 = IdvIdMother.idvId();
        IdvId idvId2 = IdvIdMother.idvId1();

        TableWriteItems writeItems = itemConverter.toBatchDeleteItems(Arrays.asList(idvId1, idvId2));

        assertThat(writeItems.getTableName()).isEqualTo(TABLE_NAME);
        assertThat(writeItems.getPrimaryKeysToDelete()).containsExactly(
                new PrimaryKey("idvId", idvId1.getValue()),
                new PrimaryKey("idvId", idvId2.getValue())
        );
    }



}
