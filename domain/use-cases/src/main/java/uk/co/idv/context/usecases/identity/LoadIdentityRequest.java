package uk.co.idv.context.usecases.identity;

import com.neovisionaries.i18n.CountryCode;
import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.channel.Channel;

public interface LoadIdentityRequest {

    Alias getProvidedAlias();

    Aliases getAliases();

    Channel getChannel();

    CountryCode getCountry();

    LoadIdentityRequest setAliases(Aliases aliases);

}
