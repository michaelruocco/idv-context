package uk.co.idv.lockout.adapter.repository;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableWriteItems;
import lombok.Builder;
import uk.co.idv.identity.entities.alias.IdvId;
import uk.co.idv.lockout.entities.attempt.Attempts;
import uk.co.mruoc.json.JsonConverter;

import java.util.Collection;


@Builder
public class AttemptItemConverter {

    private final Table table;
    private final JsonConverter jsonConverter;

    public PrimaryKey toPrimaryKey(IdvId idvId) {
        return new PrimaryKey("idvId", idvId.getValue());
    }

    public Attempts toAttempts(Item item) {
        return jsonConverter.toObject(item.getJSON("body"), Attempts.class);
    }

    public Item toItem(Attempts attempts) {
        return new Item()
                .withPrimaryKey("idvId", attempts.getIdvId().getValue())
                .withJSON("body", jsonConverter.toJson(attempts));
    }

    public TableWriteItems toBatchDeleteItems(Collection<IdvId> idvIds) {
        return new TableWriteItems(table.getTableName())
                .withPrimaryKeysToDelete(toPrimaryKeys(idvIds));
    }

    private PrimaryKey[] toPrimaryKeys(Collection<IdvId> idvIds) {
        return idvIds.stream().map(this::toPrimaryKey).toArray(PrimaryKey[]::new);
    }

}
