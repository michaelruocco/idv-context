package uk.co.idv.context.usecases.lockout;

import lombok.RequiredArgsConstructor;
import uk.co.idv.context.entities.alias.IdvId;
import uk.co.idv.context.entities.lockout.DefaultLockoutRequest;
import uk.co.idv.context.entities.lockout.ExternalLockoutRequest;
import uk.co.idv.context.entities.lockout.LockoutRequest;

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
