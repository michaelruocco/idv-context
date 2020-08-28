package uk.co.idv.context.adapter.json.alias;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.alias.IdvId;

import java.util.Collection;
import java.util.UUID;

public interface AliasesMixin {

    @JsonIgnore
    UUID getIdvIdValue();

    @JsonIgnore
    IdvId getIdvId();

    @JsonIgnore
    Aliases getCreditCardNumbers();

    @JsonIgnore
    boolean getFirstValue();

    @JsonIgnore
    boolean isEmpty();

    @JsonIgnore
    Collection<String> getTypes();

}
