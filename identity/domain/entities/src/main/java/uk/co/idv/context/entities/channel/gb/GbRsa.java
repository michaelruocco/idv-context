package uk.co.idv.context.entities.channel.gb;

import com.neovisionaries.i18n.CountryCode;
import lombok.Builder;
import uk.co.idv.context.entities.channel.Rsa;

import java.util.Optional;
import java.util.UUID;

@Builder
public class GbRsa implements Rsa {

    private final UUID issuerSessionId;

    @Override
    public String getId() {
        return "gb-rsa";
    }

    @Override
    public Optional<UUID> getIssuerSessionId() {
        return Optional.ofNullable(issuerSessionId);
    }

    @Override
    public Optional<UUID> getDsSessionId() {
        return Optional.empty();
    }

    @Override
    public CountryCode getCountry() {
        return CountryCode.GB;
    }

}
