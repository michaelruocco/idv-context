package uk.co.idv.identity.adapter.repository.query;

import lombok.RequiredArgsConstructor;
import org.bson.conversions.Bson;
import uk.co.idv.identity.entities.alias.Alias;
import uk.co.idv.identity.entities.alias.Aliases;
import uk.co.idv.identity.entities.alias.IdvId;

import static com.mongodb.client.model.Filters.or;

@RequiredArgsConstructor
public class AliasesQueryBuilder {

    private final AliasQueryBuilder builder;

    public AliasesQueryBuilder() {
        this(new AliasQueryBuilder());
    }

    public Bson toFindByAliasesQuery(Aliases aliases) {
        return or(aliases.stream().map(builder::toFindByAliasQuery).toArray(Bson[]::new));
    }

    public Bson toFindByAliasQuery(Alias alias) {
        return builder.toFindByAliasQuery(alias);
    }

    public Bson toFindByIdvIdQuery(IdvId idvId) {
        return builder.toFindByIdvIdQuery(idvId);
    }

}
