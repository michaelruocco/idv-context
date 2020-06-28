package uk.co.idv.context.entities.channel.gb;

import com.neovisionaries.i18n.CountryCode;
import lombok.Builder;
import uk.co.idv.context.entities.channel.DefaultRsa;

import java.util.UUID;

public class GbRsa extends DefaultRsa {

    public static final String ID = "gb-rsa";

    @Builder
    public GbRsa(UUID issuerSessionId) {
        super(ID, CountryCode.GB, issuerSessionId, null);
    }

}
