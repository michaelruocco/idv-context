package uk.co.idv.context.usecases.identity.save;

import uk.co.idv.context.entities.identity.Identity;

public interface SaveIdentityStrategy {

    Identity prepare(Identity updated, Identity existing);

}
