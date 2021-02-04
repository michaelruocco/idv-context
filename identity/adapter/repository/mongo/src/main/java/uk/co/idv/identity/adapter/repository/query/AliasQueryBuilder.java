package uk.co.idv.identity.adapter.repository.query;

import org.bson.conversions.Bson;
import uk.co.idv.identity.entities.alias.Alias;
import uk.co.idv.identity.entities.alias.Aliases;
import uk.co.idv.identity.entities.alias.IdvId;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.or;
import static uk.co.idv.identity.adapter.repository.MongoIdentityCollection.ALIASES_TYPE_INDEX_NAME;
import static uk.co.idv.identity.adapter.repository.MongoIdentityCollection.ALIASES_VALUE_INDEX_NAME;

public class AliasQueryBuilder {

    public Bson toFindByAliasesQuery(Aliases aliases) {
        return or(aliases.stream().map(this::toFindByAliasQuery).toArray(Bson[]::new));
    }

    public Bson toFindByAliasQuery(Alias alias) {
        if (alias.isIdvId()) {
            return toFindByIdvIdQuery((IdvId) alias);
        }
        return and(
                eq(ALIASES_VALUE_INDEX_NAME, alias.getValue()),
                eq(ALIASES_TYPE_INDEX_NAME, alias.getType())
        );
    }

    public Bson toFindByIdvIdQuery(IdvId idvId) {
        return eq("_id", idvId.getValue());
    }

}
