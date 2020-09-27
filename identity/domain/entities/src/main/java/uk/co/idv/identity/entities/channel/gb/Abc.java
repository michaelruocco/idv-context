package uk.co.idv.identity.entities.channel.gb;

import com.neovisionaries.i18n.CountryCode;
import lombok.Data;
import uk.co.idv.identity.entities.channel.Channel;

@Data
public class Abc implements Channel {

    public static final String ID = "abc";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public CountryCode getCountry() {
        return CountryCode.GB;
    }

}
