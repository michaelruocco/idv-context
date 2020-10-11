package uk.co.idv.identity.entities.channel.fake;

import com.neovisionaries.i18n.CountryCode;
import uk.co.idv.identity.entities.channel.Channel;

public class FakeChannel implements Channel {

    public static final String ID = "fake-channel";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public CountryCode getCountry() {
        return CountryCode.GB;
    }

}
