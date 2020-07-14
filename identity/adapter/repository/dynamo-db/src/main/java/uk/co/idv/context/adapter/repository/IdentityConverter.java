package uk.co.idv.context.adapter.repository;

import com.amazonaws.services.dynamodbv2.document.BatchGetItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableKeysAndAttributes;
import com.amazonaws.services.dynamodbv2.document.TableWriteItems;
import lombok.RequiredArgsConstructor;
import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.identity.Identities;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.mruoc.json.JsonConverter;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class IdentityConverter {

    private final Table table;
    private final JsonConverter jsonConverter;

    public TableWriteItems toBatchUpdateItems(Identity identity) {
        return new TableWriteItems(table.getTableName())
                .withItemsToPut(toItems(identity));
    }

    public TableWriteItems toBatchDeleteItems(Aliases aliases) {
        return new TableWriteItems(table.getTableName())
                .withPrimaryKeysToDelete(toPrimaryKeys(aliases));
    }

    public TableKeysAndAttributes toKeys(Aliases aliases) {
        return new TableKeysAndAttributes(table.getTableName())
                .withPrimaryKeys(toPrimaryKeys(aliases));
    }

    public Identities toIdentities(BatchGetItemOutcome outcome) {
        List<Item> items = outcome.getTableItems().get(table.getTableName());
        Collection<Identity> identityCollection = items.stream()
                .map(this::toIdentity)
                .distinct()
                .collect(Collectors.toList());
        return new Identities(identityCollection);
    }

    private Collection<Item> toItems(Identity identity) {
        final Aliases aliases = identity.getAliases();
        return aliases.stream()
                .map(alias -> toItem(identity, alias))
                .collect(Collectors.toList());
    }

    private PrimaryKey[] toPrimaryKeys(Aliases aliases) {
        return aliases.stream()
                .map(this::toKey)
                .toArray(PrimaryKey[]::new);
    }

    private PrimaryKey toKey(Alias alias) {
        return new PrimaryKey("alias", alias.format());
    }

    private Item toItem(Identity identity, Alias alias) {
        return new Item()
                .withPrimaryKey("alias", alias.format())
                .with("idvId", identity.getIdvIdValue().toString())
                .withJSON("body", jsonConverter.toJson(identity));
    }

    private Identity toIdentity(Item item) {
        return jsonConverter.toObject(item.getJSON("body"), Identity.class);
    }

}
