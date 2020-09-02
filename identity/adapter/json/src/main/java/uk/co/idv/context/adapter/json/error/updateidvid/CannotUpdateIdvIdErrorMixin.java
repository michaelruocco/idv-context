package uk.co.idv.context.adapter.json.error.updateidvid;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.idv.identity.entities.alias.IdvId;


public interface CannotUpdateIdvIdErrorMixin {

    @JsonIgnore
    IdvId getUpdated();

    @JsonIgnore
    IdvId getExisting();

}
