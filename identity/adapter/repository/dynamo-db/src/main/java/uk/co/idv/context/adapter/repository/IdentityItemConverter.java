package uk.co.idv.context.adapter.repository;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import lombok.RequiredArgsConstructor;
import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.mruoc.json.JsonConverter;

@RequiredArgsConstructor
public class IdentityItemConverter {

    private final JsonConverter jsonConverter;

    public PrimaryKey toPrimaryKey(Alias alias) {
        return new PrimaryKey("alias", alias.format());
    }

    public Identity toIdentity(Item item) {
        return jsonConverter.toObject(item.getJSON("body"), Identity.class);
    }

    public Item toItem(Identity identity, Alias alias) {
        return new Item()
                .withPrimaryKey("alias", alias.format())
                .with("idvId", identity.getIdvIdValue().toString())
                .withJSON("body", jsonConverter.toJson(identity));
    }

}
