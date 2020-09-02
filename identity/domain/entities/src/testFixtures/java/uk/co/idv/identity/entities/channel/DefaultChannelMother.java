package uk.co.idv.identity.entities.channel;

import com.neovisionaries.i18n.CountryCode;

public interface DefaultChannelMother {

    static DefaultChannel withId(String id) {
        return builder().id(id).build();
    }

    static DefaultChannel withCountry(CountryCode country) {
        return builder().country(country).build();
    }

    static DefaultChannel build() {
        return builder().build();
    }

    static DefaultChannel.DefaultChannelBuilder builder() {
        return DefaultChannel.builder()
                .id("default-channel")
                .country(CountryCode.GB);
    }

}
