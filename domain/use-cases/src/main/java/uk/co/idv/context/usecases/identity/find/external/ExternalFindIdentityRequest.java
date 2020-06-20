package uk.co.idv.context.usecases.identity.find.external;

import com.neovisionaries.i18n.CountryCode;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.channel.Channel;

import java.util.Collection;

public interface ExternalFindIdentityRequest {

    Aliases getAliases();

    Collection<String> getRequested();

    Channel getChannel();

    String getChannelId();

    CountryCode getCountry();

    boolean emailAddressesRequested();

    boolean phoneNumbersRequested();

}
