package uk.co.idv.identity.usecases.eligibility.external.data;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.identity.entities.alias.Aliases;
import uk.co.idv.identity.entities.identity.RequestedData;

import java.time.Duration;

@Builder
@Data
@Slf4j
public class AsyncDataLoadRequest {

    private final Duration timeout;
    private final Aliases aliases;
    private final RequestedData requestedData;

    public boolean emailAddressesRequested() {
        boolean requested = getRequestedData().emailAddressesRequested();
        log.debug("email addresses requested {}", requested);
        return requested;
    }

    public boolean phoneNumbersRequested() {
        boolean requested = getRequestedData().phoneNumbersRequested();
        log.debug("phone numbers requested {}", requested);
        return requested;
    }

    public boolean mobileDevicesRequested() {
        boolean requested = getRequestedData().mobileDevicesRequested();
        log.debug("mobile devices requested {}", requested);
        return requested;
    }

}
