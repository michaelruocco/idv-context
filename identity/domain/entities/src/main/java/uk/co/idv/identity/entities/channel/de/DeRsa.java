package uk.co.idv.identity.entities.channel.de;

import com.neovisionaries.i18n.CountryCode;
import lombok.Builder;
import uk.co.idv.identity.entities.channel.DefaultRsa;

import java.util.UUID;

public class DeRsa extends DefaultRsa {

    public static final String ID = "de-rsa";

    @Builder
    public DeRsa(UUID issuerSessionId, UUID dsSessionId) {
        super(ID, CountryCode.DE, issuerSessionId, dsSessionId);
    }

}
