package uk.co.idv.context.entities.channel.gb;

import com.neovisionaries.i18n.CountryCode;
import uk.co.idv.context.entities.channel.Channel;

public class GbAs3 implements Channel {

    public static final String ID = "AS3";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public CountryCode getCountry() {
        return CountryCode.GB;
    }

}
