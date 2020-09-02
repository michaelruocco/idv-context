package uk.co.idv.lockout.entities;

import uk.co.idv.identity.entities.alias.IdvIdMother;
import uk.co.idv.lockout.entities.DefaultLockoutRequest.DefaultLockoutRequestBuilder;

import java.time.Instant;

public interface LockoutRequestMother {

    static LockoutRequest build() {
        return builder().build();
    }

    static DefaultLockoutRequestBuilder builder() {
        return DefaultLockoutRequest.builder()
                .timestamp(Instant.parse("2020-08-14T05:18:44.700Z"))
                .idvId(IdvIdMother.idvId())
                .externalRequest(DefaultExternalLockoutRequestMother.build());
    }

}
