package uk.co.idv.identity.entities.channel;

import java.util.Optional;
import java.util.UUID;

public interface Rsa extends Channel {

    Optional<UUID> getIssuerSessionId();

    Optional<UUID> getDsSessionId();

}
