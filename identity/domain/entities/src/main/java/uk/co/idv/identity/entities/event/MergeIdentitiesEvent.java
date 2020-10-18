package uk.co.idv.identity.entities.event;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.identity.entities.alias.Aliases;
import uk.co.idv.identity.entities.alias.IdvId;
import uk.co.idv.identity.entities.identity.Identities;
import uk.co.idv.identity.entities.identity.Identity;

@Builder
@Data
public class MergeIdentitiesEvent {

    private Identity merged;
    private Identities original;

    public IdvId getMergedIdvId() {
        return merged.getIdvId();
    }

    public Aliases getOriginalIdvIds() {
        return original.getIdvIds();
    }

}
