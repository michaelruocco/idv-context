package uk.co.idv.identity.entities.channel;

import com.neovisionaries.i18n.CountryCode;

public interface Channel {

    String getId();

    CountryCode getCountry();

    default boolean hasId(String id) {
        return getId().equals(id);
    }

}
