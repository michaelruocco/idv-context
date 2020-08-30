package uk.co.idv.context.entities.policy.method.otp.delivery;

import java.time.Instant;
import java.util.Optional;

public interface Updatable {

    Optional<Instant> getLastUpdated();

}
