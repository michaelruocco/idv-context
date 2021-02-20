package uk.co.idv.method.entities.verification;


import uk.co.idv.method.entities.verification.CompleteVerificationRequest.CompleteVerificationRequestBuilder;

import java.time.Instant;
import java.util.UUID;

public interface CompleteVerificationRequestMother {

    static CompleteVerificationRequest successful() {
        return builder().build();
    }

    static CompleteVerificationRequest withTimestamp() {
        return builder().build();
    }

    static CompleteVerificationRequest withoutTimestamp() {
        return builder().timestamp(null).build();
    }

    static CompleteVerificationRequest withId(UUID id) {
        return builder().id(id).build();
    }

    static CompleteVerificationRequestBuilder builder() {
        return CompleteVerificationRequest.builder()
                .contextId(UUID.fromString("2948aadc-7f63-4b00-875b-77a4e6608e5c"))
                .id(UUID.fromString("81e11840-140e-45ac-a6af-40aa653a146e"))
                .successful(true)
                .timestamp(Instant.parse("2020-09-14T20:05:05.005Z"));
    }

}
