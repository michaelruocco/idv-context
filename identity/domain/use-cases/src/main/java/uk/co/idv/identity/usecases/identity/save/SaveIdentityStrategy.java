package uk.co.idv.identity.usecases.identity.save;

import uk.co.idv.identity.entities.identity.Identity;

public interface SaveIdentityStrategy {

    Identity prepare(Identity updated, Identity existing);

}
