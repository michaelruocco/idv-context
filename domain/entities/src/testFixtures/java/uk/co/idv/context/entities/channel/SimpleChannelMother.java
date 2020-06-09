package uk.co.idv.context.entities.channel;

import com.neovisionaries.i18n.CountryCode;

public interface SimpleChannelMother {

    static SimpleChannel withId(String id) {
        return builder().id(id).build();
    }

    static SimpleChannel withCountry(CountryCode country) {
        return builder().country(country).build();
    }

    static SimpleChannel simple() {
        return builder().build();
    }

    static SimpleChannel.SimpleChannelBuilder builder() {
        return SimpleChannel.builder()
                .id("simple-channel")
                .country(CountryCode.GB);
    }

}
