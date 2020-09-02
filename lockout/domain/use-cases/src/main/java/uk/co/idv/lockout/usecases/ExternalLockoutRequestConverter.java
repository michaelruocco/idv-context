package uk.co.idv.lockout.usecases;

import lombok.RequiredArgsConstructor;
import uk.co.idv.identity.entities.alias.IdvId;
import uk.co.idv.lockout.entities.DefaultLockoutRequest;
import uk.co.idv.lockout.entities.ExternalLockoutRequest;
import uk.co.idv.lockout.entities.LockoutRequest;

import java.time.Clock;

@RequiredArgsConstructor
public class ExternalLockoutRequestConverter {

    private final Clock clock;

    public LockoutRequest toLockoutRequest(ExternalLockoutRequest externalRequest, IdvId idvId) {
        return DefaultLockoutRequest.builder()
                .timestamp(clock.instant())
                .externalRequest(externalRequest)
                .idvId(idvId)
                .build();
    }

}
