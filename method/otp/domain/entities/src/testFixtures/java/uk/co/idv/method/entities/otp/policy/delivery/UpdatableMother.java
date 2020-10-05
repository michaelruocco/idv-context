package uk.co.idv.context.entities.policy.method.otp.delivery;

import java.time.Instant;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public interface UpdatableMother {

    static Updatable withoutLastUpdated() {
        return withLastUpdated(null);
    }

    static Updatable withLastUpdated(Instant lastUpdated) {
        Updatable updatable = mock(Updatable.class);
        given(updatable.getLastUpdated()).willReturn(Optional.ofNullable(lastUpdated));
        return updatable;
    }

}
