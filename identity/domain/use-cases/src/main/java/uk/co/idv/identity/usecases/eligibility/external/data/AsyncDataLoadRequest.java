package uk.co.idv.identity.usecases.eligibility.external.data;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.identity.entities.alias.Aliases;
import uk.co.idv.identity.entities.identity.RequestedData;

import java.time.Duration;

@Builder
@Data
public class AsyncDataLoadRequest {

    private final Duration timeout;
    private final Aliases aliases;
    private final RequestedData requestedData;

    public boolean emailAddressesRequested() {
        return getRequestedData().emailAddressesRequested();
    }

    public boolean phoneNumbersRequested() {
        return getRequestedData().phoneNumbersRequested();
    }

}
