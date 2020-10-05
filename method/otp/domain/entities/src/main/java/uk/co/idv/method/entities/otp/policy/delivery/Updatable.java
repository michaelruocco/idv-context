package uk.co.idv.method.entities.otp.policy.delivery;

import java.time.Instant;
import java.util.Optional;

public interface Updatable {

    Optional<Instant> getLastUpdated();

}
