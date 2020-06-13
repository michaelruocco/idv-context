package uk.co.idv.context.usecases.identity;

import com.neovisionaries.i18n.CountryCode;
import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.channel.Channel;

import java.time.Duration;

public interface FindIdentityRequest {

    Alias getProvidedAlias();

    Channel getChannel();

    CountryCode getCountry();

    Duration getDataLoadTimeout();

    Aliases getAliases();

    FindIdentityRequest setAliases(Aliases aliases);

}
