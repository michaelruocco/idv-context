package uk.co.idv.context.adapter.repository;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import lombok.Builder;
import uk.co.idv.context.adapter.dynamo.TimeToLiveCalculator;
import uk.co.idv.context.entities.context.Context;
import uk.co.mruoc.json.JsonConverter;

import java.util.UUID;


@Builder
public class ContextItemConverter {

    private final JsonConverter jsonConverter;
    private final TimeToLiveCalculator timeToLiveCalculator;

    public PrimaryKey toPrimaryKey(UUID id) {
        return new PrimaryKey("id", id.toString());
    }

    public Context toContext(Item item) {
        return jsonConverter.toObject(item.getJSON("body"), Context.class);
    }

    public Item toItem(Context context) {
        return new Item()
                .withPrimaryKey("id", context.getId().toString())
                .with("ttl", timeToLiveCalculator.calculate())
                .withJSON("body", jsonConverter.toJson(context));
    }

}
