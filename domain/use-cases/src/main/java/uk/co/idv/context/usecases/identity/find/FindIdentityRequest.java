package uk.co.idv.context.usecases.identity.find;

import uk.co.idv.context.entities.alias.Aliases;

public interface FindIdentityRequest {

    String getChannelId();

    Aliases getAliases();

}
