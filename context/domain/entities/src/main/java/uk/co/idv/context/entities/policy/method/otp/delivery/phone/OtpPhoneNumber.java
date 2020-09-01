package uk.co.idv.context.entities.policy.method.otp.delivery.phone;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.entities.policy.method.otp.delivery.Updatable;

import java.time.Instant;
import java.util.Optional;

@Builder
@Data
public class OtpPhoneNumber implements Updatable {

    private final String value;
    private final boolean mobile;
    private final boolean local;
    private final Instant lastUpdated;

    @Override
    public Optional<Instant> getLastUpdated() {
        return Optional.ofNullable(lastUpdated);
    }

}
