package uk.co.idv.context.usecases.identity.save;

import uk.co.idv.context.entities.identity.Identity;

public interface SaveIdentity {

    Identity save(Identity update, Identity existing);

}
