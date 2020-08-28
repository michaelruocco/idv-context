package uk.co.idv.context.entities.identity;

import com.neovisionaries.i18n.CountryCode;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.channel.Channel;

public interface FindIdentityRequest {

    Aliases getAliases();

    RequestedData getRequestedData();

    Channel getChannel();

    default String getChannelId() {
        return getChannel().getId();
    }

    default CountryCode getCountry() {
        return getChannel().getCountry();
    }

    default boolean emailAddressesRequested() {
        return getRequestedData().emailAddressesRequested();
    }

    default boolean phoneNumbersRequested() {
        return getRequestedData().phoneNumbersRequested();
    }

}
