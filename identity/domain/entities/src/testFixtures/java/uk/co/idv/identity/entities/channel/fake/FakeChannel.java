package uk.co.idv.identity.entities.channel.fake;

import com.neovisionaries.i18n.CountryCode;
import uk.co.idv.identity.entities.channel.Channel;

public class FakeChannel implements Channel {

    @Override
    public String getId() {
        return "fake-channel";
    }

    @Override
    public CountryCode getCountry() {
        return CountryCode.GB;
    }

}
