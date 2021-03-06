package uk.co.idv.method.entities.otp.delivery.phone;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.method.entities.otp.policy.delivery.Updatable;

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

    public int getLastDigit() {
        try {
            return Integer.parseInt(value.substring(value.length() - 1));
        } catch (StringIndexOutOfBoundsException | NumberFormatException e) {
            throw new InvalidPhoneNumberValueException(value, e);
        }
    }

}
