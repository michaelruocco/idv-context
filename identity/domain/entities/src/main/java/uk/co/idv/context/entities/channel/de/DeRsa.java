package uk.co.idv.context.entities.channel.de;

import com.neovisionaries.i18n.CountryCode;
import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.entities.channel.Rsa;

import java.util.Optional;
import java.util.UUID;

@Builder
@Data
public class DeRsa implements Rsa {

    public static final String ID = "de-rsa";

    private final UUID issuerSessionId;
    private final UUID dsSessionId;

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public Optional<UUID> getIssuerSessionId() {
        return Optional.ofNullable(issuerSessionId);
    }

    @Override
    public Optional<UUID> getDsSessionId() {
        return Optional.ofNullable(dsSessionId);
    }

    @Override
    public CountryCode getCountry() {
        return CountryCode.DE;
    }

}
