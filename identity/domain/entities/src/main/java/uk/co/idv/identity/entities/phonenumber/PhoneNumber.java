package uk.co.idv.identity.entities.phonenumber;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.Optional;

@Builder
@Data
public class PhoneNumber {

    private final String value;
    private final Instant lastUpdated;

    public Optional<Instant> getLastUpdated() {
        return Optional.ofNullable(lastUpdated);
    }

}
