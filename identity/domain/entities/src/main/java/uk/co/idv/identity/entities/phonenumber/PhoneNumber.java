package uk.co.idv.identity.entities.phonenumber;

import lombok.Builder;
import lombok.Data;
import lombok.With;
import uk.co.idv.identity.entities.Updatable;

import java.time.Instant;
import java.util.Optional;

@Builder
@Data
public class PhoneNumber implements Updatable<PhoneNumber> {

    @With
    private final String value;
    private final Instant lastUpdated;

    public Optional<Instant> getLastUpdated() {
        return Optional.ofNullable(lastUpdated);
    }

}
