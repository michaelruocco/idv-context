package uk.co.idv.context.adapter.repository;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import lombok.RequiredArgsConstructor;
import uk.co.idv.context.entities.context.Context;
import uk.co.mruoc.json.JsonConverter;

import java.util.UUID;


@RequiredArgsConstructor
public class ContextItemConverter {

    private final JsonConverter jsonConverter;

    public PrimaryKey toPrimaryKey(UUID id) {
        return new PrimaryKey("id", id.toString());
    }

    public Context toContext(Item item) {
        return jsonConverter.toObject(item.getJSON("body"), Context.class);
    }

    public Item toItem(Context context) {
        return new Item()
                .withPrimaryKey("id", context.getId().toString())
                .withJSON("body", jsonConverter.toJson(context));
    }

}
