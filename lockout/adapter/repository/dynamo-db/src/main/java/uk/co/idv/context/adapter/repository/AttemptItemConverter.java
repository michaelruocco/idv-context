package uk.co.idv.context.adapter.repository;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import lombok.RequiredArgsConstructor;
import uk.co.idv.identity.entities.alias.IdvId;
import uk.co.idv.context.entities.lockout.attempt.Attempts;
import uk.co.mruoc.json.JsonConverter;


@RequiredArgsConstructor
public class AttemptItemConverter {

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

}
