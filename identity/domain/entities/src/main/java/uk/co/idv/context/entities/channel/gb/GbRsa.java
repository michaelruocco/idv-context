package uk.co.idv.context.entities.channel.gb;

import com.neovisionaries.i18n.CountryCode;
import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.entities.channel.Rsa;

import java.util.Optional;
import java.util.UUID;

@Builder
@Data
public class GbRsa implements Rsa {

    public static final String ID = "gb-rsa";

    private final UUID issuerSessionId;

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
        return Optional.empty();
    }

    @Override
    public CountryCode getCountry() {
        return CountryCode.GB;
    }

}
