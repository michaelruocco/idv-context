package uk.co.idv.identity.entities.channel;

import com.neovisionaries.i18n.CountryCode;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Data
public class DefaultRsa implements Rsa {

    private final String id;
    private final CountryCode country;
    private final UUID issuerSessionId;
    private final UUID dsSessionId;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Optional<UUID> getIssuerSessionId() {
        return Optional.ofNullable(issuerSessionId);
    }

    @Override
    public Optional<UUID> getDsSessionId() {
        return Optional.ofNullable(dsSessionId);
    }

}
