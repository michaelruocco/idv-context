package uk.co.idv.identity.adapter.repository;

import lombok.RequiredArgsConstructor;
import org.bson.conversions.Bson;
import uk.co.idv.identity.entities.alias.Alias;
import uk.co.idv.identity.entities.alias.Aliases;
import uk.co.idv.identity.entities.alias.IdvId;

import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.or;

@RequiredArgsConstructor
public class AliasesConverter {

    private final AliasConverter converter;

    public Bson toFindByAliasesQuery(Aliases aliases) {
        return or(aliases.stream()
                .map(this::toFindByAliasQuery)
                .collect(Collectors.toList())
        );
    }

    public Bson toFindByAliasQuery(Alias alias) {
        return converter.toFindByAliasQuery(alias);
    }

    public Bson toFindByIdvIdQuery(IdvId idvId) {
        return converter.toFindByIdvIdQuery(idvId);
    }

}
