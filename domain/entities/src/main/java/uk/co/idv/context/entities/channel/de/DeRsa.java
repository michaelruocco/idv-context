package uk.co.idv.context.entities.channel.de;

import com.neovisionaries.i18n.CountryCode;
import lombok.Builder;
import uk.co.idv.context.entities.channel.Rsa;

import java.util.Optional;
import java.util.UUID;

@Builder
public class DeRsa implements Rsa {

    private final UUID issuerSessionId;
    private final UUID dsSessionId;

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
