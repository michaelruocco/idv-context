package uk.co.idv.identity.usecases.identity.save;

import uk.co.idv.identity.entities.identity.Identity;

public interface SaveIdentity {

    Identity save(Identity updated, Identity existing);

}
