package uk.co.idv.context.entities.channel;

import com.neovisionaries.i18n.CountryCode;

public interface Channel {

    String getId();

    CountryCode getCountry();

    default boolean hasId(String id) {
        return getId().equals(id);
    }

}
