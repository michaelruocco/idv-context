package uk.co.idv.identity.entities.event;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.identity.entities.identity.Identities;
import uk.co.idv.identity.entities.identity.Identity;

@Builder
@Data
public class MergeIdentitiesEvent {

    private Identity merged;
    private Identities original;

}
